object Config {
    const val applicationId = "com.wealthfront.magellan.kotlinsample"
    const val compileSdkVersion = 27
    const val targetSdkVersion = 27
    const val versionName = "1.0.0"
    const val versionCode = 1
    const val minSdkVersion = 21

    val forceDependencyVersions = listOf(
            Libs.appcompat_v7, Libs.preference_v7, Libs.constraint_layout, Libs.design,
            Libs.design, Libs.percent, Libs.kotlin_stdlib,
            Libs.recyclerview_v7, Libs.support_core_utils
    )

    val pluginsResolution : Map<String, String> = mapOf(
            "com.android.application" to Libs.com_android_tools_build_gradle,
            "kotlin-android" to Libs.kotlin_gradle_plugin
    )
}