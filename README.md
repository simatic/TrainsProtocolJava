TrainsProtocolJava
==================

This is a Java interface for the Trains Protocol designed by Michel Simatic. 
It uses JNI to call native functions from the [C implementation of the Trains Protocol][TrainsProtocol] (read the [tutoriel][trains-tutoriel]).

Trains protocol is a uniform and totally-ordered broadcast protocol [Défago et al., 2004].
It is designed to be a throughput-efficient protocol, especially for short messages (100 bytes or lower) [Simatic, 2012].

## Portability

Because it performs JNI calls to a C library, the Java code is not cross-plateform out of the box.
Before using the Java interface for the Trains Protocol, the C source code has to be compiled for the
targeted OS.

So far, the C code for the Trains Protocol has been tested on Linux (Debian and Ubuntu).
It also runs on Mac OS X (the following tutoriel has been tested on OS X Lion). However, except the basicTest in tests/integration, the 
other tests doesn't run on Mac OS X at the moment.


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
~~~

You also need to find the jni.h header, which comes along with the JDK (the location depends of your system):

~~~ sh
    export C_INCLUDE_PATH=/usr/lib/jvm/java-1.7.0-openjdk-amd64/include/:/usr/lib/jvm/java-1.7.0-openjdk-amd64/include/linux:.
    ant help
    ant build-all
~~~


### Building on OS X

~~~ sh
    export DYLD_LIBRARY_PATH=TrainsProtocol/lib/:$DYLD_LIBRARY_PATH
~~~

You also need to find the jni.h header, which comes along with the JDK (the location depends of your system):

~~~ sh
    export C_INCLUDE_LIBRARY=/System/Library/Frameworks/JavaVM.framework/Headers:.
    ant help
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

Create the addr_file at the root directory and follow these [guidelines][addr_file] or these [ones][addr_file_readme].
~~~ sh
    # XX is the port number for the process/machine at which you will launch the protocol
    export TRAINS_PORT=XX
~~~

Thiw will launch the example code Example.java available in src/examples: 

~~~ sh
    ant help
    ant run-examples
~~~


## Documentation

You can generate the javadoc of the Java API of the Trains Protocol in doc/javadoc/.
The C native code in TrainsProtocol is also documented with doxygen.
In doc/uml/, UML class diagrams are generated thanks to ObjectAid UML.


## Bibliography

[Défago et al., 2004] Défago, X., Schiper, A. et Urbán, P. (2004). Total order broadcast and multicast algorithms : Taxonomy and survey. ACM Comput. Surv., 36:372?421.

[Simatic, 2012] M. Simatic. Communication et partage de données dans les systèmes répartis (Data communication and data sharing in distributed system, in French). PhD thesis, École Doctorale ÉDITE, October 2012 (available at http://www-public.it-sudparis.eu/~simatic/Recherche/Publications/theseSimatic.pdf).

[trains-tutoriel]: http://www-tp-ext.it-sudparis.eu/~foltz_ar/trainsTutorial.html
[TrainsProtocol]: https://github.com/simatic/TrainsProtocol 
[ant]: http://ant.apache.org/
[addr_file_readme]: https://github.com/simatic/TrainsProtocol#running-an-application-using-trains-protocol
[addr_file]: http://www-tp-ext.it-sudparis.eu/~foltz_ar/trainsTutorial.html#addr_file
