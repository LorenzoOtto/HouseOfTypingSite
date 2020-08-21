package houseoftyping.domain;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

import be.feelio.mollie.data.payment.PaymentResponse;
import houseoftyping.sql.SQLConnection;
import houseoftyping.utils.EmailUtil;

public class Registration {

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
	private ArrayList<SecondRegistration> secondRegistrations;
	private String paymentOption;
	private String bank;
	private String nameAccountHolder;
	private String iban;
	private LocalDateTime mandateDate;
	private String totalPrice;
	private String price;
	private String mollieId;
	private PaymentResponse molliePayment;

	public Registration(String information, String secondInformation) {
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
		this.bank = registration[index++];
		this.nameAccountHolder = registration[index++];
		this.iban = registration[index++];
		this.paymentOption = registration[index++];
		this.setTotalPrice(registration[index++]);
		this.price = registration[index++];
		this.mandateDate = LocalDateTime.now();
		this.secondRegistrations = createSecondUsers(secondInformation);
	}

	public void sendWelcomeMail() throws MessagingException {
		EmailUtil.sendWelcomeMail(email, createArrayListOfNames(), firstName);
		if (this.courseCode != null) {
			SQLConnection sql = new SQLConnection();
			Course course = sql.findCourse(courseCode);
			if (course.getEmail() != null) {
				EmailUtil.sendRegistrationsToSchool(course.getEmail(), createArrayListOfNames(),
						sql.getAmountOfRegistrationsOfCourse(this.courseCode));
			}
		}
	}

	public void sendWaitingListMail() throws MessagingException {
		EmailUtil.sendWaitingListMail(email, createArrayListOfNames(), firstName);
		if (this.courseCode != null) {
			SQLConnection sql = new SQLConnection();
			Course course = sql.findCourse(courseCode);
			if (course.getEmail() != null) {
				EmailUtil.sendWaitingListToSchool(course.getEmail(), createArrayListOfNames(), course.getSchoolName(),
						sql.getAmountOfWaitersOfCourse(this.courseCode));
			}
		}
	}

	public void save() throws SQLException {
		SQLConnection sql = new SQLConnection();
		sql.saveCompleteRegistration(this);
	}

	public void saveSecondRegistration() {
		SQLConnection sql = new SQLConnection();
		sql.saveSecondRegistrations(this);
	}

	public void createMolliePayment() {
		Transaction transaction = new Transaction();
		this.molliePayment = transaction.createPayment(this.price, this.bank);
		this.mollieId = this.molliePayment.getId();
	}

	public ArrayList<String> createArrayListOfNames() {
		ArrayList<String> names = new ArrayList<String>();
		if (insertion.equals("null")) {
			names.add(firstName + " " + lastName);
		} else {
			names.add(firstName + " " + insertion + " " + lastName);
		}
		if (!secondRegistrations.isEmpty()) {
			for (SecondRegistration s : secondRegistrations) {
				if (insertion.equals("null")) {
					names.add(s.getFirstName() + " " + s.getLastName());
				} else {
					names.add(s.getFirstName() + " " + s.getInsertion() + " " + s.getLastName());
				}
			}
		}
		return names;
	}

	public ArrayList<SecondRegistration> createSecondUsers(String secondInformation) {
		ArrayList<SecondRegistration> secondUsers = new ArrayList<SecondRegistration>();
		List<String> secondRegistrations = Arrays.asList(secondInformation.split(":"));
		for (String reg : secondRegistrations) {
			if (reg != "") {
				List<String> registration = Arrays.asList(reg.split(","));
				int index = 0;
				SecondRegistration s = new SecondRegistration(registration.get(index++), registration.get(index++),
						registration.get(index++), registration.get(index++), registration.get(index++), registration.get(index++));
				secondUsers.add(s);
			}
		}
		return secondUsers;
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

	public ArrayList<SecondRegistration> getSecondCourses() {
		return secondRegistrations;
	}

	public void setSecondCourses(ArrayList<SecondRegistration> secondCourses) {
		this.secondRegistrations = secondCourses;
	}

	public String getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getNameAccountHolder() {
		return nameAccountHolder;
	}

	public void setNameAccountHolder(String nameAccountHolder) {
		this.nameAccountHolder = nameAccountHolder;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public LocalDateTime getMandateDate() {
		return mandateDate;
	}

	public void setMandateDate(LocalDateTime mandateDate) {
		this.mandateDate = mandateDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMollieId() {
		return mollieId;
	}

	public void setMollieId(String mollieId) {
		this.mollieId = mollieId;
	}

	public PaymentResponse getMolliePayment() {
		return molliePayment;
	}

	public void setMolliePayment(PaymentResponse molliePayment) {
		this.molliePayment = molliePayment;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Registration [courseCode=" + courseCode + ", gender=" + gender + ", birthDay=" + birthDay
				+ ", firstName=" + firstName + ", insertion=" + insertion + ", lastName=" + lastName + ", parentsName="
				+ parentsName + ", address=" + address + ", addressNr=" + addressNr + ", zipCode=" + zipCode + ", town="
				+ town + ", email=" + email + ", phoneNumber=" + phoneNumber + ", secondRegistrations="
				+ secondRegistrations + ", paymentOption=" + paymentOption + ", bank=" + bank + ", nameAccountHolder="
				+ nameAccountHolder + ", iban=" + iban + ", mandateDate=" + mandateDate + ", totalPrice=" + totalPrice
				+ ", price=" + price + ", mollieId=" + mollieId + ", molliePayment=" + molliePayment + "]";
	}
	
}
