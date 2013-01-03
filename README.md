TrainsProtocolJava
==================

This is a Java interface for the Trains Protocol designed by Michel Simatic. 
It uses JNI to call native functions from the C implementation of the Trains Protocol
tutoriel: http://www-tp-ext.it-sudparis.eu/~foltz_ar/trainsTutorial.html 
code: https://github.com/simatic/TrainsProtocol

## Portability

Because it performs JNI calls to a C library, the Java code is not cross-plateform out of the box.
Before using the Java interface for the Trains Protocol, the C source code has to be compiled for the
targeted OS.

So far, the C code for the Trains Protocol has been tested on Linux (Debian and Ubuntu).
It should also work on Mac OS X with a few additional commands (tutoriel yet to come).


## Dependencies

You will need to install the [ant tool] [ant] to be able to compile the code as indicated below.


## How to build and run the code

Cloning the git repository (this repo) with Java sources:

~~~ sh
    git clone git@github.com:simatic/TrainsProtocolJava.git
    cd TrainsProtocolJava/
~~~


The C code is a submodule of this git repo:

~~~ sh
    git submodule update --init
    cd TrainsProtocol/
    git checkout jni
~~~ 

### Building and Running on Linux

~~~ sh
    export LD_LIBRARY_PATH=TrainsProtocol/lib:LD_LIBRARY_PATH
    ant
~~~


Setting up the environment for the Trains Protocol:

~~~ sh
    # create the addr_file at the root directory and follow these [guidelines][addr_file]
    touch  addr_file
    # XX is the port number for the process/machine at which you will launch the protocol
    export TRAINS_PORT=XX
~~~


Thiw will launch the example code available in src/Examples: 

~~~ sh
    ant run
~~~



[ant]: http://ant.apache.org/
[addr_file]: http://www-tp-ext.it-sudparis.eu/~foltz_ar/trainsTutorial.html#addr_file
