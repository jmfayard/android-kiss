#!/usr/bin/env bash
URL="https://firebase.google.com/docs/test-lab/command-line"
APK="build/outputs/apk/debug/android-kotlin-magellan-debug.apk"
TESTAPK="build/outputs/apk/androidTest/debug/android-kotlin-magellan-debug-androidTest.apk"
TESTACTIVITY="com.wealthfront.magellan.kotlinsample.MainActivityTest"
PACKAGE="com.wealthfront.magellan.kotlinsample.test"

DEVICE_NEXUS6P="Nexus6,version=21,locale=en,orientation=portrait"

which gcloud || {
    echo "Install and configure google-cloud-sdk first"
    open "${URL}"
    exit 1
}
cat <<'EOF'
$ gcloud init
$ gcloud firebase test android models list

EOF


gw assembleDebug assembleDebugAndroidTest

gcloud firebase test android run \
  --type robo \
  --app "$APK" \
  --device model="$DEVICE_NEXUS6P" \
  --timeout 90s \
  --robo-directives


gcloud firebase test android run \
      --type instrumentation \
      --app "$APK" \
      --test "$TESTAPK" \
      --device model="$DEVICE_NEXUS6P"
