import java.time.LocalDate;

public class Operation {
    private String typeAccount;
    private String numberAccount;
    private String currency;
    private LocalDate transactionDate;
    private String referenceTransactions;
    private String operationDescription;
    private double credit;
    private double debit;

    public Operation(String typeAccount, String numberAccount, String currency, LocalDate transactionDate,
                     String referenceTransactions, String operationDescription, double credit, double debit){
        this.typeAccount  = typeAccount;
        this.numberAccount = numberAccount;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.referenceTransactions = referenceTransactions;
        this.operationDescription = operationDescription;
        this.credit = credit;
        this.debit = debit;
    }

    //==========Геттеры и сеттеры===========
    public String getTypeAccount() {
        return typeAccount;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public String getReferenceTransactions() {
        return referenceTransactions;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public double getCredit() {
        return credit;
    }

    public double getDebit() {
        return debit;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setReferenceTransactions(String referenceTransactions) {
        this.referenceTransactions = referenceTransactions;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public String getOrigin(){
        String origin = "";
        if (!operationDescription.equals("")){

        }
        return origin;
    }

}
