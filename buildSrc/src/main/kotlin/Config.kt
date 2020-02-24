object Config {
    const val applicationId = "com.wealthfront.magellan.kotlinsample"
    const val compileSdkVersion = 28
    const val targetSdkVersion = 27
    const val versionName = "1.0.0"
    const val versionCode = 1
    const val minSdkVersion = 21

    val forceDependencyVersions = listOf(
            Libs.appcompat_v7, Libs.preference_v7, Libs.constraint_layout, Libs.design,
            Libs.design, Libs.percent,
            Libs.recyclerview_v7, Libs.support_core_utils
    ) + kotlinLibraries()

    fun kotlinLibraries() = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib:" + Versions.kotlin_stdlib,
        "org.jetbrains.kotlin:kotlin-stdlib:" + Versions.kotlin_stdlib
    )
}