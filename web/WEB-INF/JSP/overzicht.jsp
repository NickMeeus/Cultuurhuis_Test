<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<!DOCTYPE html>
<html>
    <head>
        <vdab:head />
    </head>
    <body>
        <section class="clearFix">
            <h1>Het Cultuurhuis: overzicht</h1>
            <img src="<c:url value="/images/bevestig.png"/>" alt="bevestig"/>
        </section>

        <vdab:navigatie/>
        
        
        <c:if test="${not empty gelukteReservaties}">
            <h2>Gelukte reservaties</h2>
            <table>
                <thead>
                    <tr> 
                        <th>Datum</th>
                        <th>Titel</th>
                        <th>Uitvoerders</th>
                        <th>Prijs(&euro;)</th>
                        <th>Plaatsen</th>
                    </tr>
                </thead> 
                <tbody>            
                    <c:forEach var="reservatie" items="${gelukteReservaties}">
                        <tr>
                            <td><fmt:formatDate value="${reservatie.key.datum}" type="both" dateStyle="short" timeStyle="short"/></td>
                                <td>${reservatie.key.titel}</td>
                                <td>${reservatie.key.uitvoerders}</td>
                                <td>${reservatie.key.prijs}</td>
                                <td>${reservatie.value}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${not empty mislukteReservaties}">
            <h2>Mislukte reservaties</h2>
            <table>
                <thead>
                    <tr> 
                        <th>Datum</th>
                        <th>Titel</th>
                        <th>Uitvoerders</th>
                        <th>Prijs(&euro;)</th>
                        <th>Plaatsen</th>
                    </tr>
                </thead> 
                <tbody>            
                    <c:forEach var="reservatie" items="${mislukteReservaties}">
                        <tr>
                            <td><fmt:formatDate value="${reservatie.key.datum}" type="both" dateStyle="short" timeStyle="short"/></td>
                                <td>${reservatie.key.titel}</td>
                                <td>${reservatie.key.uitvoerders}</td>
                                <td>${reservatie.key.prijs}</td>
                                <td>${reservatie.value}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>
