<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<!DOCTYPE html>
<html lang="nl">
    <head>
        <vdab:head title="Het CultuurHuis"/>
    </head>
    <body>
        <section id="top" class="clearFix">
            <h1>Het Cultuurhuis: voorstellingen</h1>
            <img src="<c:url value="/images/voorstellingen.png"/>" alt="voorstellingen">
        </section>
        <section id="genres" class="clearFix">
            <h2>Genres</h2>
            <nav>
                <ul>
                    <c:if test="${not empty genres}">
                        <c:forEach var="genre" items="${genres}">
                            <c:url var="url" value="">
                                <c:param name="genreId" value="${genre.id}" />
                            </c:url>
                            <li><a href="<c:url value="${url}"/>">${genre.naam}</a></li>
                            </c:forEach>
                        </c:if>
                </ul>
            </nav>
        </section>

        <c:if test="${not empty genre}">
            <h2>${genre.naam} voorstellingen</h2>
        </c:if>
        <c:if test="${not empty fouten}">
            <c:forEach var="fout" items="${fouten}">
                ${fout}
            </c:forEach>
        </c:if>
        <c:if test="${empty fouten and not empty voorstellingen}">
        <thead>
            <tr> 
                <th>Datum</th>
                <th>Titel</th>
                <th>Uitvoerders</th>
                <th>Prijs</th>
                <th>Vrije plaatsen</th>
                <th>Reserveren</th>
            </tr>
        </thead> 
        <tbody>
            <c:forEach var="voorstelling" items="${voorstellingen}">
                <tr>
                    <td><fmt:formatDate value="${voorstelling.datum}" type="both" dateStyle="short" timeStyle="short"/></td>
                    <td>${voorstelling.titel}</td>
                    <td>${voorstelling.uitvoerders}</td>
                    <td>&euro; ${voorstelling.prijs}</td>
                    <td>${voorstelling.vrijePlaatsen}</td>
                    <c:url var="url" value="/reserveren.htm">
                        <c:param name="voorstellingID" value="${voorstelling.id}" />
                    </c:url>
                    <td><c:if test='${voorstelling.vrijePlaatsen gt 0}'>
                        <a href="${url}">Reserveren</a>
                    </c:if></td>
                </tr>
            </c:forEach>
        </tbody>
    </c:if>
</body>
</html>