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
 * It exposes the endpoint to handle GET and POST requests from a client to update the existing user.
 * @author Julia Tsukanova
 * @version 1.0
 */
@WebServlet("/admin/update_user")
public class UpdateUsserServlet extends HttpServlet {
    private static final long serialVersionUID = -1745337085836312367L;
    private JdbcUserDao userDao = new JdbcUserDao();
    private JdbcRoleDao roleDao = new JdbcRoleDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String updatePage = "/WEB-INF/admin/user_form.jsp";
        String login = req.getParameter("login");
        try {
            User byLogin = userDao.findByLogin(login);
            List<Role> rolesList = roleDao.getAll();
            req.setAttribute("user", byLogin);
            req.setAttribute("rolesList", rolesList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher(updatePage).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String listUrl = req.getContextPath() + "/admin/list_users";
        long id = Long.parseLong(req.getParameter("id"));
        try {
            User userDaoById = userDao.findById(id);
            if (userDaoById == null) {
                throw new RuntimeException("Can not update a user. The record does not exist");
            }
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date birthDate = dateFormat.parse(req.getParameter("birthDate"));

            String roleName = req.getParameter("name");
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(roleName));

            userDaoById.setEmail(email);
            if (password != null && !password.isEmpty()) {
                userDaoById.setPassword(password);
            }
            userDaoById.setFirstName(firstName);
            userDaoById.setLastName(lastName);
            userDaoById.setBirthDate(birthDate);
            userDaoById.setRoles(roles);
            userDao.update(userDaoById);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(listUrl);

    }
}
