<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<!DOCTYPE html>
<html lang="nl">
    <head>
        <vdab:head />
    </head>
    <body>
        <section class="clearFix">
            <h1>Het Cultuurhuis: reservatiemandje</h1>
            <img src="<c:url value="/images/mandje.png"/>" alt="mandje"/>
        </section>

        <vdab:navigatie huidigePagina="mandje"/>

        <c:if test="${not empty fouten}">
            <c:forEach var="fout" items="${fouten}">
                <p>${fout}</p>
            </c:forEach>
        </c:if>

        <c:if test="${empty fouten and not empty reservaties}">
            <table>
                <form method="post" id="verwijderform">
                    <thead>
                        <tr> 
                            <th>Datum</th>
                            <th>Titel</th>
                            <th>Uitvoerders</th>
                            <th>Prijs</th>
                            <th>Plaatsen</th>
                            <th><input type="submit" value="Verwijderen"/></th>
                        </tr>
                    </thead> 
                    <tbody>            
                        <c:forEach var="reservatie" items="${reservaties}">
                            <tr>
                                <td><fmt:formatDate value="${reservatie.key.datum}" type="both" dateStyle="short" timeStyle="short"/></td>
                                <td>${reservatie.key.titel}</td>
                                <td>${reservatie.key.uitvoerders}</td>
                                <td>&euro; ${reservatie.key.prijs}</td>
                                <td id="vrijePlaatsen">${reservatie.value}</td>
                                <td><input type='checkbox' name='id' value='${reservatie.key.id}'></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </form>
                <script>
                    document.getElementById('verwijderknop').onsubmit = function () {
                        document.getElementById('verwijderknop').disabled = true;
                    };
                </script>
            </table>
            <p>Te betalen: &euro;${totalePrijs}</p>
        </c:if>
    </body>
</html>
