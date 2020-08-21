package houseoftyping.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import houseoftyping.domain.Course;
import houseoftyping.domain.Registration;
import houseoftyping.domain.SecondRegistration;
import houseoftyping.domain.User;
import houseoftyping.domain.WaitingList;
import houseoftyping.utils.PasswordStorage;

public class SQLConnection {
	private static ResourceBundle rb = ResourceBundle.getBundle("sql");
	private Connection con;

	public SQLConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/houseoftyping?autoReconnect=true&useSSL=false&createDatabaseIfNotExist=true&amp&serverTimezone=Europe/Amsterdam",
					rb.getString("sql.username"), rb.getString("sql.password"));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String username, String password) {
		String query = "SELECT * FROM user where username=?";
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, username);
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				if (PasswordStorage.verifyPassword(password, rs.getString(2)))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public User findUserByName(String username) {
		String query = "SELECT * FROM user where username=?";
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, username);
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return new User(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean checkIfCourseExists(String courseCode) {
		String query = "SELECT * FROM course where course_code=?";
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, courseCode);
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public XSSFWorkbook fillWorkbookWithAllRegistrations(XSSFWorkbook workbook, User user, boolean lastRegistration) {
		XSSFSheet sheet = workbook.createSheet("Inschrijven-site");
		int rowCount = 0;
		Row headerRow = sheet.createRow(rowCount++);
		int columnCount = 0;
		String allCellHeaderNames = "Cursuscode,Geslacht,Geboortedatum,Voornaam,Tussenvoegsel,Achternaam,Naam Ouders,Adres,Stad,Adres nummer,Postcode,E-mailadres,Telefoonnummer,IBAN,Ter name van,Betalingsoptie,Cursusprijs,Mandaatdatum,Via Mollie betaald(0=nee 1=ja)";
		String[] cellHeaderNames = allCellHeaderNames.split(",");
		for (int i = 0; i < 19; i++) {
			Cell cell = headerRow.createCell(columnCount++);
			cell.setCellValue(cellHeaderNames[i]);
		}
		String query = "SELECT * FROM registration";
		CellStyle oddPriceColor = workbook.createCellStyle();
		oddPriceColor.setFillBackgroundColor(IndexedColors.RED.getIndex());
		oddPriceColor.setFillPattern(FillPatternType.DIAMONDS);
		try {
			Statement stmn = con.createStatement();
			// execute the query, and get a java resultset
			ResultSet rs = stmn.executeQuery(query);
			while (rs.next()) {
				columnCount = 0;
				Row row = sheet.createRow(rowCount++);
				for (int i = 2; i < 21; i++) {
					Cell cell = row.createCell(columnCount++);
					if (rs.getString(2).equals("AWNM50000")) {
						cell.setCellStyle(oddPriceColor);
					}
					cell.setCellValue(rs.getString(i));
				}

				if (rs.isLast()) {
					if (lastRegistration) {
						updateLastExportID(Integer.parseInt(rs.getString(1)), false, user);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public XSSFWorkbook fillWorkbookWithLastRegistrations(XSSFWorkbook workbook, User user) {
		if (user.getLastExport() == 0) {
			fillWorkbookWithAllRegistrations(workbook, user, true);
		} else {
			XSSFSheet sheet = workbook.createSheet("Inschrijven-site");
			int rowCount = 0;
			Row headerRow = sheet.createRow(rowCount++);
			int columnCount = 0;
			String allCellHeaderNames = "Cursuscode,Geslacht,Geboortedatum,Voornaam,Tussenvoegsel,Achternaam,Naam Ouders,Adres,Stad,Adres nummer,Postcode,E-mailadres,Telefoonnummer,IBAN,Ter name van,Betalingsoptie,Mandaatdatum,Cursusprijs,Via Mollie Betaald(0=nee 1=ja)";
			String[] cellHeaderNames = allCellHeaderNames.split(",");
			for (int i = 0; i < 19; i++) {
				Cell cell = headerRow.createCell(columnCount++);
				cell.setCellValue(cellHeaderNames[i]);
			}
			String query = "SELECT * FROM registration where `registration_id` > ?";
			CellStyle oddPriceColor = workbook.createCellStyle();
			oddPriceColor.setFillBackgroundColor(IndexedColors.RED.getIndex());
			oddPriceColor.setFillPattern(FillPatternType.DIAMONDS);
			try {
				PreparedStatement stmn = con.prepareStatement(query);
				stmn.setInt(1, user.getLastExport());
				// execute the query, and get a java resultset
				ResultSet rs = stmn.executeQuery();
				while (rs.next()) {
					columnCount = 0;
					Row row = sheet.createRow(rowCount++);
					for (int i = 2; i < 21; i++) {
						Cell cell = row.createCell(columnCount++);
						if (rs.getString(2).equals("AWNM50000")) {
							cell.setCellStyle(oddPriceColor);
						}
						cell.setCellValue(rs.getString(i));
					}
					if (rs.isLast()) {
						updateLastExportID(Integer.parseInt(rs.getString(1)), false, user);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return workbook;
	}

	public XSSFWorkbook fillWorkbookWithAllRegistrationsAccountant(XSSFWorkbook workbook, User user,
			boolean lastRegistration) {
		XSSFSheet sheet = workbook.createSheet("Inschrijven-site");
		int rowCount = 0;
		Row headerRow = sheet.createRow(rowCount++);
		int columnCount = 0;
		String allCellHeaderNames = "Voornaam,Tussenvoegsel,Achternaam,Adres,Stad,Adres nummer,Postcode,E-mailadres,IBAN,Ter name van,Betalingsoptie,Cursusprijs,Mandaatdatum,Via Mollie betaald(0=nee 1=ja)";
		String[] cellHeaderNames = allCellHeaderNames.split(",");
		for (int i = 0; i < 14; i++) {
			Cell cell = headerRow.createCell(columnCount++);
			cell.setCellValue(cellHeaderNames[i]);
		}
		String query = "SELECT * FROM registration";
		try {
			Statement stmn = con.createStatement();
			// execute the query, and get a java resultset
			ResultSet rs = stmn.executeQuery(query);
			while (rs.next()) {
				columnCount = 0;

				Row row = sheet.createRow(rowCount++);
				for (int i = 5; i < 21; i++) {
					if (i == 8 || i == 14) {
					} else {
						Cell cell = row.createCell(columnCount++);
						cell.setCellValue(rs.getString(i));
					}
				}

				if (rs.isLast()) {
					if (lastRegistration) {
						updateLastExportID(Integer.parseInt(rs.getString(1)), true, user);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public XSSFWorkbook fillWorkbookWithLastRegistrationsAccountant(XSSFWorkbook workbook, User user) {
		if (user.getLastExportAdministration() == 0) {
			fillWorkbookWithAllRegistrationsAccountant(workbook, user, true);
		} else {
			XSSFSheet sheet = workbook.createSheet("Inschrijven-site");
			int rowCount = 0;
			Row headerRow = sheet.createRow(rowCount++);
			int columnCount = 0;
			String allCellHeaderNames = "Voornaam,Tussenvoegsel,Achternaam,Adres,Stad,Adres nummer,Postcode,E-mailadres,IBAN,Ter name van,Betalingsoptie,Cursusprijs,Mandaatdatum,Via Mollie betaald(0=nee 1=ja)";
			String[] cellHeaderNames = allCellHeaderNames.split(",");
			for (int i = 0; i < 14; i++) {
				Cell cell = headerRow.createCell(columnCount++);
				cell.setCellValue(cellHeaderNames[i]);
			}
			String query = "SELECT * FROM registration where `registration_id` > ?";
			try {
				PreparedStatement stmn = con.prepareStatement(query);
				stmn.setInt(1, user.getLastExportAdministration());
				// execute the query, and get a java resultset
				ResultSet rs = stmn.executeQuery();
				while (rs.next()) {
					columnCount = 0;
					Row row = sheet.createRow(rowCount++);
					for (int i = 5; i < 21; i++) {
						if (i == 8 || i == 14) {
						} else {
							Cell cell = row.createCell(columnCount++);
							cell.setCellValue(rs.getString(i));
						}
					}
					if (rs.isLast()) {
						updateLastExportID(Integer.parseInt(rs.getString(1)), true, user);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return workbook;
	}

	public XSSFWorkbook fillWorkbookWithAllCourses(XSSFWorkbook workbook) {
		XSSFSheet sheet = workbook.createSheet("Export cursussen");
		int rowCount = 0;
		Row headerRow = sheet.createRow(rowCount++);
		int columnCount = 0;
		String allCellHeaderNames = "Cursuscode,Flyer school naam,School naam,Prijs,Prijs tweede deelnemer, E-mail school, Maximum aantal inschrijvingen, Huidig aantal inschrijvingen";
		String[] cellHeaderNames = allCellHeaderNames.split(",");
		for (int i = 0; i < 8; i++) {
			Cell cell = headerRow.createCell(columnCount++);
			cell.setCellValue(cellHeaderNames[i]);
		}
		String query = "SELECT * FROM course";
		try {
			Statement stmn = con.createStatement();
			// execute the query, and get a java resultset
			ResultSet rs = stmn.executeQuery(query);
			while (rs.next()) {
				columnCount = 0;
				Row row = sheet.createRow(rowCount++);
				for (int i = 1; i < 9; i++) {
					Cell cell = row.createCell(columnCount++);
					cell.setCellValue(rs.getString(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public XSSFWorkbook fillWorkbookWithWaitingList(XSSFWorkbook workbook) {
		XSSFSheet sheet = workbook.createSheet("Wachtlijst-site");
		int rowCount = 0;
		Row headerRow = sheet.createRow(rowCount++);
		int columnCount = 0;
		String allCellHeaderNames = "Wachtlijst Nummer,Cursuscode,Geslacht,Geboortedatum,Voornaam,Tussenvoegsel,Achternaam,Naam Ouders,Adres,Stad,Adres nummer,Postcode,E-mailadres,Telefoonnummer,Mandaatdatum,Cursusprijs)";
		String[] cellHeaderNames = allCellHeaderNames.split(",");
		for (int i = 0; i < 16; i++) {
			Cell cell = headerRow.createCell(columnCount++);
			cell.setCellValue(cellHeaderNames[i]);
		}
		String query = "SELECT * FROM waiting_list";
		try {
			Statement stmn = con.createStatement();
			// execute the query, and get a java resultset
			ResultSet rs = stmn.executeQuery(query);
			while (rs.next()) {
				columnCount = 0;
				Row row = sheet.createRow(rowCount++);
				for (int i = 1; i < 17; i++) {
					Cell cell = row.createCell(columnCount++);
					cell.setCellValue(rs.getString(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public int findLastRegistrationId() {
		String query = "SELECT registration_id FROM registration ORDER BY registration_id DESC LIMIT 1";
		try {
			Statement stmn = con.createStatement();
			// execute the query, and get a java resultset
			ResultSet rs = stmn.executeQuery(query);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Course findCourse(String courseCode) {
		String query = "SELECT * FROM course where course_code=?";
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, courseCode);
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				int i = 1;
				Course course = new Course();
				course.setCourseCode(rs.getString(i++));
				course.setSchoolName(rs.getString(i++));
				course.setSchoolNameWithoutArticle(rs.getString(i++));
				course.setCoursePrice(rs.getString(i++));
				course.setSecondCoursePrice(rs.getString(i++));
				course.setEmail(rs.getString(i++));
				course.setMaximumRegistrations(rs.getInt(i++));
				course.setCurrentRegistrations(rs.getInt(i++));
				course.setCourseFound(true);
				return course;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveCourse(Course course) {
		String query;
		boolean courseExists = checkIfCourseExists(course.getCourseCode());
		if (courseExists) {
			query = "UPDATE course set school_name = ?, school_name_without_article = ?, price = ?, price_second = ?, email = ?, maximum_registrations = ?, current_registrations = ? where course_code = ?";
		} else {
			query = "INSERT INTO course (course_code, school_name, school_name_without_article, price, price_second, email, maximum_registrations, current_registrations)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
		}
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			int i = 1;
			if (!courseExists) {
				preparedStmt.setString(i++, course.getCourseCode());
			}
			preparedStmt.setString(i++, course.getSchoolName());
			preparedStmt.setString(i++, course.getSchoolNameWithoutArticle());
			preparedStmt.setString(i++, course.getCoursePrice());
			preparedStmt.setString(i++, course.getSecondCoursePrice());
			preparedStmt.setString(i++, course.getEmail());
			preparedStmt.setInt(i++, course.getMaximumRegistrations());
			preparedStmt.setInt(i++, course.getCurrentRegistrations());
			if (courseExists) {
				preparedStmt.setString(i++, course.getCourseCode());
			}
			// execute the preparedstatement
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void saveCompleteRegistration(Registration reg) throws SQLException {
		String query = "INSERT INTO registration (course_code, gender, birthdate, first_name, insertion, last_name, parents_name, address, town, address_nr, zipcode, email, phone_number, iban, name_bank_holder, payment_option, course_price, mandate_date, completed_mollie, mollie_id)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		int i = 1;
		if (reg.getCourseCode().equals("null")) {
			preparedStmt.setString(i++, "");
		} else {
			preparedStmt.setString(i++, reg.getCourseCode());
		}
		preparedStmt.setString(i++, reg.getGender());
		String[] s = reg.getBirthDay().split("-");
		preparedStmt.setDate(i++,
				Date.valueOf(LocalDate.of(Integer.parseInt(s[2]), Integer.parseInt(s[1]), Integer.parseInt(s[0]))));
		preparedStmt.setString(i++, reg.getFirstName());
		preparedStmt.setString(i++, reg.getInsertion());
		preparedStmt.setString(i++, reg.getLastName());
		preparedStmt.setString(i++, reg.getParentsName());
		preparedStmt.setString(i++, reg.getAddress());
		preparedStmt.setString(i++, reg.getTown());
		preparedStmt.setString(i++, reg.getAddressNr());
		preparedStmt.setString(i++, reg.getZipCode());
		preparedStmt.setString(i++, reg.getEmail());
		preparedStmt.setString(i++, reg.getPhoneNumber());
		preparedStmt.setString(i++, reg.getIban());
		preparedStmt.setString(i++, reg.getNameAccountHolder());
		preparedStmt.setString(i++, reg.getPaymentOption());
		preparedStmt.setString(i++, reg.getPrice());
		preparedStmt.setTimestamp(i++, Timestamp.valueOf(reg.getMandateDate()));
		preparedStmt.setBoolean(i++, false);
		preparedStmt.setString(i++, reg.getMollieId());
		preparedStmt.execute();
		for (SecondRegistration su : reg.getSecondCourses()) {
				PreparedStatement preparedStmt1 = con.prepareStatement(query);
				int i1 = 1;
				preparedStmt1.setString(i1++, su.getCourseCode());
				preparedStmt1.setString(i1++, su.getGender());
				String[] s1 = su.getBirthday().split("-");
				preparedStmt1.setDate(i1++, Date
						.valueOf(LocalDate.of(Integer.parseInt(s1[2]), Integer.parseInt(s1[1]), Integer.parseInt(s1[0]))));
				preparedStmt1.setString(i1++, su.getFirstName());
				preparedStmt1.setString(i1++, su.getInsertion());
				preparedStmt1.setString(i1++, su.getLastName());
				preparedStmt1.setString(i1++, reg.getParentsName());
				preparedStmt1.setString(i1++, reg.getAddress());
				preparedStmt1.setString(i1++, reg.getTown());
				preparedStmt1.setString(i1++, reg.getAddressNr());
				preparedStmt1.setString(i1++, reg.getZipCode());
				preparedStmt1.setString(i1++, reg.getEmail());
				preparedStmt1.setString(i1++, reg.getPhoneNumber());
				preparedStmt1.setString(i1++, reg.getIban());
				preparedStmt1.setString(i1++, reg.getNameAccountHolder());
				preparedStmt1.setString(i1++, reg.getPaymentOption());
				preparedStmt1.setString(i1++, su.getPrice());
				preparedStmt1.setTimestamp(i1++, Timestamp.valueOf(reg.getMandateDate()));
				preparedStmt1.setBoolean(i1++, false);
				preparedStmt1.setString(i1++, reg.getMollieId());
				preparedStmt1.execute();
		}
	}

	public void saveSecondRegistrations(Registration reg) {
		String query = "INSERT INTO registration (course_code, gender, birthdate, first_name, insertion, last_name, parents_name, address, town, address_nr, zipcode, email, phone_number, iban, name_bank_holder, payment_option, course_price, mandate_date, completed_mollie, mollie_id)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		for (SecondRegistration su : reg.getSecondCourses()) {
			try {
				PreparedStatement preparedStmt = con.prepareStatement(query);
				int i = 1;
				preparedStmt.setString(i++, su.getCourseCode());
				preparedStmt.setString(i++, su.getGender());
				String[] s = su.getBirthday().split("-");
				preparedStmt.setDate(i++, Date
						.valueOf(LocalDate.of(Integer.parseInt(s[2]), Integer.parseInt(s[1]), Integer.parseInt(s[0]))));
				preparedStmt.setString(i++, su.getFirstName());
				preparedStmt.setString(i++, su.getInsertion());
				preparedStmt.setString(i++, su.getLastName());
				preparedStmt.setString(i++, reg.getParentsName());
				preparedStmt.setString(i++, reg.getAddress());
				preparedStmt.setString(i++, reg.getTown());
				preparedStmt.setString(i++, reg.getAddressNr());
				preparedStmt.setString(i++, reg.getZipCode());
				preparedStmt.setString(i++, reg.getEmail());
				preparedStmt.setString(i++, reg.getPhoneNumber());
				preparedStmt.setString(i++, reg.getIban());
				preparedStmt.setString(i++, reg.getNameAccountHolder());
				preparedStmt.setString(i++, reg.getPaymentOption());
				preparedStmt.setString(i++, su.getPrice());
				preparedStmt.setTimestamp(i++, Timestamp.valueOf(reg.getMandateDate()));
				preparedStmt.setBoolean(i++, false);
				preparedStmt.setString(i++, reg.getMollieId());
				preparedStmt.execute();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void saveWaitingList(WaitingList wl) {
		String query = "INSERT INTO waiting_list (course_code, gender, birthdate, first_name, insertion, last_name, parents_name, address, town, address_nr, zipcode, email, phone_number, mandate_date, course_price)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			int i = 1;
			if (wl.getCourseCode().equals("null")) {
				preparedStmt.setString(i++, "");
			} else {
				preparedStmt.setString(i++, wl.getCourseCode());
			}
			preparedStmt.setString(i++, wl.getGender());
			String[] s = wl.getBirthDay().split("-");
			preparedStmt.setDate(i++,
					Date.valueOf(LocalDate.of(Integer.parseInt(s[2]), Integer.parseInt(s[1]), Integer.parseInt(s[0]))));
			preparedStmt.setString(i++, wl.getFirstName());
			preparedStmt.setString(i++, wl.getInsertion());
			preparedStmt.setString(i++, wl.getLastName());
			preparedStmt.setString(i++, wl.getParentsName());
			preparedStmt.setString(i++, wl.getAddress());
			preparedStmt.setString(i++, wl.getTown());
			preparedStmt.setString(i++, wl.getAddressNr());
			preparedStmt.setString(i++, wl.getZipCode());
			preparedStmt.setString(i++, wl.getEmail());
			preparedStmt.setString(i++, wl.getPhoneNumber());
			preparedStmt.setTimestamp(i++, Timestamp.valueOf(wl.getMandateDate()));
			preparedStmt.setString(i++, wl.getPrice());
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Can only be used when user does not exist in database
	public void saveUser(User user) {
		String query = "INSERT INTO user (username, password, last_excel_export, last_excel_export_administration)"
				+ " values (?, ?, ?, ?)";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, user.getUsername());
			preparedStmt.setString(2, user.getPassword());
			preparedStmt.setInt(3, user.getLastExport());
			preparedStmt.setInt(4, user.getLastExportAdministration());
			// execute the preparedstatement
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateLastExportNumbers(User user) {
		String query = "select * from user where id=?";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, user.getUsername());
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				user.setLastExport(rs.getInt(3));
				user.setLastExportAdministration(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateLastExportID(int value, boolean isAdministration, User user) {
		String query;
		if (isAdministration) {
			user.setLastExportAdministration(value);
			query = "UPDATE user set last_excel_export_administration = ? where username = ?";
		} else {
			user.setLastExport(value);
			query = "UPDATE user set last_excel_export = ? where username = ?";
		}
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, value);
			preparedStmt.setString(2, user.getUsername());
			// execute the preparedstatement
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateMollieStatusByMollieId(String mollieId) {
		String query = "update registration set completed_mollie = ? where mollie_id = ?";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setBoolean(1, true);
			preparedStmt.setString(2, mollieId);
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addOneRegistrationToCourse(Course course) {
		String query = "update course set current_registrations = ? where course_code = ?";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, course.getCurrentRegistrations() + 1);
			preparedStmt.setString(2, course.getCourseCode());
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteAllCourses() throws SQLException {
		String query = "DELETE FROM course";
		Statement stmn = con.createStatement();
		// execute the query, and get a java resultset
		stmn.execute(query);
	}

	public int getAmountOfRegistrationsOfCourse(String courseCode) {
		ArrayList<Integer> registrationIds = new ArrayList<>();
		String query = "select registration_id from registration where course_code = ?";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, courseCode);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				registrationIds.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return registrationIds.size();
	}

	public int getAmountOfWaitersOfCourse(String courseCode) {
		ArrayList<Integer> waitingListIds = new ArrayList<>();
		String query = "select waiting_id from waiting_list where course_code = ?";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, courseCode);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				waitingListIds.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return waitingListIds.size();
	}
}
