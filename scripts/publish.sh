#!/bin/bash

LIBRARIES="core dash hls smoothstreaming ui"
EXTENSIONS="cast gvr ima jobdispatcher leanback mediasession okhttp rtmp"

./gradlew clean assemble

./gradlew "library:artifactoryPublish"
for lib in $LIBRARIES; do
		echo "------------------------------------------------------------------------"
		echo "Publishing library-$lib"
		echo

		./gradlew "library-$lib:artifactoryPublish"
done

for ext in $EXTENSIONS; do
		echo "------------------------------------------------------------------------"
		echo "Publishing extension-$ext"
		echo

		./gradlew "extension-$ext:artifactoryPublish"
done