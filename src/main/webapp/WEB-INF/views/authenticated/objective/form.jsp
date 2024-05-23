<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.objective.form.label.title" path="title" />
	<acme:input-textbox code="authenticated.objective.form.label.description" path="description" />
	<acme:input-moment code="authenticated.objective.form.label.instantiationMoment" path="instantiationMoment"/>	
	<acme:input-moment code="authenticated.objective.form.label.startPeriod" path="startPeriod" />
	<acme:input-moment code="authenticated.objective.form.label.endPeriod" path="endPeriod" />
	<acme:input-checkbox code="authenticated.objective.form.label.priority" path="priority" />
	<acme:input-url code="authenticated.objective.form.label.link" path="link" />
</acme:form>