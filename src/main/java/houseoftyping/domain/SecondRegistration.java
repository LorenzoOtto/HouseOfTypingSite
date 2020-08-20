package houseoftyping.domain;

public class SecondRegistration {

	private String courseCode;
	private String gender;
	private String birthday;
	private String firstName;
	private String insertion;
	private String lastName;
	private String price;

	public SecondRegistration(String gender, String birthday, String firstName, String insertion, String lastName,
			String price) {
		switch (price) {
		case "55,00":
			this.courseCode = "ADNM50000";
			break;
		case "60,00":
			this.courseCode = "AYNM50000";
			break;
		case "87,00":
			this.courseCode = "AZNM50000";
			break;
		default:
			this.courseCode = "AWNM50000";
			break;
		}
		this.gender = gender;
		this.birthday = birthday;
		this.firstName = firstName;
		this.insertion = insertion;
		this.lastName = lastName;
		this.price = price;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	@Override
	public String toString() {
		return "SecondUser [gender=" + gender + ", birthday=" + birthday + ", firstName=" + firstName + ", insertion="
				+ insertion + ", lastName=" + lastName + "]";
	}

}
