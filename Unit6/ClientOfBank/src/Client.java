public abstract class Client {
    private double bankAccount;

    public double getBankAccount() {
        return bankAccount;
    }

    protected void setBankAccount(double bankAccount) {
        this.bankAccount = bankAccount;
    }
    public abstract void giveOut(double sum);

    public abstract void putOn(double sum);

    public void printAmount(){
    }
}
