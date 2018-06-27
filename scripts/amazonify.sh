#!/bin/bash

echo "Creating amazon package directory..."

for sourceDir in $(find . -name exoplayer2); do
	# Create tmp dir
	mkdir -p "$sourceDir/../tmp_amazon"

	# Move all source files in this package to the tmp dir.
	mv "$sourceDir"/* "$sourceDir/../tmp_amazon"

	# Make an amazon package dir.
	mkdir -p "$sourceDir/amazon"

	# Move everything from the tmp dir to the amazon package dir.
	mv "$sourceDir"/../tmp_amazon/* "$sourceDir/amazon"

	# Remove the tmp dir.
	rmdir "$sourceDir/../tmp_amazon"
done

echo "Renaming packages..."

for javaFile in $(find . -name *.java); do
	sed -i 's/com\.google\.android\.exoplayer2/com\.google\.android\.exoplayer2\.amazon/gi' "$javaFile" 1> /dev/null 2> /dev/null

	if [ $? -ne 0 ]; then
		echo "Failed to rename package for class: $javaFile"
	fi
done

for androidManifest in $(find . -name AndroidManifest.xml); do
	sed -i 's/com\.google\.android\.exoplayer2/com\.google\.android\.exoplayer2\.amazon/gi' "$androidManifest" 1> /dev/null 2> /dev/null

	if [ $? -ne 0 ]; then
		echo "Failed to rename package for manifest: $androidManifest"
	fi
done
