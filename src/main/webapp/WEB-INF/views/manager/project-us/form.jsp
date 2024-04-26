<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
	<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.projectUs.form.userStory.title"/>
		</th>
		<td>
			<acme:print value="${userStory.getTitle()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.projectUs.form.userStory.description"/>
		</th>
		<td>
			<acme:print value="${userStory.getDescription()}"/>
		</td>
	</tr>
	</table>

<acme:form>	
	<acme:input-select code="manager.projectUs.form.project" path="project" choices="${projects}"/>
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'delete')}">
			<acme:submit code="manager.projectUs.form.button.delete" action="/manager/project-us/delete?userStoryId=${userStoryId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.projectUs.form.button.create" action="/manager/project-us/create?userStoryId=${userStoryId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>