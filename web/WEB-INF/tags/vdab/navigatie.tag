<%@tag description="snelnavigatie tussen paginas" pageEncoding="UTF-8"%>
<%@attribute name="huidigePagina" required="false" type="java.lang.String"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav>
    <c:if test="${huidigePagina ne 'index'}">
        <a href="<c:url value="/"/>">Voorstellingen</a>
    </c:if>
    <c:if test="${not empty mandje}">
        <c:if test="${huidigePagina ne 'mandje'}">
            <a href="<c:url value="/mandje.htm"/>">Reservatiemandje</a>
        </c:if>
        <c:if test="${huidigePagina ne 'bevestig'}">
            <a href="<c:url value="/bevestig.htm"/>">Bevestiging Reservatie</a>
        </c:if>
    </c:if>
</nav>