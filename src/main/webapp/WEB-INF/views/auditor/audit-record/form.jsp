<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.auditRecord.form.label.code" path="code"/>
	<acme:input-moment code="auditor.auditRecord.form.label.periodStart" path="periodStart"/>
	<acme:input-moment code="auditor.auditRecord.form.label.periodEnd" path="periodEnd"/>
	<acme:input-select code="auditor.auditRecord.form.mark" path="mark" choices="${marks}"/>
	<acme:input-select code="auditor.auditRecord.form.codeAudit" path="codeAudit" choices="${codeAudits}"/>
	<acme:input-url code="auditor.auditRecord.form.label.optionalLink" path="optionalLink"/>
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')  && published == false }">
			<acme:submit code="auditor.auditRecord.form.button.update" action="/auditor/audit-record/update"/>
			<acme:submit code="auditor.auditRecord.form.button.delete" action="/auditor/audit-record/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditRecord.form.button.create" action="/auditor/audit-record/create"/>
		</jstl:when>			
	</jstl:choose>

</acme:form>