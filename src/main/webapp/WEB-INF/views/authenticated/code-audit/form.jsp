<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">

	<acme:input-textbox code="authenticated.codeAudit.form.label.code" path="code"/>
	<acme:input-moment code="authenticated.codeAudit.form.label.executionDate" path="executionDate"/>
	<acme:input-select code="authenticated.codeAudit.form.label.type" path="type" choices="${types}"/>
	<acme:input-select code="authenticated.codeAudit.form.mark" path="mark" choices="${marks}" readonly="true"/>
	<acme:input-textarea code="authenticated.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
	<acme:input-url code="authenticated.codeAudit.form.label.optionalLink" path="optionalLink"/>
	<acme:input-select code="authenticated.codeAudit.form.label.project" path="project" choices="${projects}"/>
	<acme:button code="authenticated.codeAudit.form.button.list.auditRecords" action="/authenticated/audit-record/list-of-code-audit?id=${id}"/>

</acme:form>