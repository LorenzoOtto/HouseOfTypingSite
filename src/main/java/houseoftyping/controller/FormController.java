package houseoftyping.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import houseoftyping.domain.Course;
import houseoftyping.domain.Registration;
import houseoftyping.domain.WaitingList;
import houseoftyping.sql.SQLConnection;

/**
 * Servlet implementation class FormController
 */
public class FormController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FormController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		boolean canRegister = true;
		String information = request.getParameter("allInformation");
		String secondInformation = request.getParameter("allSecondInformation");
		Registration registration = new Registration(information, secondInformation);
		if (!registration.getCourseCode().equals("null")) {
			SQLConnection sql = new SQLConnection();
			Course course = sql.findCourse(registration.getCourseCode());
			if (course.hasMaximum()) {
				if (course.getCurrentRegistrations() == course.getMaximumRegistrations()) {
					canRegister = false;
					addRegistrationToWaitList(registration);
					try {
						registration.sendWaitingListMail();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					registration.saveSecondRegistration();
					out.print("full");
				} else {
					sql.addOneRegistrationToCourse(course);
				}
			}
		}
		if (canRegister) {
			if (registration.getPaymentOption().equals("1")) {
				registration.createMolliePayment();
				Cookie cookie = new Cookie("MollieId", registration.getMollieId());
				cookie.setMaxAge(24 * 60 * 60);
				response.addCookie(cookie);
			}
			try {
				registration.sendWelcomeMail();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			if (registration.getCourseCode() == null) {
				registration.setCourseCode("");
			}
			registration.save();
			if(registration.getPaymentOption().equals("1")) {
				out.print(registration.getMolliePayment().getLinks().getCheckout().getHref());
			} else {
				out.print("saved");
			}
		}
	}

	public void addRegistrationToWaitList(Registration registration) {
		WaitingList wl = new WaitingList(registration);
		wl.save();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
