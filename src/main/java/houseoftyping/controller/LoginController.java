package houseoftyping.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import houseoftyping.sql.SQLConnection;

/**
 * Servlet implementation class FormController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
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
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			SQLConnection sql = new SQLConnection();
			if(sql.login(username, password)) {
				Cookie cookie = new Cookie("Username", username);
				cookie.setMaxAge(24*60*60);
				response.addCookie(cookie);
				out.print(true);
			} else {
				
				out.print(false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			out.print(false);
		}
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
