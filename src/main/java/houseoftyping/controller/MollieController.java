package houseoftyping.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.feelio.mollie.data.payment.PaymentResponse;
import houseoftyping.domain.Transaction;
import houseoftyping.sql.SQLConnection;

/**
 * Servlet implementation class FormController
 */
public class MollieController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MollieController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		if (request.getParameter("type").equals("statusPayment")) {
			String mollieId = request.getParameter("mollieId");
			Transaction trans = new Transaction();
			PaymentResponse pr = trans.getPaymentById(mollieId);
			if(pr.getStatus().equals("paid")) {
				SQLConnection sql = new SQLConnection();
				sql.updateMollieStatusByMollieId(mollieId);
 			}
			out.print(pr.getStatus());
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
