package houseoftyping.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import houseoftyping.domain.Course;
import houseoftyping.domain.User;
import houseoftyping.sql.SQLConnection;

/**
 * Servlet implementation class FormController
 */
@MultipartConfig
public class ExcelController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExcelController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("type").equals("upload")) {
			response.setContentType("text/html; charset=UTF-8");
			handleUpload(request, response);
		} else if (request.getParameter("type").equals("downloadLatestExcel")) {
			handleDownload(request, response, false);
		} else if (request.getParameter("type").equals("downloadFullExcel")) {
			handleDownload(request, response, true);
		} else if (request.getParameter("type").equals("getLastExportNumber")) {
			handleExportNumber(request, response);
		} else if (request.getParameter("type").equals("downloadAllCourses")) {
			handleCourseDownload(request, response);
		} else if (request.getParameter("type").equals("downloadWaitingList")) {
			handleWaitingListDownload(request, response);
		}
	}

	@SuppressWarnings("resource")
	private void handleWaitingListDownload(HttpServletRequest request, HttpServletResponse response) {
		SQLConnection conn = new SQLConnection();
		XSSFWorkbook workbook = new XSSFWorkbook();
		LocalDate ld = LocalDate.now();
		String fileName = "";
		try {
			workbook = conn.fillWorkbookWithWaitingList(workbook);
			fileName = "export-wachtlijst-" + ld.getDayOfMonth() + "-" + findFullMonthByInt(ld.getMonthValue()) + "-"
					+ ld.getYear();
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
			workbook.write(response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private void handleCourseDownload(HttpServletRequest request, HttpServletResponse response) {
		SQLConnection conn = new SQLConnection();
		XSSFWorkbook workbook = new XSSFWorkbook();
		LocalDate ld = LocalDate.now();
		String fileName = "";
		try {
			workbook = conn.fillWorkbookWithAllCourses(workbook);
			fileName = "export-alle-cursussen-" + ld.getDayOfMonth() + "-" + findFullMonthByInt(ld.getMonthValue())
					+ "-" + ld.getYear();
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
			workbook.write(response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleExportNumber(HttpServletRequest request, HttpServletResponse response) {
		SQLConnection sql = new SQLConnection();
		int lastRegistrationId = sql.findLastRegistrationId();
		User user = sql.findUserByName(request.getParameter("username"));
		try {
			PrintWriter out = response.getWriter();
			if (user == null) {
				out.print("logout");
			} else {
				int newRegistrationsNumber = lastRegistrationId - user.getLastExport();
				int newRegistrationsNumberAdministration = lastRegistrationId - user.getLastExportAdministration();
				out.print(newRegistrationsNumber + "," + newRegistrationsNumberAdministration);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("resource")
	private void handleDownload(HttpServletRequest request, HttpServletResponse response, boolean fullDownload) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("Username")) {
				SQLConnection conn = new SQLConnection();
				User user = conn.findUserByName(cookie.getValue());
				XSSFWorkbook workbook = new XSSFWorkbook();
				LocalDate ld = LocalDate.now();
				String fileName = "";
				try {
					if (request.getParameter("isAccountant").equals("true")) {
						if (fullDownload) {
							workbook = conn.fillWorkbookWithAllRegistrationsAccountant(workbook, user, false);
							fileName = "export-inschrijvingen-" + ld.getDayOfMonth() + "-"
									+ findFullMonthByInt(ld.getMonthValue()) + "-" + ld.getYear() + "-administratie";
						} else {
							workbook = conn.fillWorkbookWithLastRegistrationsAccountant(workbook, user);
							fileName = "export-laatste-inschrijvingen-" + ld.getDayOfMonth() + "-"
									+ findFullMonthByInt(ld.getMonthValue()) + "-" + ld.getYear() + "-administratie";
						}
					} else {
						if (fullDownload) {
							workbook = conn.fillWorkbookWithAllRegistrations(workbook, user, false);
							fileName = "export-inschrijvingen-" + ld.getDayOfMonth() + "-"
									+ findFullMonthByInt(ld.getMonthValue()) + "-" + ld.getYear();
						} else {
							workbook = conn.fillWorkbookWithLastRegistrations(workbook, user);
							fileName = "export-laatste-inschrijvingen-" + ld.getDayOfMonth() + "-"
									+ findFullMonthByInt(ld.getMonthValue()) + "-" + ld.getYear();
						}
					}
					response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
					workbook.write(response.getOutputStream());
					response.flushBuffer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String findFullMonthByInt(int monthValue) {
		switch (monthValue) {
		case 1:
			return "januari";
		case 2:
			return "februari";
		case 3:
			return "maart";
		case 4:
			return "april";
		case 5:
			return "mei";
		case 6:
			return "juni";
		case 7:
			return "juli";
		case 8:
			return "augustus";
		case 9:
			return "september";
		case 10:
			return "oktober";
		case 11:
			return "november";
		case 12:
			return "december";
		}
		return null;
	}

	private void handleUpload(HttpServletRequest request, HttpServletResponse response) {
		Part filePart = null;
		String successful = "true";
		try {
			filePart = request.getPart("file");
		} catch (IOException | ServletException ex) {
			ex.printStackTrace();
			successful = "false";
		}
		try {
			InputStream fileContent = filePart.getInputStream();
			Workbook workbook = WorkbookFactory.create(fileContent);
			createCoursesByWorkbook(workbook);
		} catch (Exception E) {
			E.printStackTrace();
			successful = "false";
		}
		Cookie cookie = new Cookie("UploadStatus", successful);
		cookie.setMaxAge(10 * 60);
		response.addCookie(cookie);
		try {
			response.sendRedirect("dashboard");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createCoursesByWorkbook(Workbook workbook) {
		DataFormatter dataFormatter = new DataFormatter();
		Sheet sheet = null;
		for (Sheet s : workbook) {
			if (s.getSheetName().equals("upload site")) {
				sheet = s;
			}
		}
		SQLConnection sql = new SQLConnection();
		for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
			if (j != 0) {
				Row row = sheet.getRow(j);
				List<String> valuesFromExcel = new ArrayList<>();
				for (Cell cell : row) {
					cell.setCellFormula(null);
					String cellValue = dataFormatter.formatCellValue(cell);
					if (!cellValue.equals("0")) {
						valuesFromExcel.add(cellValue);
					} else {
						valuesFromExcel.add(null);
					}
				}
				Course c = new Course(valuesFromExcel);
				if (c.getCourseCode().equals(null)) {
				} else {
					c.save(sql);
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
