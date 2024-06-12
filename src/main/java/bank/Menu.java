package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exception.amountException;

public class Menu {

  private Scanner scanner;

  public static void main(String[] arg) {
    System.out.println("WELCOME TO GLOBE BANK INTERNATIONAL");
    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();
    if (customer != null) {
      Account account = DataSource.getAccount(customer.getAccount_id());
      menu.showMenu(customer, account);
    }
    menu.scanner.close();

  }

  private Customer authenticateUser() {
    System.out.println("Enter username:");
    String username = scanner.next();
    System.out.println("Enter your password:");
    String password = scanner.next();

    Customer customer = null;

    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("Error:" + e.getMessage());
    }
    return customer;

  }

  private void showMenu(Customer customer, Account account) {
    int selection = 0;
    while (selection != 4 && customer.isAuthenticated()) {
      System.out.println("============================");
      System.out.println("please select one of the following options:");
      System.out.println("1.Deposit");
      System.out.println("2.Withdraw");
      System.out.println("3.Check Balance");
      System.out.println("4.Exit");
      System.out.println("============================");

      selection = scanner.nextInt();
      double amount = 0;
      switch (selection) {
        case 1:
          System.out.println("Enter the amount to deposit:");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (amountException e) {
            System.out.println(e.getMessage());
            System.out.println("Try again");
          }
          break;
        case 2:
          System.out.println("Enter the amount to withdraw:");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (amountException e) {
            System.out.println(e.getMessage());
            System.out.println("Try Again!");
          }
          break;
        case 3:
          System.out.println("Balance:" + account.getBalance());
          break;
        case 4:
          Authenticator.logout(customer);
          System.out.println("Thanks for banking with us!");
          break;
        default:
          System.out.println("Invalid option.Try Again!");
          break;
      }
    }
  }

}
