<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<!DOCTYPE html>
<html>
    <head>
        <vdab:head />
    </head>
    <body>
        <section class="clearFix">
            <h1>Het Cultuurhuis: bevestiging reservaties</h1>
            <img src="<c:url value="/images/bevestig.png"/>" alt="bevestig"/>
        </section>

        <vdab:navigatie huidigePagina="bevestig"/>

        <h2>Stap 1: wie ben je?</h2>


        <form method="post" id="zoekform" action="<c:url value="/bevestig.htm"/>">

            <p class="pmargin">Gebruikersnaam:</p>
            <div><input name="gebruikersnaam" type="text" id="naam" value="${klant.gebruikersnaam}" autofocus/></label></div>
            <p class="pmargin">Paswoord:</p>
            <div><input name="paswoord" type="password" id="bevestigpaswoord" value="${klant.paswoord}"/></label></div>
            <div><input name="zoekknop" type="submit" value="Zoek me op" id="zoekknop" <c:if test="${not empty klant}">disabled="disabled"</c:if>/></div>
            </form>
            
            <div>
                <form action="<c:url value="/nieuweklant.htm"/>" method="get">
                <input type="submit" value="Ik ben nieuw" name="nieuwknop" id="nieuwknop" <c:if test="${not empty klant}">disabled="disabled"</c:if> />
            </form>
        </div>

        <c:if test="${not empty klant}">
            <h5>${klant.voornaam} ${klant.familienaam} ${klant.straat} ${klant.huisnr} ${klant.postcode} ${klant.gemeente}</h5>
        </c:if>
        <c:if test="${not empty fouten}">
            <h5><c:forEach var="fout" items="${fouten}">${fout}</c:forEach></h5>
        </c:if>
        <h2>Stap 2: Bevestigen</h2>

        <form name="bevestigknop" method="post" id="bevestigform" >
            <input value="${param.gebruikersnaam}" type="hidden"/>
            <input type="submit" value="Bevestigen" id="bevestigknop" <c:if test="${empty klant}">disabled="disabled"</c:if>/>
        </form>

        <script>
            document.getElementById('zoekknop').onsubmit = function () {
                document.getElementById('zoekknop').disabled = true;
            };
            document.getElementById('nieuwknop').onsubmit = function () {
                document.getElementById('nieuwknop').disabled = true;
            };
            document.getElementById('bevestigknop').onsubmit = function () {
                document.getElementById('bevestigknop').disabled = true;
            };
        </script>
    </body>
</html>