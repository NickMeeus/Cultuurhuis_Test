package be.vdab.servlets;

import be.vdab.dao.VoorstellingDAO;
import be.vdab.entities.Voorstelling;
import static be.vdab.servlets.MandjeConstanten.MANDJE;
import java.io.IOException;
import java.math.BigDecimal;
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

@WebServlet("/mandje.htm")
public class MandjeServlet extends HttpServlet implements MandjeConstanten {

    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/JSP/mandje.jsp";
    private final transient VoorstellingDAO voorstellingDAO = new VoorstellingDAO();

    @Resource(name = VoorstellingDAO.JNDI_CULTUURHUIS)
    void setDataSource(DataSource dataSource) {
        voorstellingDAO.setDataSource(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> fouten = new ArrayList<>();
        HttpSession session = request.getSession();

        @SuppressWarnings("unchecked")
        Map<Long, Integer> mandje = (HashMap<Long, Integer>) session.getAttribute(MANDJE);
        if (mandje == null) {
            mandje = new HashMap<>();
            session.setAttribute(MANDJE, mandje);
        }
        Map<Voorstelling, Integer> reservaties = new HashMap<>();
        BigDecimal totalePrijs = BigDecimal.ZERO;

        if (!mandje.isEmpty()) {
            List<Voorstelling> voorstellingenInMandje = voorstellingDAO.findByVoorstellingenIds(mandje.keySet());
            for (Voorstelling voorstelling : voorstellingenInMandje) {
                int aantalPlaatsen = mandje.get(voorstelling.getId());
                reservaties.put(voorstelling, aantalPlaatsen);
                BigDecimal voorstellingPrijs = voorstelling.getPrijs();
                BigDecimal tussenPrijs = voorstellingPrijs.multiply(new BigDecimal(aantalPlaatsen));
                totalePrijs = totalePrijs.add(tussenPrijs);
            }
            request.setAttribute("reservaties", reservaties);
            request.setAttribute("totalePrijs", totalePrijs);
        } else {
            fouten.add("Er zit niets in het winkelmandje.");
            request.setAttribute("fouten", fouten);
        }
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> fouten = new ArrayList<>();
        if (request.getParameterValues("id") != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                @SuppressWarnings("unchecked")
                Map<Long, Integer> mandje = (HashMap<Long, Integer>) session.getAttribute(MANDJE);
                if (!mandje.isEmpty()) {
                    String[] teVerwijderen = request.getParameterValues("id");
                    for (String verwijderen : teVerwijderen) {
                        try {
                            if (mandje.remove(Long.parseLong(verwijderen)) == null) {
                                fouten.add("Een reservatie die je wil verwijderen bestaat niet.");
                            }
                        } catch (NumberFormatException ex) {
                            fouten.add("Een reservatie die je wil verwijderen is niet correct.");
                            request.setAttribute("fouten", fouten);
                            request.getRequestDispatcher(VIEW).forward(request, response);
                        }
                    }
                    if (mandje.isEmpty()) {
                        session.removeAttribute(MANDJE);
                    } else {
                        session.setAttribute(MANDJE, mandje);
                    }
                    response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
                } else {
                    fouten.add("Het winkelmandje is al leeg.");
                    request.setAttribute("fouten", fouten);
                    request.getRequestDispatcher(VIEW).forward(request, response);
                }
            }
        } else {
            fouten.add("Niets geselecteerd om te verwijderen.");
            request.setAttribute("fouten", fouten);
            request.getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}
