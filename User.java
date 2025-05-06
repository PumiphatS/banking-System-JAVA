public class User {
    private String username;
    private String password;
    private String field3;
    private String field4;
    private String field5;
    private double checkingBalance;
    private double savingsBalance;

    public User(String username, String password, String field3, String field4, String field5,
                double checkingBalance, double savingsBalance) {
        this.username = username;
        this.password = password;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.checkingBalance = checkingBalance;
        this.savingsBalance = savingsBalance;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getField3() { return field3; }
    public String getField4() { return field4; }
    public String getField5() { return field5; }

    public double getCheckingBalance() { return checkingBalance; }
    public void setCheckingBalance(double balance) { this.checkingBalance = balance; }

    public double getSavingsBalance() { return savingsBalance; }
    public void setSavingsBalance(double balance) { this.savingsBalance = balance; }
}
