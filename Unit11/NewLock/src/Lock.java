public class Lock {

    private Object locker = new Object();

    public void lock() throws InterruptedException {
        synchronized (this) {
            locker.wait();
        }
    }

    public void unlock() {
        synchronized (this) {
            locker.notifyAll();
        }
    }
}