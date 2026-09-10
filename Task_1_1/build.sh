#!/bin/bash
set -e

SRC_DIR="src/main/java"
OUT_DIR="build/classes"
DOC_DIR="build/docs/javadoc"
JAR_PATH="build/app.jar"

mkdir -p "$OUT_DIR" "$DOC_DIR"

find "$SRC_DIR" -name "*.java" | xargs javac --release 25 -d "$OUT_DIR"
find "$SRC_DIR" -name "*.java" | xargs javadoc -d "$DOC_DIR" -quiet -Xdoclint:none
java -jar "$JAR_PATH"

echo "Completed"