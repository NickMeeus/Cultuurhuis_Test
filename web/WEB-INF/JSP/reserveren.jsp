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
            <h1>Het Cultuurhuis: reserveren</h1>
            <img src="<c:url value="/images/reserveren.png"/>" alt="reserveren"/>
        </section>
        
        <vdab:navigatie/>

        <c:if test="${empty fouten}">
            <article id="reserveren">
                <h5>Voorstelling:</h5>
                <p>${voorstelling.titel}</p>

                <h5>Uitvoerders:</h5>
                <p>${voorstelling.uitvoerders}</p>

                <h5>Datum:</h5>
                <p><fmt:formatDate value="${voorstelling.datum}" type="both" dateStyle="short" timeStyle="short"/></p>

                <h5>Prijs:</h5>
                <p>&euro;${voorstelling.prijs}</p>

                <h5>Vrije plaatsen:</h5>
                <p>${voorstelling.vrijePlaatsen}</p>

                
                <h5>Plaatsen:</h5>
                <form method="post" id="reserveerform" action="<c:url value="/reserveren.htm"/>">
                    <div>
                        <span>${formFouten.getal}</span>
                        <input name="plaatsen" type="number" min="1" max="${voorstelling.vrijePlaatsen}" id="plaatsen" value="${plaatsen}" autofocus required/>
                        <input name="voorstellingId" type="hidden" value="${voorstelling.id}"/>
                    </div>
                    <div>
                        <input type="submit" value="Reserveren" id="reserveerknop"/>
                    </div>
                </form>
                <script>
                    document.getElementById('reserveerknop').onsubmit = function () {
                        document.getElementById('reserveerknop').disabled = true;
                    };
                </script>
            </article>
        </c:if>

        <c:if test='${not empty fouten}'>
            <c:forEach var='fout' items='${fouten}'>
                <p>${fout}</p>
            </c:forEach>
        </c:if>
    </body>
</html>