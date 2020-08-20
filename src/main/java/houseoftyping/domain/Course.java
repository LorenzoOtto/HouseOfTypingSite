package houseoftyping.domain;

import java.util.List;

import houseoftyping.sql.SQLConnection;

public class Course {
	private String courseCode;
	private String schoolName;
	private String schoolNameWithoutArticle;
	private String coursePrice;
	private String secondCoursePrice;
	private boolean courseFound;
	private String email;
	private int maximumRegistrations;
	private int currentRegistrations;

	public Course() {
	}

	public Course(String courseCode, String schoolName, String schoolNameWithoutArticle, String coursePrice,
			String secondCoursePrice, String email, int maximumRegistrations, int currentRegistrations) {
		super();
		this.courseCode = courseCode;
		this.schoolName = schoolName;
		this.schoolNameWithoutArticle = schoolNameWithoutArticle;
		this.coursePrice = coursePrice;
		this.secondCoursePrice = secondCoursePrice;
		this.email = email;
		this.maximumRegistrations = maximumRegistrations;
		this.currentRegistrations = currentRegistrations;
	}

	public Course(List<String> values) {
		if (values.get(0).replaceAll("\\s", "").length() == 9) {
			int i = 0;
			this.courseCode = values.get(i++).replaceAll("\\s", "");
			this.schoolName = values.get(i++);
			this.schoolNameWithoutArticle = values.get(i++);
			if (values.get(i) == null) {
				i++;
				this.coursePrice = "0,00";
			} else {
				this.coursePrice = values.get(i++);
			}
			if (values.get(i) == null) {
				i++;
				this.secondCoursePrice = "0,00";
			} else {
				this.secondCoursePrice = values.get(i++);
			}
			if (values.get(i) == "") {
				this.email = null;
				i++;
			} else {
				this.email = values.get(i++);
			}
			if (values.get(i) == null) {
				this.maximumRegistrations = 0;
			} else {
				this.maximumRegistrations = Integer.parseInt(values.get(i++));
			}
			if (values.get(i) == null) {
				this.currentRegistrations = 0;
			} else {
				this.currentRegistrations = Integer.parseInt(values.get(i++));
			}
		}
	}

	public void save(SQLConnection sql) {
		sql.saveCourse(this);
	}

	public boolean isFull() {
		if (this.maximumRegistrations == this.currentRegistrations && maximumRegistrations > 0) {
			return true;
		}
		return false;
	}

	public boolean hasMaximum() {
		if (this.maximumRegistrations != 0) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return isFull() + ":" + schoolName + ":" + coursePrice + ":" + secondCoursePrice + ":" + courseCode;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolNameWithoutArticle() {
		return schoolNameWithoutArticle;
	}

	public void setSchoolNameWithoutArticle(String schoolNameWithoutArticle) {
		this.schoolNameWithoutArticle = schoolNameWithoutArticle;
	}

	public String getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(String coursePrice) {
		this.coursePrice = coursePrice;
	}

	public String getSecondCoursePrice() {
		return secondCoursePrice;
	}

	public void setSecondCoursePrice(String secondCoursePrice) {
		this.secondCoursePrice = secondCoursePrice;
	}

	public boolean isCourseFound() {
		return courseFound;
	}

	public void setCourseFound(boolean courseFound) {
		this.courseFound = courseFound;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getMaximumRegistrations() {
		return maximumRegistrations;
	}

	public void setMaximumRegistrations(int maximumRegistrations) {
		this.maximumRegistrations = maximumRegistrations;
	}

	public int getCurrentRegistrations() {
		return currentRegistrations;
	}

	public void setCurrentRegistrations(int currentRegistrations) {
		this.currentRegistrations = currentRegistrations;
	}

}