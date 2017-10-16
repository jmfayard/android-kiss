#!/usr/bin/env bash
export ITERATIONS=${1:-1}
export PACKAGE="com.wealthfront.magellan.kotlinsample"
export ACTIVITY="com.wealthfront.magellan.kotlinsample.MainActivity"
echo "Starting $PACKAGE"

export START_OK=$( adb shell am start -n ${PACKAGE}/${ACTIVITY} 2>&1 | grep -i error )
test -z "$START_OK" || {
    echo "Package ${PACKAGE} not installed yet"
    echo "You can install it with"
    echo "$ ./gradlew installDebug"
    exit 1
}

echo "Will run ${ITERATIONS} iterations on ${PACKAGE} of the evil chaos monkey: "
echo "$ adb shell monkey -p ${PACKAGE} -v 5000"

sleep 2
for i in $( seq 1 ${ITERATIONS} ) ; do
  adb shell monkey -p "${PACKAGE}" -v 5000
done

