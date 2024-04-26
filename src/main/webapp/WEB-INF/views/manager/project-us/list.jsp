<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.userStory.list.label.title" path="title"  width="40%"/>
	<acme:list-column code="manager.userStory.list.label.description" path="description" width="40%" />
	<acme:list-column code="manager.userStory.list.label.estimatedCostPerHour" path="estimatedCostPerHour" width="20%" />
</acme:list>

<acme:button test="${showCreate}" code="manager.userStory.list.button.create" action="/manager/user-story/create?masterId=${masterId}"/>
<acme:button test="${showCreate}" code="manager.userStory.list.button.create" action="/manager/project-user-story/create?masterId=${masterId}"/>