//package inventory;
//
//import java.util.Scanner;
//
//import main.OrganicAura;
//
//public class ClientAccess extends OrganicAura {
//
//	Inventory i = new Inventory();
//	Orders o = new Orders();
//	private String username;
//	
//	public void buy() {
//		Scanner scan = null;
//		int quantity, productId;
//		float totalPrice = 0;
//		boolean cont = true;
//
//		while (cont) {
//			try {
//				scan = new Scanner(System.in);
//				i.showProducts();
//				o.setUsername(getUsername());  // Updating the Username to the orders
//				
//				System.out.println("\nWhat would you like to buy?");
//				System.out.print("Product ID: ");
//				productId = scan.nextInt(); 
//				i.setProductId(productId);  //updates the productId in the Inventory 
//				o.setProductId(productId);  //add the productId in the Orders
//				
//				System.out.print("Quantity : ");
//				quantity = scan.nextInt();
//				o.setOrderQty(quantity);    // updates the orderQty in the Orders 
//				i.read();                   // calls the read method to set all the other values regarding the product for eg. item,.
//
//				if (quantity > i.getQuantity()) {
//					throw new QuantityOutOfBoundsException("\nQuantity Exceeds the Stock");
//				} else if (quantity < 1) {
//					throw new QuantityOutOfBoundsException("\nQuantity Cannot be Less than 1");
//
//				} else {
//					totalPrice = totalPrice + quantity * i.getPrice();
//					o.setPrice(i.getPrice()); // updates the price in the orders
//					o.setItem(i.getItem()); // updates the items in the orders
//					i.deduct(quantity);
//					
//					o.add(); // calls the add method to add the order into the table
//
//					System.out.println("Do you want to continue shopping?");
//					System.out.println("1. Continue Shopping");
//					System.out.println("0. Checkout Total");
//
//					int choice = getUserInput(1);
//
//					if (choice == 0) {
//						cont = false;
//						break;
//					}
//				}
//
//			} catch (QuantityOutOfBoundsException e) {
//				// Handle the QuantityOutOfBoundsException here
//				System.err.print(e.getMessage()+"\n");
//				
//				try {
//				    Thread.sleep(1 * 1000);
//				} catch (InterruptedException ie) {
//				    Thread.currentThread().interrupt();
//				}
//			}
//
//		}
//		System.out.println("Total Price: " + totalPrice);
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public static class QuantityOutOfBoundsException extends Exception {
//		private static final long serialVersionUID = 1L;
//
//		public QuantityOutOfBoundsException() {
//			super();
//		}
//
//		public QuantityOutOfBoundsException(String message) {
//			super(message);
//		}
//	}
//}
