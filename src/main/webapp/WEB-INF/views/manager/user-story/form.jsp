<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.userstory.form.label.title" path="title"/>	
	<acme:input-textarea code="manager.userstory.form.label.description" path="description"/>	
	<acme:input-money code="manager.userstory.form.label.estimatedCost" path="estimatedCost"/>	
	<acme:input-textarea code="manager.userstory.form.label.acceptanceCriteria" path="acceptanceCriteria"/>	
	<acme:input-select code="manager.userstory.form.label.priority" path="priority" choices="${priorities}"/>	
	<acme:input-url code="manager.userstory.form.label.link" path="link"/>
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == false}">
			<acme:button code="manager.userstory.list.button.deletePublishedUserStory" action="/manager/project-us/delete?userStoryId=${id}"	/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true }">
			<acme:submit code="manager.userstory.form.button.update" action="/manager/user-story/update"/>
			<acme:submit code="manager.userstory.form.button.delete" action="/manager/user-story/delete"/>
			<acme:submit code="manager.userstory.form.button.publish" action="/manager/user-story/publish"/>
			<acme:button code="manager.user-story.list.button.deleteFromProject" action="/manager/project-us/delete?userStoryId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.userstory.list.button.create" action="/manager/user-story/create"/>
		</jstl:when>
	</jstl:choose>
	
</acme:form>