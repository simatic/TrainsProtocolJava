#!/bin/sh

CPath="/Users/stephanie/dev/PFE/TrainsProtocol"

echo "Generating C JNI libraries"
cd bin/

echo "generating trains_Interface.h"
javah -jni trains.Interface
mv trains_Interface.h $CPath/include/trains_Interface.h

echo "compiling interface.o"
cd $CPath/src 

#Ok if we're compiling on Mac OS X 
#TODO: testing OS 
gcc -x c -I$CPath/include -I/System/Library/Frameworks/JavaVM.framework/Headers -c interface.c -o interface.o
echo "compiling libInterface.jnilib"
gcc -dynamiclib -o libInterface.jnilib interface.o 

mv libInterface.jnilib $CPath/lib

exit 0;
