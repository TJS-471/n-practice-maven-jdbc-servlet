package com.mycompany.controller.admin;


import com.mycompany.dao.JdbcUserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A web servlet class that extends {@link HttpServlet}.
 * It exposes the endpoint to handle a GET request from a client to delete a user.
 * @author Julia Tsukanova
 * @version 1.0
 */
@WebServlet("/admin/delete_user")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = -8335596485258034581L;
	private JdbcUserDao dao = new JdbcUserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String listUrl = req.getContextPath() + "/admin/list_users";
		long id = Long.parseLong(req.getParameter("id"));
		try {
            dao.remove(id);
        }catch (SQLException e){
		    e.printStackTrace();
        }
		resp.sendRedirect(listUrl);
	}

}
