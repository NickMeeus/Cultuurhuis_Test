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
            <h1>Het Cultuurhuis: nieuwe klant</h1>
            <img src="<c:url value="/images/nieuweklant.png"/>" alt="nieuweklant"/>
        </section>

        <vdab:navigatie />

        <form method="post" id="nieuwform" action="<c:url value="/nieuweklant.htm"/>">
            <p class="pnieuw">Voornaam:</p>
            <div><input name="voornaam" type="text" class="nieuwbreed" id="voornaam" value="${param.voornaam}" autofocus /></div>
            <p class="pnieuw">Familienaam:</p>
            <div><input name="familienaam" type="text" class="nieuwbreed" id="familienaam" value="${param.familienaam}" /></div>
            <p class="pnieuw">Straat:</p>
            <div><input name="straat" type="text" class="nieuwbreed" id="straat" value="${param.straat}" /></div>
            <p class="pnieuw">Huisnr:</p>
            <div><input name="huisnr" type="text" class="nieuwbreed" id="huisnr" value="${param.huisnr}" /></div>
            <p class="pnieuw">Postcode:</p>
            <div><input name="postcode" type="text" class="nieuwbreed" id="postcode" value="${param.postcode}" /></div>
            <p class="pnieuw">Gemeente:</p>
            <div><input name="gemeente" type="text" class="nieuwbreed" id="gemeente" value="${param.gemeente}" /></div>
            <p class="pnieuw">Gebruikersnaam:</p>
            <div><input name="gebruikersnaam" type="text" class="nieuwbreed" id="gebruikersnaam" value="${param.gebruikersnaam}" /></div>
            <p class="pnieuw">Paswoord:</p>
            <div><input name="paswoord" type="password" class="nieuwsmal" id="paswoord" /></div>
            <p class="pnieuw">Herhaal paswoord:</p>
            <div><input name="herhaalpaswoord" type="password" class="nieuwsmal" id="herhaalpaswoord" /></div>

            <div><input type="submit" value="OK" id="okknop"/></div>
        </form>

        <c:if test='${not empty fouten}'>
            <article class="clearFix">
                <ul id="klantFouten" >
                    <c:forEach var='fout' items='${fouten}'>
                        <li>${fout}</li>
                        </c:forEach>
                </ul>
                </article>
            </c:if>

    </body>
</html>
