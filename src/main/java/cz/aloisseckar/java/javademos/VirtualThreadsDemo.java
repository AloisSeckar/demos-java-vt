package cz.aloisseckar.java.javademos;

import lombok.AllArgsConstructor;

public class VirtualThreadsDemo {

    // Debian GNU/Linux 11
    // max. threads:
    // cat /proc/sys/kernel/threads-max
    // user process limit:
    // ulimit -u

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

    public void demo(int threadCount) {

        // 'platform threads' are bound to OS
        // => number of instances limited
        System.out.println("Platform threads");
        System.out.println("----------------");
        releaseThreads = false;
        int platformThreadsGenerated = 0;
        try {
            while (platformThreadsGenerated < threadCount) {
                Thread.ofPlatform().start(new SimpleTask("platform-" + platformThreadsGenerated));
                platformThreadsGenerated++;
                if (platformThreadsGenerated % 1000 == 0) {
                    System.out.println(platformThreadsGenerated + " threads started");
                }
            }
        } catch (OutOfMemoryError err) {
            // probable runtime exception to be raised when hitting the system limit
            err.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        System.out.println("Releasing platform threads...");
        releaseAllThreads();
        System.out.println("Managed to handle " + platformThreadsGenerated + " platform threads");
        System.out.println();

        // 'virtual threads' are orchestrated within JVM
        // => potentially unlimited
        System.out.println("Virtual threads");
        System.out.println("---------------");
        releaseThreads = false;
        int virtualThreadsGenerated = 0;
        try {
            while (virtualThreadsGenerated < threadCount) {
                Thread.ofVirtual().start(new SimpleTask("virtual-" + virtualThreadsGenerated));
                virtualThreadsGenerated++;
                if (virtualThreadsGenerated % 1000 == 0) {
                    System.out.println(virtualThreadsGenerated + " threads started");
                }
            }
        } catch (OutOfMemoryError err) {
            // this shouldn't happen with virtual threads
            err.printStackTrace(System.err);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        System.out.println("Releasing virtual threads...");
        releaseAllThreads();
        System.out.println("Managed to handle " + virtualThreadsGenerated + " virtual threads");
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
