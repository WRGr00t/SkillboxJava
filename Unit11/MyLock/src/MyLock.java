class MyLock {
    private boolean isLock = false;
    private Thread curThread = new Thread();

    public MyLock(boolean isLock, Thread curThread) {
        this.isLock = isLock;
        this.curThread = curThread;
    }

    public MyLock() {
    }

    public synchronized void updateMoney(Account account, long money) {
        while (!isLock && curThread != Thread.currentThread()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (money > 0) {
            account.addMoney(money);
            System.out.printf("Произведено зачисление %d на счет№ %d", money, account);
        } else if (money < 0) {
            account.deductMoney(money);
            System.out.printf("Произведено зачисление %d на счет№ %d", money, account);
        }
        else {
            System.out.println("Сумма нулевая");
        }
        notify();
    }

    public synchronized void lock(Account account) {
        account.setLock(new MyLock(true, Thread.currentThread()));
    }

    public synchronized void unLock(Account account){
        account.setLock(new MyLock(false, Thread.currentThread()));
    }
}
