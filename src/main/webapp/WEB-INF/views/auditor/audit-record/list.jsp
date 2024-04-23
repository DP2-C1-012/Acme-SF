<%@ page language="java" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:list>
    <acme:list-column code="auditor.auditRecord.list.label.code" path="code"  width="40%" />
    <acme:list-column code="auditor.auditRecord.list.label.periodStart" path="periodStart" width="25%" />
    <acme:list-column code="auditor.auditRecord.list.label.periodEnd" path="periodEnd" width="25%" />
    <acme:list-column code="auditor.auditRecord.list.label.mark" path="mark" width="10%" />
</acme:list>
<jstl:if test="${_command == 'list'}">
	<acme:button code="auditor.auditRecord.list.button.create" action="/auditor/audit-record/create"/>
</jstl:if>