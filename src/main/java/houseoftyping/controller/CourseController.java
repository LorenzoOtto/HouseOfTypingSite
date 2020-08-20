package houseoftyping.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import houseoftyping.domain.Course;
import houseoftyping.sql.SQLConnection;

/**
 * Servlet implementation class FormController
 */
public class CourseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CourseController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		if (request.getParameter("type").equals("deleteAllCourses")) {
			SQLConnection sql = new SQLConnection();
			String successful = "true";
			try {
				sql.deleteAllCourses();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				successful = "false";
			}
			Cookie cookie = new Cookie("DeleteStatus", successful);
			cookie.setMaxAge(10 * 60);
			response.addCookie(cookie);
			try {
				response.sendRedirect("dashboard");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String courseCode = request.getParameter("code").toUpperCase();
			PrintWriter out = response.getWriter();
			SQLConnection sql = new SQLConnection();
			Course course = sql.findCourse(courseCode);
			if (course != null) {
				if (course.isCourseFound()) {
					out.print(course.toString());
				} else {
					out.print(false);
				}
			} else {
				out.print(false);
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
