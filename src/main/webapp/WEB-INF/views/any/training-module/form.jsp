<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.trainingModule.form.label.code" path="code"/>	
	<acme:input-textarea code="any.trainingModule.form.label.details" path="details"/>	
	<acme:input-textbox code="any.trainingModule.form.label.difficultyLevel" path="difficultyLevel"/>
		<acme:input-moment code="any.trainingModule.form.label.creationMoment" path="creationMoment"/>	
	<acme:input-moment code="any.trainingModule.form.label.updateMoment" path="updateMoment"/>	
	<acme:input-url code="any.trainingModule.form.label.link" path="link"/>	
	<acme:input-textbox code="any.trainingModule.form.label.time" path="time" readonly="true"/>
	<acme:input-textbox code="any.trainingModule.form.label.developer" path="degree"/>
	<acme:input-textbox code="developer.trainingModule.form.label.projectCode" path="projectCode" readonly="true"/>
</acme:form>