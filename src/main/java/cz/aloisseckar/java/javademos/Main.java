package cz.aloisseckar.java.javademos;

/**
 * This demo shows new preview feature from Java 19 - Virtual Threads
 *
 * Introduced: Java 19
 * Finalized: Not yet
 *
 * Further reading:
 * https://blogs.oracle.com/javamagazine/post/java-loom-virtual-threads-platform-threads
 * https://openjdk.org/jeps/425
 *
 * @author alois.seckar@atos.net
 */
public class Main {

    public static void main(String[] args) {

        // https://stackoverflow.com/a/19165338/3204544
        System.out.println("JVM INFO");
        System.out.println(System.getProperty("java.vm.name"));
        System.out.println(System.getProperty("java.home"));
        System.out.println(System.getProperty("java.vendor"));
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.specification.vendor"));
        //

        // NOTE: this will only work on Unix-based platforms
        // on Windows there is actually no limit for process
        // you will only experience decreasing performance...

        VirtualThreadsDemo instance = new VirtualThreadsDemo();
        instance.info();
        instance.demo();

    }
}
