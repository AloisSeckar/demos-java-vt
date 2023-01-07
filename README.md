# Java 19 Virtual Threads

This application shows the difference between the old 'platform' and new 'virtual' threads
in Java 19. While 'platform' threads are bind to underlying OS and thus potentially limited,
'virtual' threads exist only inside JVM and you can have as many as you want. 

In this example the program first trying to create N 'platform' threads, while this is
intended to fail during the process. Then the very same code is executed with 'virtual'
threads and everything finishes without runtime error.

## Note
This will only work as intended on Unix-based systems, because there is an upper bound of
number of threads (processes) and also maximum number of processes per user. On the other
hand on Windows there is no such limit - the number of threads is unlimited, although
performance issues will occur if you try to push it.

Therefore, a Linux machine is required to run this demo and see the "correct" results.

## Usage
Checkout and `mvn clean install` the project and then either run
`java --enable-preview -jar target\JavaDemos-2.0.jar` directly or move the `jar` 
to target Linux platform with Java and run the command there.

## Author
* alois.seckar@atos.net
* http://alois-seckar.cz
