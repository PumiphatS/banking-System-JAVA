public class User {
    private String username;
    private Account spending;
    private Account savings;

    public User(String username) {
        this.username = username;
        this.spending = new Account("Spending");
        this.savings = new Account("Savings");
    }

    public String getUsername() {
        return username;
    }

    public Account getSpending() {
        return spending;
    }

    public Account getSavings() {
        return savings;
    }

    public boolean transferBetweenOwn(String from, String to, double amount) {
        Account source = from.equalsIgnoreCase("Spending") ? spending : savings;
        Account target = to.equalsIgnoreCase("Spending") ? spending : savings;

        if (source != target && source.withdraw(amount)) {
            target.deposit(amount);
            return true;
        }
        return false;
    }

    public boolean transferTo(User targetUser, String toType, double amount) {
        if (spending.withdraw(amount)) {
            if (toType.equalsIgnoreCase("Spending")) {
                targetUser.getSpending().deposit(amount);
            } else {
                targetUser.getSavings().deposit(amount);
            }
            return true;
        }
        return false;
    }
}
