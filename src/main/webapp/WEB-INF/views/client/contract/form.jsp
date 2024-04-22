<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:form>
    <acme:input-textbox code="contract.form.label.code" path="code"/>
    <acme:input-moment code="contract.form.label.moment" path="moment"/>
    <acme:input-textbox code="contract.form.label.provider" path="provider"/>
    <acme:input-textbox code="contract.form.label.customer" path="customer"/>
    <acme:input-textarea code="contract.form.label.goals" path="goals"/>
    <acme:input-textbox code="contract.form.label.budget" path="budget"/>
    <acme:input-integer code="contract.form.label.project" path="project"/>
    <acme:input-checkbox code="contract.form.label.draftMode" path="draftMode"/>
    <jstl:choose>
    	<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.contract.form.button.create" action="/client/contract/create"/>
		</jstl:when>
    </jstl:choose>
</acme:form>