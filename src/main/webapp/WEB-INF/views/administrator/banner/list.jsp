<%@ page language="java" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:list>
    <acme:list-column code="administrator.banner.list.label.instantiationMomment" path="instantiationMomment"  width="34%" />
    <acme:list-column code="administrator.banner.list.label.displayPeriodStart" path="displayPeriodStart" width="32%" />
    <acme:list-column code="administrator.banner.list.label.displayPeriodEnd" path="displayPeriodEnd" width="32%" />
</acme:list>
<jstl:if test="${_command == 'list'}">
	<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>
</jstl:if>