<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:form>
    <acme:input-moment code="contract.form.label.moment" path="moment"/>
    <acme:input-textbox code="contract.form.label.provider" path="provider"/>
    <acme:input-textbox code="contract.form.label.customer" path="customer"/>
    <acme:input-textarea code="contract.form.label.goals" path="goals"/>
    <acme:input-textbox code="contract.form.label.budget" path="budget"/>
    <acme:input-checkbox code="contract.form.label.draftMode" path="draftMode"/>
</acme:form>