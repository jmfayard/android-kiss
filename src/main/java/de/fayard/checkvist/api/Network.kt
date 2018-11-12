package de.fayard.checkvist.api


import com.squareup.moshi.Moshi
import de.fayard.checkvist.api.Property.CHECKVIST_LIST
import de.fayard.checkvist.api.Property.CHECKVIST_URL
import de.fayard.checkvist.api.Property.USER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

val CHECKVIST_KEY : String = TODO()

val kodeinCheckvistModule = Kodein.Module("checkvist") {

    bind() from singleton { buildOkHttpClient() }
    bind() from singleton { Moshi.Builder().build()  }
    bind() from singleton {
        buildRetrofit(
            CHECKVIST_DEFAULT_PARAMS["CHECKVIST_URL"] as String,
            instance(),
            instance()
        )
    }
    bind() from singleton { CheckvistCoroutineApi(instance(), instance()) }
    bind() from singleton {
        CheckvistCredentials(
            CHECKVIST_KEY,
            CHECKVIST_DEFAULT_PARAMS["USER"] as String,
            CHECKVIST_DEFAULT_PARAMS["CHECKVIST_LIST"] as Int
        )
    }
    bind() from singleton { instance<Retrofit>().create(CheckvistApi::class.java) }
}

private object Property {

    const val CHECKVIST_LIST = "CHECKVIST_LIST"
    const val USER = "USER"
    const val CHECKVIST_URL = "CHECKVIST_URL"
}

val CHECKVIST_DEFAULT_PARAMS = mapOf(
    USER to "jmfayard@gmail.com",
    CHECKVIST_URL to "https://checkvist.com/",
    CHECKVIST_LIST to 649516
)


suspend fun addTask(api: CheckvistCoroutineApi, credentials: CheckvistCredentials, title: String, input: List<String>) {
    val parentTask = api.createTask(CNewTask(content = title), credentials.defaultList).checkOk()
    var position = 0
    var noteFound = false
    var noteContent = ""
    for (i in input) {
        val line = i.trim().trimMargin("- ")
        noteFound = noteFound or line.isBlank()

        if (noteFound) {
            noteContent += line
        } else {
            position++
            val childrenTask =
                CNewTask(content = line, parent_id = parentTask.id, position = -1)
            api.createTask(childrenTask, credentials.defaultList)

        }
    }
    if (noteContent.trim().isNotBlank()) {
        println("ok")
        api.createNote(noteContent, parentTask)
        println("bye")
    }
}

private fun <T: Any> Result<T>.checkOk(): T {
    if (this is Result.Ok) return this.value
    else error("Got $this")
}

suspend fun CoroutineScope.textFrom(param: String): ReceiveChannel<String> = produce {
    val input = """
Ingrédients
Carottes
Oignons

Super note ici""".trim()

    for (line in input.splitToSequence('\n')) {
        send(line)
    }
}

@Suppress("UNUSED_VARIABLE")
suspend fun doStuff(api: CheckvistCoroutineApi, credentials: CheckvistCredentials) {
    println(
        """"
        |USER=${credentials.USER}
        |CHECKVIST_KEY=${credentials.CHECKVIST_KEY}
        |AUTH=${credentials.auth()}
    """.trimMargin()
    )
    val login: String = api.login().checkOk().debug("login")
    val currentUser: CUser = api.currentUser().checkOk().debug("currentUser")
    val lists: List<CList> = api.lists().checkOk().debugList("lists")
    val list: CList = lists.first { list -> list.name == "api" }.debug("list")
    val tasks: List<CTask> = api.tasks(list.id).checkOk().debugList("tasks")
    val firstTask: CTask = tasks.first()
    check(firstTask.content == "task")
    val notes: List<CNote> = api.getNotes(firstTask).checkOk().debugList("notes")
    check(notes.first().comment == "note")

    val newTask = api.createTask(CNewTask(content = "Try kotlin #coroutines ^asap"), list.id).checkOk().debug("newTask")
    api.deleteTask(newTask.id, list.id).debug("deleted")

}


fun buildOkHttpClient(): OkHttpClient {
    val LEVEL = HttpLoggingInterceptor.Level.BASIC
    val logger: HttpLoggingInterceptor = HttpLoggingInterceptor(::println).setLevel(LEVEL)
    return OkHttpClient.Builder().addNetworkInterceptor(logger).build()
}

fun buildRetrofit(baseUrl: String, client: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(client)
    .validateEagerly(true)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

typealias Unknown = java.lang.Object

interface CheckvistApi {

    @POST("auth/login.json")
    fun login(@Query("username") username: String, @Query("remote_key") remote_key: String): Call<String>

    @GET("auth/curr_user.json")
    fun currentUser(@Header("Authorization") auth: String): Call<CUser>

    @GET("checklists.json")
    fun checklists(@Header("Authorization") auth: String): Call<List<CList>>

    @GET("checklists/{list}.json")
    fun checklist(@Path("list") list: Int, @Header("Authorization") auth: String): Call<CList>

    @GET("checklists/{list}/tasks.json")
    fun checkTasks(@Path("list") list: Int, @Header("Authorization") auth: String): Call<List<CTask>>

    @POST("checklists/{list}/tasks.json")
    fun createTask(@Body task: CNewTask, @Path("list") list: Int, @Header("Authorization") auth: String): Call<CTask>

    @DELETE("checklists/{list}/tasks/{task}.json")
    fun deleteTask(@Path("task") task: Int, @Path("list") list: Int, @Header("Authorization") auth: String): Call<CTask>

    @GET("checklists/{list}/tasks/{task}/comments.json")
    fun getNotes(@Path("task") task: Int, @Path("list") list: Int, @Header("Authorization") auth: String): Call<List<CNote>>

    @POST("checklists/{list}/tasks/{task}/comments.json")
    @FormUrlEncoded
    fun createNote(
        @Field("comment[comment]") note: String,
        @Path("task") task: Int,
        @Path("list") list: Int,
        @Header("Authorization") auth: String
    ): Call<CNote>

}

class CheckvistCoroutineApi(val api: CheckvistApi, val credentials: CheckvistCredentials) {
    suspend fun login(): Result<String> = api.login(credentials.USER, credentials.CHECKVIST_KEY).awaitResult()
    suspend fun currentUser(): Result<CUser> = api.currentUser(credentials.auth()).awaitResult()
    suspend fun lists(): Result<List<CList>> = api.checklists(credentials.auth()).awaitResult()
    suspend fun list(list: Int): Result<CList> = api.checklist(list, credentials.auth()).awaitResult()
    suspend fun tasks(list: Int): Result<List<CTask>> = api.checkTasks(list, credentials.auth()).awaitResult()
    suspend fun createTask(task: CNewTask, list: Int): Result<CTask> =
        api.createTask(task, list, credentials.auth()).awaitResult()

    suspend fun deleteTask(task: Int, list: Int): Result<CTask> =
        api.deleteTask(task, list, credentials.auth()).awaitResult()

    suspend fun getNotes(task: CTask) = api.getNotes(task.id, task.checklist_id, credentials.auth()).awaitResult()
    suspend fun createNote(comment: String, task: CTask) =
        api.createNote(comment, task.id, task.checklist_id, credentials.auth()).awaitResult()
}


