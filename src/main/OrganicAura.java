package main;

import java.util.Scanner;

import inventory.Admin;
import inventory.Clients;
import users.AdminUser;
import users.ClientUser;

public class OrganicAura {
	// ---------------------------------- MAIN
	// ----------------------------------------------
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int choice;
		int loginchoice;

		while (true) {
			choice = displayRegister(scanner);

			if (choice == 2) {
				register();
				continue;
			} else if (choice == 0) {
				System.out.println("Exiting...");
				break;
			} else {
				loginchoice = displayLogin(scanner);

				login(loginchoice);

			}

		}
		scanner.close();
	}

	// ----------------------------------END
	// MAIN------------------------------------------------

	// ----------------------------Additional
	// Methods-------------------------------------------

	private static int displayRegister(Scanner scan) {
		System.out.println("Choose");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("0. Exit");
		int choice = getUserInput(scan, 2);
		return choice;
	}

	// Method to display the main menu
	private static int displayLogin(Scanner scan) {
		System.out.println("\nLogin In as:");
		System.out.println("1. Client");
		System.out.println("2. Admin");
		System.out.println("0. Return");
		int choice = getUserInput(scan, 2);
		return choice;
	}

	// Method to get user input with error handling
	public static int getUserInput(Scanner scanner, int range) {
		int choice = -1;
		boolean validInput = false;

		while (!validInput) {
			System.out.print("Enter your choice: ");
			try {
				choice = scanner.nextInt();

				if (choice < 0) {
					throw new choiceOutOfBoundsException("Negative inputs are not accepted. Enter a valid choice");
				} else if (choice > range) {
					throw new choiceOutOfBoundsException("Choice is Out of Bounds. Enter a valid choice");
				} else {
					validInput = true;
				}
			} catch (java.util.InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
			} catch (choiceOutOfBoundsException e) {
				e.getMessage();
			}
		}
		return choice;
	}

	private static void register() {
		ClientUser user = new ClientUser();
		user.add();
	}

	private static void login(int choice) {

		Scanner scan = new Scanner(System.in);

		System.out.println("Enter Username");
		String username = scan.next();
		System.out.println("Enter Password");
		String pwd = scan.next();

		if (choice == 1) {
			ClientUser user = new ClientUser();
			user.setUsername(username);
			user.setPassword(pwd);
			int exist = user.authenticate();

			if (exist > 0) {
				System.out.println("Login Successfull");
				Clients client = new Clients();
				client.buy();
			} else {
				System.out.println("Login Failed");
				return;
			}

		} else if (choice == 2) {
			AdminUser user = new AdminUser();

			user.setUsername(username);
			user.setPassword(pwd);

			int exist = user.authenticate();
			if (exist > 0) {
				System.out.println("Login Successfull");
				adminfunctions();

			} else {
				System.out.println("\n** X ** Login Failed ** X **\n");
				return;
			}

		} else {
			return;
		}

	}

	private static void adminfunctions() {

		System.out.println("Welcome to Admin Console");
		Scanner scan = new Scanner(System.in);
		int choice = -1;

		AdminUser user = new AdminUser();
		Admin actions = new Admin();

		while (choice != 0) {
			System.out.println("What would you like to do?");
			System.out.println("1. Add another admin user");
			System.out.println("2. Add items in the inventory");
			System.out.println("3. Show the Inventory");
			System.out.println("4. Update items in the inventory");
			System.out.println("5. Delete items in the inventory");
			System.out.println("0. Logout");
			choice = getUserInput(scan, 5);

			switch (choice) {
			case 1:
				user.add();
				break;

			case 2:
				actions.add();
				break;

			case 3:
				actions.show();
				break;

			case 4:
				actions.update();
				break;

			case 5:
				actions.delete();
				break;

			case 0:
				break;

			}
		}

	}

	public static class choiceOutOfBoundsException extends Exception {
		private static final long serialVersionUID = 1L;

		public choiceOutOfBoundsException() {
			super();
		}

		public choiceOutOfBoundsException(String message) {
			super(message);
		}
	}

}
