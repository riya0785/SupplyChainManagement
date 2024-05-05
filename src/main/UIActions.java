package main;

public interface UIActions {
	
	public void loginButtons();
	
	public void loginFields();
	
	public void registrationFields();
	
	public void dashboard(String x);
	
	public boolean register(String username, String password);
	
	public void refreshInventoryTable(String x);


}
