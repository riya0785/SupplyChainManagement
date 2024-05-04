package inventory;

import java.util.Scanner;

import main.OrganicAura;

public class Clients extends OrganicAura {

	Inventory i = new Inventory();

	public void buy() {
		Scanner scan = null;
		int quantity;
		float totalPrice = 0;
		boolean cont = true;

		while (cont) {
			try {
				scan = new Scanner(System.in);
				i.show();
				System.out.println("\nWhat would you like to buy?");
				System.out.print("Product ID: ");
				i.setProductId(scan.nextInt());
				System.out.print("Quantity : ");
				quantity = scan.nextInt();
				i.read();

				if (quantity > i.getQuantity()) {
					throw new QuantityOutOfBoundsException("Quantity Exceeds the Stock");
				} else if (quantity < 1) {
					throw new QuantityOutOfBoundsException("Quantity Cannot be Less than 1");

				} else {
					totalPrice = quantity * i.getPrice();
					i.deduct(quantity);

					System.out.println("Do you want to continue shopping?");
					System.out.println("1. Continue Shopping");
					System.out.println("0. Checkout Total");

					int choice = getUserInput(scan, 1);

					if (choice == 0) {
						cont = false;
						break;
					}
				}

			} catch (QuantityOutOfBoundsException e) {
				// Handle the QuantityOutOfBoundsException here
				System.out.println(e.getMessage());
			}

		}
		System.out.println("Total Price: " + totalPrice);
	}

	public static class QuantityOutOfBoundsException extends Exception {
		private static final long serialVersionUID = 1L;

		public QuantityOutOfBoundsException() {
			super();
		}

		public QuantityOutOfBoundsException(String message) {
			super(message);
		}
	}
}
