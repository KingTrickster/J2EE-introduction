package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Home() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get session (this time, the existing one)
		HttpSession session = request.getSession();
		// retrieve user from session
		String username = (String) session.getAttribute("username");
		if (username == null) { // username not stored go back to login
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
			dispatcher.forward(request, response);
			return;
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>\n<html>\n" + "<head><title>Home</title></head><body>\n"
				+ "<h3>HOME PAGE</h3><br><br><div>Welcome <b>" + username + "</b></div>\n"
				+ "<div><a href='Logout'>Logout</a></div>\n" + "</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username != null || password != null) {
			if (!username.equals("root") || !password.equals("pa$$word")) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
				dispatcher.forward(request, response);
				return;
			}
		}
		// get a session, create new one if necessary
		HttpSession session = request.getSession();
		// store the username in the session
		session.setAttribute("username", username);
		doGet(request, response);
	}

}
