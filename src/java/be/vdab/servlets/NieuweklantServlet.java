package be.vdab.servlets;

import be.vdab.dao.KlantDAO;
import be.vdab.entities.Klant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

@WebServlet("/nieuweklant.htm")
public class NieuweklantServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/JSP/nieuweklant.jsp";
    private static final String REDIRECT_URL = "%s/bevestig.htm";
    private final transient KlantDAO klantDAO = new KlantDAO();

    @Resource(name = KlantDAO.JNDI_CULTUURHUIS)
    void setDataSourceTag(DataSource dataSource) {
        klantDAO.setDataSource(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> fouten = new ArrayList<>();
        Map<String, String> nieuweGebruiker = new LinkedHashMap<>();

        nieuweGebruiker.put("voornaam", request.getParameter("voornaam"));
        nieuweGebruiker.put("familienaam", request.getParameter("familienaam"));
        nieuweGebruiker.put("straat", request.getParameter("straat"));
        nieuweGebruiker.put("huisnr", request.getParameter("huisnr"));
        nieuweGebruiker.put("postcode", request.getParameter("postcode"));
        nieuweGebruiker.put("gemeente", request.getParameter("gemeente"));
        nieuweGebruiker.put("gebruikersnaam", request.getParameter("gebruikersnaam"));
        nieuweGebruiker.put("paswoord", request.getParameter("paswoord"));
        nieuweGebruiker.put("herhaalpaswoord", request.getParameter("herhaalpaswoord"));

        for (Map.Entry<String, String> entry : nieuweGebruiker.entrySet()) {
            if (entry.getValue() == null || entry.getValue().equals("")) {
                fouten.add(entry.getKey() + " is niet ingevuld");
            }
        }

        if (fouten.isEmpty()) {
            if (nieuweGebruiker.get("paswoord").equals(nieuweGebruiker.get("herhaalpaswoord"))) {
                if (!klantDAO.BestaatGebruikersnaam(nieuweGebruiker.get("gebruikersnaam"))) {
                    Klant klant = new Klant(nieuweGebruiker.get("voornaam"), 
                                            nieuweGebruiker.get("familienaam"), 
                                            nieuweGebruiker.get("straat"), 
                                            nieuweGebruiker.get("huisnr"), 
                                            nieuweGebruiker.get("postcode"), 
                                            nieuweGebruiker.get("gemeente"), 
                                            nieuweGebruiker.get("gebruikersnaam"), 
                                            nieuweGebruiker.get("paswoord"));
                    klantDAO.create(klant);
                    HttpSession session = request.getSession(false);
                    session.setAttribute("klant", klant);
                    response.sendRedirect(response.encodeRedirectURL(String.format(REDIRECT_URL, request.getContextPath())));
                } else {
                    fouten.add("Gebruikersnaam bestaat al, kies een andere.");
                    request.setAttribute("fouten", fouten);
                    request.getRequestDispatcher(VIEW).forward(request, response);
                }

            } else {
                fouten.add("De wachtwoorden zijn niet hetzelfde, probeer het opnieuw.");
                request.setAttribute("fouten", fouten);
                request.getRequestDispatcher(VIEW).forward(request, response);
            }
        } else {
            request.setAttribute("fouten", fouten);
            request.getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}