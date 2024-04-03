<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="developer.trainingModule.form.label.code" path="code" readonly="true"/>
	<acme:input-moment code="developer.trainingModule.form.label.creation-moment" path="creationMoment" readonly="true"/>
	<acme:input-moment code="developer.trainingModule.form.label.update-moment" path="updateMoment" readonly="true"/>
	<acme:input-textarea code="developer.trainingModule.form.label.details" path="details" readonly="true"/>
	<acme:input-textbox code="developer.trainingModule.form.label.difficulty-level" path="difficultyLevel" readonly="true"/>
	<acme:input-url code="developer.trainingModule.form.label.link" path="link" readonly="true"/>
	<acme:input-integer code="developer.trainingModule.form.label.total-time" path="totalTime" readonly="true"/>
	<acme:input-integer code="developer.trainingModule.form.label.project" path="project" readonly="true"/>
	
</acme:form>