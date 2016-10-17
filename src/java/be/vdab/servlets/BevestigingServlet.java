package be.vdab.servlets;

import be.vdab.dao.KlantDAO;
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

@WebServlet("/bevestig.htm")
public class BevestigingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/JSP/bevestig.jsp";
    private static final String REDIRECT_URL_OVERZICHT = "%s/overzicht.htm";
    private final transient KlantDAO klantDAO = new KlantDAO();
    private final transient VoorstellingDAO voorstellingDAO = new VoorstellingDAO();

    @Resource(name = KlantDAO.JNDI_CULTUURHUIS)
    void setDataSourceTag(DataSource dataSource) {
        klantDAO.setDataSource(dataSource);
        voorstellingDAO.setDataSource(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> fouten = new ArrayList<>();
        HttpSession session = request.getSession();

        if (request.getParameterValues("zoekknop") != null) {
            String gebruikersnaam = request.getParameter("gebruikersnaam");
            String paswoord = request.getParameter("paswoord");
            if (klantDAO.KanGebruikerInloggen(gebruikersnaam, paswoord)) {
                session.setAttribute("klant", klantDAO.findByGebruikersnaam(gebruikersnaam));
                request.getRequestDispatcher(VIEW).forward(request, response);
            } else {
                fouten.add("Verkeerde gebruikersnaam of paswoord.");
                request.setAttribute("fouten", fouten);
                request.getRequestDispatcher(VIEW).forward(request, response);
            }
        } else {
 //       if (request.getParameterValues("bevestigform") != null) {
            Map<Voorstelling, Integer> gelukteReservaties = new HashMap<>();
            Map<Voorstelling, Integer> mislukteReservaties = new HashMap<>();

            @SuppressWarnings("unchecked")
            Map<Long, Integer> reservatiesInMandje = (HashMap<Long, Integer>) session.getAttribute("mandje");

            if (reservatiesInMandje == null) {
                reservatiesInMandje = new HashMap<>();
                session.setAttribute(MANDJE, reservatiesInMandje);
            }
            if (!reservatiesInMandje.isEmpty()) {
                for (Map.Entry<Long, Integer> entry : reservatiesInMandje.entrySet()) {
                    Voorstelling voorstelling = voorstellingDAO.findByVoorstellingId(entry.getKey());
                    if (voorstelling.getVrijePlaatsen() >= entry.getValue()) {
                        int nieuweVrijePlaatsen = voorstelling.getVrijePlaatsen() - entry.getValue();
                        voorstellingDAO.UpdateVrijePlaatsen(entry.getKey(), nieuweVrijePlaatsen);
                        gelukteReservaties.put(voorstellingDAO.findByVoorstellingId(entry.getKey()), entry.getValue());
                    } else {
                        mislukteReservaties.put(voorstellingDAO.findByVoorstellingId(entry.getKey()), entry.getValue());
                    }
                }
                session.setAttribute("gelukteReservaties", gelukteReservaties);
                session.setAttribute("mislukteReservaties", mislukteReservaties);
                session.removeAttribute(MANDJE);
                response.sendRedirect(response.encodeRedirectURL(String.format(REDIRECT_URL_OVERZICHT, request.getContextPath())));
            } else {
                fouten.add("Er zit niets meer in het mandje, voeg opnieuw iets toe.");
                request.setAttribute("fouten", fouten);
                request.getRequestDispatcher(VIEW).forward(request, response);
            }
        }
    }
}
