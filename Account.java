public class Account {
    private double balance;
    private final String type;

    public Account(String type) {
        this.type = type;
        this.balance = 0.0;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public String getType() {
        return type;
    }
}
