package com.mycompany.controller.role;

import com.mycompany.dao.JdbcRoleDao;
import com.mycompany.dao.JdbcUserDao;
import com.mycompany.entity.Role;
import com.mycompany.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * A web servlet class that extends {@link HttpServlet}.
 * It exposes the endpoint to handle a GET request from a client to list all roles.
 * @author Julia Tsukanova
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/admin/list_roles"})
public class ListRolesServlet extends HttpServlet {
    private static final long serialVersionUID = 4672764955746030026L;
    private JdbcRoleDao roleDao = new JdbcRoleDao();
    private JdbcUserDao userDao = new JdbcUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String listPage = "/WEB-INF/role/roles_list.jsp";
        String login = (String) req.getSession().getAttribute("login");
        try {
            User userByLogin = userDao.findByLogin(login);
            if (userByLogin != null) {
                String lastName = userByLogin.getLastName();
                req.setAttribute("lastName", lastName);
                List<Role> roles = userByLogin.getRoles();
                if (roles != null) {
                    for (Role role : roles) {
                        roleDao.createTable();
                        req.setAttribute("role", role.getName());
                    }
                }
            }
            List<Role> roleList = roleDao.getAll();
            req.setAttribute("roleList", roleList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher(listPage).forward(req, resp);
    }

}
