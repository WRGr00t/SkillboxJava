public class Account {
    private long money;
    private String accNumber;
    private boolean isBlocked;

    public Account(long money, String accNumber, boolean isBlocked) {
        this.money = money;
        this.accNumber = accNumber;
        this.isBlocked = isBlocked;
    }

    public Account(long money, String accNumber) {
        this(money, accNumber, false);
    }

    public Account() {
    }

    public long getMoney() {
        return money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public synchronized void addMoney(long money){
        this.money = this.money+ money;
    }

    public synchronized void deductMoney(long money){
        if (money <= this.getMoney()){
            this.money = this.money - money;
        }
        else {
            System.out.println("Недостаточно средств");
        }
    }
}
