#!/bin/bash

LIBRARIES="core dash hls smoothstreaming ui"

./gradlew clean assemble

for lib in $LIBRARIES; do
		./gradlew "library-$lib:artifactoryPublish"
done