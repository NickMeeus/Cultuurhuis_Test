package be.vdab.servlets;

import be.vdab.dao.VoorstellingDAO;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/overzicht.htm")
public class OverzichtServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/JSP/overzicht.jsp";
    private final transient VoorstellingDAO voorstellingDAO = new VoorstellingDAO();

    @Resource(name = VoorstellingDAO.JNDI_CULTUURHUIS)
    void setDataSource(DataSource dataSource) {
        voorstellingDAO.setDataSource(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
