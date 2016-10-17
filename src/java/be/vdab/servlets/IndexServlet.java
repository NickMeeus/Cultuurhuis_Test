package be.vdab.servlets;

import be.vdab.dao.GenreDAO;
import be.vdab.dao.VoorstellingDAO;
import be.vdab.entities.Genre;
import be.vdab.entities.Voorstelling;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/JSP/index.jsp";
    private final transient GenreDAO genreDAO = new GenreDAO();
    private final transient VoorstellingDAO voorstellingDAO = new VoorstellingDAO();

    @Resource(name = GenreDAO.JNDI_CULTUURHUIS)
    void setDataSource(DataSource dataSource) {
        genreDAO.setDataSource(dataSource);
        voorstellingDAO.setDataSource(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("genres", genreDAO.findAll());
        List<String> fouten = new ArrayList<>();

        if (request.getParameterValues("genreId") != null) {
            try {
                long genreId = Long.parseLong(request.getParameter("genreId"));
                List<Voorstelling> voorstellingen = voorstellingDAO.findAllByGenre(genreId);
                if (voorstellingen.isEmpty()) {
                    fouten.add("Geen voorstellingen gevonden voor dit genre.");
                }
                request.setAttribute("voorstellingen", voorstellingen);
                Genre genre = genreDAO.findByID(genreId);
                request.setAttribute("genre", genre);
            } catch (NumberFormatException ex) {
                fouten.add("Ongeldig genre.");
            }
        }
        request.setAttribute("fouten", fouten);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }
}
