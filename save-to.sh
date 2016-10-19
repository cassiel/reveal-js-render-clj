#!/bin/bash

if [ "$1" ]; then
    wget --recursive \
         --timestamping \
         --no-directories \
         --convert-links \
         --directory-prefix=$1 \
         http://localhost:5000/reveal.js/index-ssi.shtml
    mv $1/index-ssi.shtml $1/index.html
else
    echo "Destination directory needed"
    exit 1
fi
