<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:input-textbox code="authenticated.auditRecord.form.label.code" path="code"/>
	<acme:input-moment code="authenticated.auditRecord.form.label.periodStart" path="periodStart"/>
	<acme:input-moment code="authenticated.auditRecord.form.label.periodEnd" path="periodEnd"/>
	<acme:input-select code="authenticated.auditRecord.form.mark" path="mark" choices="${marks}"/>
	<acme:input-select code="authenticated.auditRecord.form.codeAudit" path="codeAudit" choices="${codeAudits}"/>
	<acme:input-url code="authenticated.auditRecord.form.label.optionalLink" path="optionalLink"/>
</acme:form>