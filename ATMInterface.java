import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<Transaction> transactions;

    public Account(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String pin) {
        return this.pin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
        System.out.println("$" + amount + " deposited successfully.");
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount));
            System.out.println("$" + amount + " withdrawn successfully.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void transfer(double amount, Account recipient) {
        if (balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            transactions.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
            System.out.println("$" + amount + " transferred to " + recipient.getUserId() + " successfully.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void printTransactions() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
        }
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create user account
        Account userAccount = new Account("user123", "1234");

        // ATM login
        System.out.print("Enter User ID: ");
        String userIdInput = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pinInput = scanner.nextLine();

        if (!userAccount.getUserId().equals(userIdInput) || !userAccount.validatePin(pinInput)) {
            System.out.println("Invalid User ID or PIN. Exiting...");
            System.exit(0);
        }

        boolean running = true;
        while (running) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    userAccount.printTransactions();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    userAccount.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    userAccount.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    String recipientId = scanner.nextLine();
                    // Consume newline character
                    scanner.nextLine();
                    System.out.print("Enter amount to transfer: $");
                    double transferAmount = scanner.nextDouble();
                    Account recipientAccount = new Account(recipientId, ""); // Placeholder pin
                    userAccount.transfer(transferAmount, recipientAccount);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }

        System.out.println("Thank you for using the ATM. Goodbye!");
        scanner.close();
    }
}
