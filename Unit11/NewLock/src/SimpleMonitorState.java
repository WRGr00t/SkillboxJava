public class SimpleMonitorState {

    public static void main(String args[]) throws InterruptedException {

        SimpleMonitorState t = new SimpleMonitorState();
        SimpleRunnable m = new SimpleRunnable(t);
        Thread t1 = new Thread(m);
        t1.start();
        t.call();

    }

    public void call() throws InterruptedException {
        synchronized (this) {
            wait();
            System.out.println("Single by Threads ");
        }
    }

}

class SimpleRunnable implements Runnable {

    SimpleMonitorState t;

    SimpleRunnable(SimpleMonitorState t) {
        this.t = t;
    }

    @Override
    public void run() {

        try {
            // Sleep
            Thread.sleep(10000);
            synchronized (this.t) {
                this.t.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}