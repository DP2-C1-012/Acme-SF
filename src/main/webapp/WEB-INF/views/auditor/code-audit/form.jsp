<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="auditor.codeAudit.form.label.code" path="code"/>
	<acme:input-moment code="auditor.codeAudit.form.label.executionDate" path="executionDate"/>
	<acme:input-select code="auditor.codeAudit.form.label.type" path="type" choices="${types}"/>
	<acme:input-select code="auditor.codeAudit.form.mark" path="mark" choices="${marks}" readonly="true"/>
	<acme:input-textarea code="auditor.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
	<acme:input-url code="auditor.codeAudit.form.label.optionalLink" path="optionalLink"/>
	<acme:input-select code="auditor.codeAudit.form.label.project" path="project" choices="${projects}"/>
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')  && published == false }">
			<acme:submit code="auditor.codeAudit.form.button.update" action="/auditor/code-audit/update"/>
			<acme:submit code="auditor.codeAudit.form.button.delete" action="/auditor/code-audit/delete"/>
			<acme:submit code="auditor.codeAudit.form.button.publish" action="/auditor/code-audit/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.codeAudit.form.button.create" action="/auditor/code-audit/create"/>
		</jstl:when>			
	</jstl:choose>

</acme:form>