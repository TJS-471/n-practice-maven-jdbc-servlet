package com.mycompany.controller.admin;


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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A web servlet class that extends {@link HttpServlet}.
 * It exposes the endpoint to handle GET and POST requests from a client to create a new user.
 * @author Julia Tsukanova
 * @version 1.0
 */
@WebServlet("/admin/create_user")
public class CreateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 7422595120679228599L;
    private JdbcRoleDao roleDao = new JdbcRoleDao();
    private JdbcUserDao userDao = new JdbcUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String createPage = "/WEB-INF/admin/user_form.jsp";
        try {
            List<Role> rolesList = roleDao.getAll();
            req.setAttribute("rolesList", rolesList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher(createPage).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String listUrl = req.getContextPath() + "/admin/list_users";

        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date birthDate = dateFormat.parse(req.getParameter("birthDate"));

            String roleName = req.getParameter("name");
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(roleName));

            User user = new User(login, email, password, firstName, lastName, birthDate, roles);
            userDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(listUrl);
    }

}
