<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.trainingModule.form.label.code" path="code" readonly="true"/>
	<acme:input-textbox code="developer.trainingModule.form.label.creationMoment" path="creationMoment" readonly="true"/>
	<acme:input-textbox code="developer.trainingModule.form.label.details" path="details" readonly="true"/>
	<acme:input-textbox code="developer.trainingModule.form.label.difficultyLevel" path="difficultyLevel" readonly="true"/>
	<acme:input-textbox code="developer.trainingModule.form.label.updateMoment" path="updateMoment" readonly="true"/>
	<acme:input-url code="developer.trainingModule.form.label.link" path="link" readonly="true"/>
</acme:form>
