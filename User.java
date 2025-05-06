// user class to hold balance info and update it accordingly
public class User {
    public String username;
    public double checkingBalance;
    public double savingsBalance;
    public double totalDeposited;
    public double totalTransferred;

    public User(String username, double checkingBalance, double savingsBalance,
                double totalDeposited, double totalTransferred) {
        this.username = username;
        this.checkingBalance = checkingBalance;
        this.savingsBalance = savingsBalance;
        this.totalDeposited = totalDeposited;
        this.totalTransferred = totalTransferred;
    }
}
