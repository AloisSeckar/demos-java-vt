package cz.aloisseckar.java.javademos;

import lombok.AllArgsConstructor;

public class VirtualThreadsDemo {

    public static final String PLATFORM = "platform";
    public static final String VIRTUAL = "virtual";

    public boolean releaseThreads;

    public void info() {
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.println("VIRTUAL THREADS IN ACTION DEMO");
        System.out.println("The difference between 'platform' and 'virtual'");
        System.out.println("threads shown on practical example");
        System.out.println("(only working on Unix-based systems)");
        System.out.println("-------------------------------------------------");
        System.out.println();
    }

    public void demo(String threadType, int threadCount) {

        System.out.println("Managing " + threadType + " threads");
        System.out.println("------------------------");

        releaseThreads = false;
        int threadsGenerated = 0;
        try {
            while (threadsGenerated < threadCount) {
                var threadName = threadType + "-" + threadsGenerated;
                if (VIRTUAL.equals(threadType)) {
                    Thread.ofVirtual().start(new SimpleTask(threadName));
                } else {
                    Thread.ofPlatform().start(new SimpleTask(threadName));
                }
                threadsGenerated++;
                if (threadsGenerated % 500 == 0) {
                    System.out.println(threadsGenerated + " threads started");
                }
            }
        } catch (OutOfMemoryError err) {
            // platform threads - probable runtime exception to be raised when hitting the system limit
            // virtual threads - this shouldn't happen
            err.printStackTrace(System.err);
        }

        System.out.println("Releasing " + threadType + " threads...");
        releaseAllThreads();
        System.out.println("Managed to handle " + threadsGenerated + " " + threadType + " threads");
        System.out.println();
    }

    private void releaseAllThreads() {
        releaseThreads = true;
        sleep(2000);
    }

    private void sleep(int period) {
        try {
            Thread.sleep(period);
        } catch (InterruptedException ex) {
            System.err.println("Sleep interrupted!");
        }
    }

    // simple task to be performed by our threads
    // prints out the current thread ID + given instance name
    @AllArgsConstructor
    private class SimpleTask implements Runnable {

        // potentially useful to identify the thread instance
        // currently not used to avoid polluting log
        private String threadName;

        @Override
        public void run() {
            while (!releaseThreads) {
                sleep(1000);
            }
        }
    }

}
