#!/bin/bash

# Delete all .class files recursively
find . -type f -name "*.class" -exec rm -f {} \;

# Compile the Java source file
javac src/main/Driver.java -cp src

# Execute the Java program with specified arguments
java -classpath src main.Driver "$1" fp

