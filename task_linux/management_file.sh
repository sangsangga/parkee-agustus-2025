#!/bin/bash

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "Directory and Extension are Required"
    exit 1
fi

DIRECTORY="$1"
EXTENSION="$2"

if [ ! -d "$DIRECTORY"]; then
    echo "Directory Not Exists"
    exit 1
fi


echo "Searching for $EXTENSION files in $DIRECTORY..."
RESULTS=$(find "$DIRECTORY" -name "*.$EXTENSION")


if [ -n "$RESULTS" ]; then
    echo "$RESULTS"
else 
    echo "No Files with $EXTENSION in $DIRECTORY"
fi

