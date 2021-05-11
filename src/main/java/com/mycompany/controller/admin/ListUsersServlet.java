package com.mycompany.controller.admin;


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
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A web servlet class that extends {@link HttpServlet}.
 * It exposes the endpoint to handle a GET request from a client to list all users.
 * @author Julia Tsukanova
 * @version 1.0
 */
@WebServlet(urlPatterns = { "/admin/list_users" })
public class ListUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 4085180157534705398L;
	private JdbcUserDao dao = new JdbcUserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String listPage = "/WEB-INF/admin/users_list.jsp";
        String login = (String) req.getSession().getAttribute("login");
        try {
            User userByLogin = dao.findByLogin(login);
                String lastName = userByLogin.getLastName();
                req.setAttribute("lastName", lastName);
                List<Role> roles = userByLogin.getRoles();
                if(roles != null) {
                    for (Role role : roles) {
                        req.setAttribute("role", role.getName());
                    }
                }
            List<User> usersList = dao.findAll();
            Map<Long, Integer> ages = new HashMap<>();
            for (User user : usersList) {
                Date birthDate = user.getBirthDate();
                Period period = Period.between(birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        LocalDate.now());
                int age = period.getYears();
                ages.put(user.getId(), age);
            }
            req.setAttribute("ages", ages);
            req.setAttribute("usersList", usersList);
        }catch (SQLException e){
            e.printStackTrace();
        }
		req.getRequestDispatcher(listPage).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
