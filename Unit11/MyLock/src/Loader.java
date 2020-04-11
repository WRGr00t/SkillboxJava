public class Loader {
    static int c = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            MyLock lock = new MyLock(Thread.currentThread(), new Object());
            for (int i = 0; i < 10_000; i++) {
                try {
                    lock.lock();
                    System.out.println("up " + c + " " + Thread.currentThread().getName());
                    c++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            MyLock lock = new MyLock(Thread.currentThread(), new Object());
            for (int i = 0; i < 10_000; i++) {
                try {
                    lock.lock();
                    System.out.println("down " + c + " " + Thread.currentThread().getName());
                    c--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(c);
    }
}