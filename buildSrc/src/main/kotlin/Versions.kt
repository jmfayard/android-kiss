import kotlin.String

/**
 * Find which updates are available by running
 *     `$ ./gradlew syncLibs`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version. */
object Versions {
    const val constraint_layout: String = "1.0.2" 

    const val com_android_support_test_espresso: String = "3.0.1" //available: "3.0.2" 

    const val appcompat_v7: String = "27.0.2" 

    const val cardview_v7: String = "27.0.2" //available: "28.0.0" 

    const val design: String = "27.0.2" 

    const val multidex: String = "1.0.2" //available: "1.0.3" 

    const val percent: String = "27.0.2" 

    const val preference_v7: String = "27.0.2" 

    const val support_v4: String = "27.0.2" //available: "28.0.0" 

    const val aapt2: String = "3.2.0-4818971" //available: "3.2.1-4818971" 

    const val com_android_tools_build_gradle: String = "3.2.0" //available: "3.2.1" 

    const val lint_gradle: String = "26.2.0" //available: "26.2.1" 

    const val kotlinandroidviewbindings: String = "0.12" 

    const val jsr305: String = "3.0.2" 

    const val com_gradle_build_scan_gradle_plugin: String = "1.10.2" //available: "2.0.1" 

    const val timber: String = "4.6.0" //available: "4.7.1" 

    const val mockito_kotlin: String = "1.5.0" 

    const val com_wealthfront: String = "1.0.0" //available: "1.1.0" 

    const val kotlintest: String = "2.0.7" 

    const val jmfayard_github_io_gradle_kotlin_dsl_libs_gradle_plugin: String = "0.2.6" 

    const val junit: String = "4.12" 

    const val slimadapter: String = "2.1.2" 

    const val org_jetbrains_kotlin: String = "1.2.71" //available: "1.3.0" 

    /**
     *
     *   To update Gradle, edit the wrapper file at path:
     *      ./gradle/wrapper/gradle-wrapper.properties
     */
    object Gradle {
        const val runningVersion: String = "4.10.2"

        const val currentVersion: String = "4.10.2"

        const val nightlyVersion: String = "5.1-20181031000036+0000"

        const val releaseCandidate: String = "5.0-rc-1"
    }
}