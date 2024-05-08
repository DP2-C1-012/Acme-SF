<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="developer.trainingModule.form.label.code" path="code" placeholder="developer.training-module.form.placeholder.code"/>
	<acme:input-moment code="developer.trainingModule.form.label.creation-moment" path="creationMoment"/>
	<acme:input-moment code="developer.trainingModule.form.label.update-moment" path="updateMoment"/>
	<acme:input-textarea code="developer.trainingModule.form.label.details" path="details"/>
	<acme:input-select code="developer.trainingModule.form.label.difficultyLevel" path="difficultyLevel" choices="${difficultyLevels}"/>
	<acme:input-url code="developer.trainingModule.form.label.link" path="link" placeholder="developer.training-module.form.placeholder.link" />
	<acme:input-integer code="developer.trainingModule.form.label.total-time" path="time"  readonly="true"/>
	<acme:input-checkbox code="developer.training-module.form.label.draft-mode" path="draftMode"/>	
	<acme:input-select code="developer.trainingModule.form.label.project" path="project" choices="${projects}"/>
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="developer.training-module.form.button.training-session" action="/developer/training-session/list?trainingModuleId=${id}"/>		
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')  && draftMode == true }">
			<acme:button code="developer.training-module.form.button.training-session" action="/developer/training-session/list?trainingModuleId=${id}"/>
			<acme:submit code="developer.training-module.form.button.update" action="/developer/training-module/update"/>
			<acme:submit code="developer.training-module.form.button.delete" action="/developer/training-module/delete"/>
			<acme:submit code="developer.training-module.form.button.publish" action="/developer/training-module/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training-module.form.button.create" action="/developer/training-module/create"/>
		</jstl:when>			
	</jstl:choose>

</acme:form>