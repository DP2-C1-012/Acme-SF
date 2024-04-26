
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.userstory.list.label.title" path="title"  width="40%"/>
	<acme:list-column code="manager.project.list.label.abstrac" path="description" width="40%" />
	<acme:list-column code="manager.project.list.label.cost" path="estimatedCost" width="20%" />
</acme:list>
<acme:button code="manager.userstory.create" action="/manager/user-story/create"/>
