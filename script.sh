#!/bin/sh

# init the environment by setting up C_INCLUDE_PATH
# the configuration of JAVA_HOME is not enough because some Train protocol Makefiles do not explicitly used this parameters for compile
# and this variable will be used for other applications beside this script, so export keyword is needed
# TODO: auto detection of include folders

# store init path
InitPath=`pwd`

CPath="./TrainsProtocol"

echo "Generating C JNI libraries"
cd ./src

echo "generating trains_Interface.h"
javac trains/Interface.java
javah -jni trains.Interface
cd ..
cp ./src/trains_Interface.h $CPath/include/trains_Interface.h

echo "compiling interface.o"
cd $CPath/src 

#TODO: works on Debian, need to check if it works for Mac
#Ref: http://en.wikipedia.org/wiki/Uname
OSName=`uname -s`

if [ "$OSName" = "Linux" ]
then
	#Compiling on Linux
	#gcc -I$JAVA_HOME/include -o libInterface.so -shared interface.c
  #export C_INCLUDE_PATH=/usr/lib/jvm/java-1.7.0-openjdk-amd64/include/:/usr/lib/jvm/java-1.7.0-openjdk-amd64/include/linux:.
	make allForJNI
else
	if [ "$OSName" = "Darwin" ]
	then
		echo "compile on Mac OS X"
		#Ok if we're compiling on Mac OS X 
                #export C_INCLUDE_PATH=/System/Library/Frameworks/JavaVM.framework/Headers:.
                # NB : Thanks to the use of "gcc -M" in TrainsProtocol 
                # makefiles, there is no more difference between Linux and
                # Mac OS X compilation. Neveretheless, we keep the test
                # to know on which OS we are compiling, just in case we would
                # need it.
                make allForJNI
	fi
fi

if [ ! -d ../lib ]
then
	mkdir ../lib
fi
#mv libtrains.* ../lib

# update LD_LIBARAY_PATH which is also java.library.path
LibPath=$(cd ../lib; pwd)
LD_LIBRARY_PATH=$LibPath:$LD_LIBRARY_PATH

# go back to the init folder
cd $InitPath
