package javaproj;

//OOP-Based Banking System in Java

import java.util.*;

//Interface for Account operations
interface AccountOperations {
 void deposit(double amount);
 void withdraw(double amount);
 double getBalance();
}

//Abstract Bank Account Class
abstract class BankAccount implements AccountOperations {
 protected String accountNumber;
 protected String holderName;
 protected double balance;

 public BankAccount(String accNum, String name, double initialBalance) {
     this.accountNumber = accNum;
     this.holderName = name;
     this.balance = initialBalance;
 }

 public String getAccountNumber() {
     return accountNumber;
 }

 public String getHolderName() {
     return holderName;
 }

 public double getBalance() {
     return balance;
 }

 public void deposit(double amount) {
     balance += amount;
     System.out.println("Deposited: " + amount);
 }

 public abstract void withdraw(double amount);

 public abstract void displayAccountType();
}

//SavingsAccount Class
class SavingsAccount extends BankAccount {
 public SavingsAccount(String accNum, String name, double initialBalance) {
     super(accNum, name, initialBalance);
 }

 public void withdraw(double amount) {
     if (amount <= balance) {
         balance -= amount;
         System.out.println("Withdrawn: " + amount);
     } else {
         System.out.println("Insufficient funds!");
     }
 }

 public void displayAccountType() {
     System.out.println("Account Type: Savings");
 }
}

//CurrentAccount Class
class CurrentAccount extends BankAccount {
 private double overdraftLimit = 1000;

 public CurrentAccount(String accNum, String name, double initialBalance) {
     super(accNum, name, initialBalance);
 }

 public void withdraw(double amount) {
     if (amount <= balance + overdraftLimit) {
         balance -= amount;
         System.out.println("Withdrawn: " + amount);
     } else {
         System.out.println("Overdraft limit exceeded!");
     }
 }

 public void displayAccountType() {
     System.out.println("Account Type: Current");
 }
}

//Admin Interface
interface AdminOperations {
 void addAccount(BankAccount acc);
 void removeAccount(String accNo);
 void listAccounts();
}

//Admin Class
class Admin implements AdminOperations {
 private List<BankAccount> accounts = new ArrayList<>();

 public void addAccount(BankAccount acc) {
     accounts.add(acc);
     System.out.println("Account added: " + acc.getAccountNumber());
 }

 public void removeAccount(String accNo) {
     accounts.removeIf(acc -> acc.getAccountNumber().equals(accNo));
     System.out.println("Account removed: " + accNo);
 }

 public void listAccounts() {
     for (BankAccount acc : accounts) {
         System.out.println("Account No: " + acc.getAccountNumber() + ", Name: " + acc.getHolderName() + ", Balance: " + acc.getBalance());
         acc.displayAccountType();
         System.out.println("---------------------------");
     }
 }

 public BankAccount findAccount(String accNo) {
     for (BankAccount acc : accounts) {
         if (acc.getAccountNumber().equals(accNo)) return acc;
     }
     return null;
 }
}

//Main Class with Menu
public class BankingSystem {
 public static void main(String[] args) {
     Scanner sc = new Scanner(System.in);
     Admin admin = new Admin();

     while (true) {
         System.out.println("\n=== Banking System Menu ===");
         System.out.println("1. Add Account");
         System.out.println("2. Remove Account");
         System.out.println("3. List Accounts");
         System.out.println("4. Deposit");
         System.out.println("5. Withdraw");
         System.out.println("6. Exit");
         System.out.print("Choose an option: ");

         int choice = sc.nextInt();
         sc.nextLine();

         switch (choice) {
             case 1:
                 System.out.print("Enter Account Number: ");
                 String accNo = sc.nextLine();
                 System.out.print("Enter Name: ");
                 String name = sc.nextLine();
                 System.out.print("Initial Balance: ");
                 double balance = sc.nextDouble();
                 sc.nextLine();
                 System.out.print("Type (savings/current): ");
                 String type = sc.nextLine();
                 BankAccount acc = type.equalsIgnoreCase("savings") ? new SavingsAccount(accNo, name, balance) : new CurrentAccount(accNo, name, balance);
                 admin.addAccount(acc);
                 break;
             case 2:
                 System.out.print("Enter Account Number to Remove: ");
                 admin.removeAccount(sc.nextLine());
                 break;
             case 3:
                 admin.listAccounts();
                 break;
             case 4:
                 System.out.print("Enter Account Number: ");
                 BankAccount depositAcc = admin.findAccount(sc.nextLine());
                 if (depositAcc != null) {
                     System.out.print("Amount to Deposit: ");
                     depositAcc.deposit(sc.nextDouble());
                 } else {
                     System.out.println("Account not found.");
                 }
                 break;
             case 5:
                 System.out.print("Enter Account Number: ");
                 BankAccount withdrawAcc = admin.findAccount(sc.nextLine());
                 if (withdrawAcc != null) {
                     System.out.print("Amount to Withdraw: ");
                     withdrawAcc.withdraw(sc.nextDouble());
                 } else {
                     System.out.println("Account not found.");
                 }
                 break;
             case 6:
                 System.out.println("Thank you for using the Banking System!");
                 sc.close();
                 return;
             default:
                 System.out.println("Invalid choice. Try again.");
         }
     }
 }
}