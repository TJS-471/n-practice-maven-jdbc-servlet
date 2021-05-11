package com.mycompany.controller.role;

import com.mycompany.dao.JdbcRoleDao;
import com.mycompany.entity.Role;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A web servlet class that extends {@link HttpServlet}.
 * It exposes the endpoint to handle GET and POST requests from a client to create a new role.
 * @author Julia Tsukanova
 * @version 1.0
 */
@WebServlet("/admin/create_role")
public class CreateRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 455711899767225185L;
	private JdbcRoleDao dao = new JdbcRoleDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String createPage = "/WEB-INF/role/role_form.jsp";
		req.getRequestDispatcher(createPage).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String listUrl = req.getContextPath() + "/admin/list_roles";
		try {
            Role role = new Role();
            role.setName(req.getParameter("name"));
            dao.create(role);
        }catch (SQLException e){
		    e.printStackTrace();
        }
		resp.sendRedirect(listUrl);
	}

}
