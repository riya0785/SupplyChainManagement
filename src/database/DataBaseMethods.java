package database;

import java.sql.Connection;

public abstract class DataBaseMethods extends DBConnector{
	
	Connection userconnect = connectUserDB(); 
	
	Connection iconnect = connectInventoryDB();
	

	



}
