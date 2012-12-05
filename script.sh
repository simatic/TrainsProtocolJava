#!/bin/sh

CPath="./TrainsProtocol"

echo "Generating C JNI libraries"
cd ./src

echo "generating trains_Interface.h"
javah -jni trains.Interface
cd ..
mv ./src/trains_Interface.h $CPath/include/trains_Interface.h

echo "compiling interface.o"
cd $CPath/src 

#Ok if we're compiling on Mac OS X 
#TODO: testing OS 
#gcc -x c -I$CPath/include -I/System/Library/Frameworks/JavaVM.framework/Headers -c interface.c -o interface.o
#echo "compiling libInterface.jnilib"
#gcc -dynamiclib -o libInterface.jnilib interface.o 

#Compiling on Linux
#gcc -I$JAVA_HOME/include -o libInterface.so -shared interface.c
make all
if [ ! -d ../lib ]
then
	mkdir ../lib
fi
mv libtrains.so ../lib
LD_LIBRARY_PATH=../lib:LD_LIBRARY_PATH

exit 0;
