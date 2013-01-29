TrainsProtocolJava
==================

This is a Java interface for the Trains Protocol designed by Michel Simatic. 
It uses JNI to call native functions from the [C implementation of the Trains Protocol][TrainsProtocol] (read the [tutoriel][trains-tutoriel]).

## Portability

Because it performs JNI calls to a C library, the Java code is not cross-plateform out of the box.
Before using the Java interface for the Trains Protocol, the C source code has to be compiled for the
targeted OS.

So far, the C code for the Trains Protocol has been tested on Linux (Debian and Ubuntu).
It also runs on Mac OS X (the following tutoriel has been tested on OS X Lion).


## Dependencies

You will need to install the [ant tool] [ant] to be able to compile the code as indicated below.
If you're on OS X, you shall need to install a GNU gcc version via macports or homebrew.

## How to get the code

Cloning the git repository (this repo) with Java sources:

~~~ sh
    git clone git@github.com:simatic/TrainsProtocolJava.git
    cd TrainsProtocolJava/
~~~


The native code is a submodule of this git repo:

~~~ sh
    git submodule update --init
    cd TrainsProtocol/

    #IMPORTANT !
    git checkout jni
~~~ 

## How to build

### Building on Linux


~~~ sh
    export LD_LIBRARY_PATH=TrainsProtocol/lib:LD_LIBRARY_PATH
    ant help
    ant build-all
~~~



### Building on OS X

~~~ sh
    export DYLD_LIBRARY_PATH=TrainsProtocol/lib/:$DYLD_LIBRARY_PATH
    ant help
    ant build-all
~~~


#### Notes for OS X Lion (10.7)

On OS X Lion the default compiler is llvm-clang:

~~~ sh
    ls -l /usr/bin/gcc
    lrwxr-xr-x  1 root  wheel  12 11 mar  2012 /usr/bin/gcc@ -> llvm-gcc-4.2
~~~

Calling gccmakedep in the Makefile will fail. Here is a workaround:

~~~ sh
    # Find or install gcc through macports or homebrew
    # e.g.: find /opt -iname "gcc"

    # Then you can either change the symbolic link or do the following export:
    export CC=/opt/local/bin/gcc
    
    # Locate gccmakedep (which should be installed with your custom installed version of make)
    export GCCMAKEDEP_USER=/opt/local/bin/gcc
    ant help
    ant build-all
~~~ 


## Setting up the environment for the Trains Protocol:

Create the addr_file at the root directory and follow these [guidelines][addr_file].

~~~ sh
    # XX is the port number for the process/machine at which you will launch the protocol
    export TRAINS_PORT=XX
~~~

Thiw will launch the example code Example.java available in src/examples: 

~~~ sh
    ant help
    ant run-example
~~~


[trains-tutoriel]: http://www-tp-ext.it-sudparis.eu/~foltz_ar/trainsTutorial.html
[TrainsProtocol]: https://github.com/simatic/TrainsProtocol 
[ant]: http://ant.apache.org/
[addr_file]: http://www-tp-ext.it-sudparis.eu/~foltz_ar/trainsTutorial.html#addr_file
