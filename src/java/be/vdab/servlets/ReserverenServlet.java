package be.vdab.servlets;

import be.vdab.dao.VoorstellingDAO;
import be.vdab.entities.Voorstelling;
import static be.vdab.servlets.MandjeConstanten.MANDJE;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/reserveren.htm")
public class ReserverenServlet extends HttpServlet implements MandjeConstanten {

    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/JSP/reserveren.jsp";
    private static final String REDIRECT_URL = "%s/mandje.htm";
    private final transient VoorstellingDAO voorstellingDAO = new VoorstellingDAO();

    @Resource(name = VoorstellingDAO.JNDI_CULTUURHUIS)
    void setDataSource(DataSource dataSource) {
        voorstellingDAO.setDataSource(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> fouten = new ArrayList<>();

        if (request.getParameterValues("voorstellingId") != null) {
            try {
                long voorstellingId = Long.parseLong(request.getParameter("voorstellingId"));
                Voorstelling voorstelling = voorstellingDAO.findByVoorstellingId(voorstellingId);

                if (voorstelling != null) {
                    request.setAttribute("voorstelling", voorstelling);
                } else {
                    fouten.add("Ongeldige voorstelling.");
                }
            } catch (NumberFormatException ex) {
                fouten.add("Ongeldig voorstelling nummer opgegeven.");
            }
        }
        request.setAttribute("fouten", fouten);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> fouten = new ArrayList<>();
        Map<String, String> formFouten = new HashMap<>();

        if (request.getParameterValues("voorstellingId") != null && request.getParameterValues("plaatsen") != null) {
            HttpSession session = request.getSession();
            @SuppressWarnings("unchecked")
            Map<Long, Integer> reservatiesInMandje = (HashMap<Long, Integer>) session.getAttribute("mandje");
            if (reservatiesInMandje == null) {
                reservatiesInMandje = new HashMap<>();
                session.setAttribute(MANDJE, reservatiesInMandje);
            }
            try {
                long voorstellingId = Long.parseLong(request.getParameter("voorstellingId"));
                int aantalPlaatsen = Integer.parseInt(request.getParameter("plaatsen"));
                int plaatsenBeschikbaar = voorstellingDAO.findByVoorstellingId(voorstellingId).getVrijePlaatsen();

                if (aantalPlaatsen <= plaatsenBeschikbaar) {
                    reservatiesInMandje.put(voorstellingId, aantalPlaatsen);
                    request.setAttribute("plaatsen", aantalPlaatsen);
                    request.setAttribute(MANDJE, reservatiesInMandje);
                    response.sendRedirect(response.encodeRedirectURL(String.format(REDIRECT_URL, request.getContextPath())));
                }
            } catch (NumberFormatException ex) {
                formFouten.put("getal", "Fout met nummering opgetreden.");
                request.setAttribute("formFouten", formFouten);
                request.getRequestDispatcher(VIEW + "?voorstellingId=" + Long.parseLong(request.getParameter("voorstellingId"))).forward(request, response);
            }
        } else {
            fouten.add("Ongeldig voorstelling nummer opgegeven.");
            request.setAttribute("fouten", fouten);
            request.getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}
