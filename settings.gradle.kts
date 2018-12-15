pluginManagement {
    repositories {
        maven("/Users/jmfayard/Dev/mautinoa/buildSrcVersions/build/repository")
        gradlePluginPortal()
        jcenter()
        google()
    }

    resolutionStrategy {
        eachPlugin {
            val plugin = requested.id.id
            val module = when {
                plugin.startsWith("com.android") -> Libs.com_android_tools_build_gradle
                plugin.startsWith("kotlin")  -> Libs.kotlin_gradle_plugin
                else -> return@eachPlugin
            }
            println("resolutionStrategy for plugin=$plugin : $module")
            useModule(module)
        }
    }
}
rootProject.name = "android-kotlin-magellan"