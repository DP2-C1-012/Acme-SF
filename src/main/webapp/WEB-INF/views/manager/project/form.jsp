<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.project.form.label.code" path="code"/>	
	<acme:input-textbox code="manager.project.form.label.title" path="title"/>	
	<acme:input-textarea code="manager.project.form.label.abstrac" path="abstrac"/>	
	<acme:input-money code="manager.project.form.label.cost" path="cost"/>	
	<acme:input-url code="manager.project.form.label.link" path="link"/>
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="manager.project.userStories" action="/manager/user-story/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true }">
			<acme:input-checkbox code="manager.project.form.label.indication" path="indication"/>
			<acme:button code="manager.project.userStories" action="/manager/user-story/list?masterId=${id}"/>
			<acme:submit code="manager.project.form.button.update" action="/manager/project/update"/>
			<acme:submit code="manager.project.form.button.delete" action="/manager/project/delete"/>
			<acme:submit code="manager.project.form.button.publish" action="/manager/project/publish"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true }">
			<acme:input-checkbox code="manager.project.form.label.indication" path="indication"/>
			<acme:input-money code="manager.project.form.label.money" path="money" readonly="true"/>
			<acme:button code="manager.project.userStories" action="/manager/user-story/list?masterId=${id}"/>
			<acme:submit code="manager.project.form.button.update" action="/manager/project/update"/>
			<acme:submit code="manager.project.form.button.delete" action="/manager/project/delete"/>
			<acme:submit code="manager.project.form.button.publish" action="/manager/project/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.project.form.button.create" action="/manager/project/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>