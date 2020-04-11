import java.util.concurrent.locks.ReentrantLock;

public class MyLock {

    private volatile Thread owner;
    private Object object;
    //private int depth = 0;

    public MyLock(Thread owner, Object object) {
        this.owner = owner;
        this.object = object;
        //this.depth = 0;
    }


    public void lock() throws InterruptedException {
        if (owner != Thread.currentThread()) {
            synchronized (this) {
                object.wait();
            }
        } else {
            //depth++;
        }
    }

    public void unlock() {
        try{
            if (owner != Thread.currentThread()){
                synchronized (object) {
                    object.notify();
                }
            } else {
                synchronized (object) {
                    //depth = 0;
                    object.notify();
                }
            }
        } finally {
            synchronized (object){
                object.notifyAll();
            }
        }
    }
}