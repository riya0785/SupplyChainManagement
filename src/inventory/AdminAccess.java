//package inventory;
//
//import java.util.Scanner;
//
//public class AdminAccess {
//
//	Inventory i = new Inventory();
//
//	public void add() {
//		Scanner scan = new Scanner(System.in);
//		try {
//			System.out.print("Product ID: ");
//			i.setProductId(scan.nextInt());
//			System.out.print("\nItem: ");
//			i.setItem(scan.next());
//			System.out.print("\nPrice: ");
//			i.setPrice(scan.nextFloat());
//			System.out.print("\nQuantity : ");
//			i.setQuantity(scan.nextInt());
//			i.add();
//		}catch(Exception e) {
//			e.getMessage();
//		}
//
//	}
//
//
//	public void update() {
//		// Getting product ID from the user for the query
//		Scanner scan = new Scanner(System.in);
//
//		try {
//			System.out.print("Product ID: ");
//			i.setProductId(scan.nextInt());
//			System.out.print("\nItem: ");
//			i.setItem(scan.next());
//			System.out.print("\nPrice: ");
//			i.setPrice(scan.nextFloat());
//			System.out.print("\nQuantity : ");
//			i.setQuantity(scan.nextInt());
//
//			i.update();
//		}catch(Exception e) {
//			e.getMessage();
//		}
//
//	}
//
//	public void delete() {
//		Scanner scan = new Scanner(System.in);
//
//		try {
//			System.out.print("Product ID: ");
//			i.setProductId(scan.nextInt());
//			i.delete();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	public void showOrders() {
//		Orders o = new Orders();
//		o.showOrders();
//	}
//	
//	public void show() {
//		i.showProducts();
//	}
//
//}
