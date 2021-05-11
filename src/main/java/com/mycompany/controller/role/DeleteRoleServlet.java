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
 * It exposes the endpoint to handle a GET request from a client to delete a role.
 * @author Julia Tsukanova
 * @version 1.0
 */
@WebServlet("/admin/delete_role")
public class DeleteRoleServlet extends HttpServlet {
	private static final long serialVersionUID = -3332712910319283010L;
	private JdbcRoleDao dao = new JdbcRoleDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String listUrl = req.getContextPath() + "/admin/list_roles";
		long id = Long.parseLong(req.getParameter("id"));
		String roleName = req.getParameter("name");
		try {
            Role role = new Role(id, roleName);
            dao.remove(role);
        }catch (SQLException e){
		    e.printStackTrace();
        }
		resp.sendRedirect(listUrl);
	}

}
