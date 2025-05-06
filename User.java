public class User {
    private String username;
    private String password;
    private double checkingBalance;
    private double savingsBalance;

    public User(String username, double checking, double savings) {
        this.username = username;
        this.password = ""; // default blank
        this.checkingBalance = checking;
        this.savingsBalance = savings;
    }

    public User(String username, String password, double checking, double savings) {
        this.username = username;
        this.password = password;
        this.checkingBalance = checking;
        this.savingsBalance = savings;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public double getCheckingBalance() { return checkingBalance; }
    public void setCheckingBalance(double checkingBalance) { this.checkingBalance = checkingBalance; }

    public double getSavingsBalance() { return savingsBalance; }
    public void setSavingsBalance(double savingsBalance) { this.savingsBalance = savingsBalance; }
}
