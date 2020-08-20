package houseoftyping.domain;

import houseoftyping.sql.SQLConnection;
import houseoftyping.utils.PasswordStorage;

public class User {
	private String username;
	private String password;
	private int lastExport;
	private int lastExportAdministration;

	public User(String username, String password) {
		super();
		this.username = username;
		try {
			this.password = PasswordStorage.createHash(password);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.lastExport = 0;
		this.lastExportAdministration = 0;
	}
	
	public User(String username, String password, int lastExport, int lastExportAdministration) {
		super();
		this.username = username;
		this.password = password;
		this.lastExport = lastExport;
		this.lastExportAdministration = lastExportAdministration;
	}
	
	public void save() {
		SQLConnection sql = new SQLConnection();
		sql.saveUser(this);
	}

	public int getLastExport() {
		return lastExport;
	}

	public void setLastExport(int lastExport) {
		this.lastExport = lastExport;
	}

	public int getLastExportAdministration() {
		return lastExportAdministration;
	}

	public void setLastExportAdministration(int lastExportAdministration) {
		this.lastExportAdministration = lastExportAdministration;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
