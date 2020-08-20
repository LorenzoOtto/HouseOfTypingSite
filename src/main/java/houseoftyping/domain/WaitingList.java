package houseoftyping.domain;

import java.time.LocalDateTime;

import houseoftyping.sql.SQLConnection;

public class WaitingList {
	private String courseCode;
	private String gender;
	private String birthDay;
	private String firstName;
	private String insertion;
	private String lastName;
	private String parentsName;
	private String address;
	private String addressNr;
	private String zipCode;
	private String town;
	private String email;
	private String phoneNumber;
	private String price;
	private LocalDateTime mandateDate;
	
	public WaitingList (String information) {
		int index = 0;
		String[] registration = information.split(",");
		this.courseCode = registration[index++];
		this.gender = registration[index++];
		this.birthDay = registration[index++];
		this.firstName = registration[index++];
		this.insertion = registration[index++];
		this.lastName = registration[index++];
		this.parentsName = registration[index++];
		this.address = registration[index++];
		this.town = registration[index++];
		this.addressNr = registration[index++];
		this.zipCode = registration[index++];
		this.email = registration[index++];
		this.phoneNumber = registration[index++];
		this.price = registration[index++];
		this.mandateDate = LocalDateTime.now();
	}
	
	public WaitingList (Registration registration) {
		this.courseCode = registration.getCourseCode();
		this.gender = registration.getGender();
		this.birthDay = registration.getBirthDay();
		this.firstName = registration.getFirstName();
		this.insertion = registration.getInsertion();
		this.lastName = registration.getLastName();
		this.parentsName = registration.getParentsName();
		this.address = registration.getAddress();
		this.town = registration.getTown();
		this.addressNr = registration.getAddressNr();
		this.zipCode = registration.getZipCode();
		this.email = registration.getEmail();
		this.phoneNumber = registration.getPhoneNumber();
		this.price = registration.getPrice();
		this.mandateDate = LocalDateTime.now();
	}
	
	public void save() {
		SQLConnection sql = new SQLConnection();
		sql.saveWaitingList(this);
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getInsertion() {
		return insertion;
	}

	public void setInsertion(String insertion) {
		this.insertion = insertion;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getParentsName() {
		return parentsName;
	}

	public void setParentsName(String parentsName) {
		this.parentsName = parentsName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressNr() {
		return addressNr;
	}

	public void setAddressNr(String addressNr) {
		this.addressNr = addressNr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public LocalDateTime getMandateDate() {
		return mandateDate;
	}

	public void setMandateDate(LocalDateTime mandateDate) {
		this.mandateDate = mandateDate;
	}
}