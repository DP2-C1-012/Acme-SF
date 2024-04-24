<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.sponsorship.list.label.code" path="code" width="10%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.type" path="type" width="40%"/>
	<acme:list-column code="sponsor.sponsorship.list.label.amount" path="amount" width="50%"/>	
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="sponsor.sponsorship.list.button.create" action="/sponsor/sponsorship/create"/>
</jstl:if>		
	

