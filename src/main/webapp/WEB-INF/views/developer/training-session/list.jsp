<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.training-session.list.label.code" path="code" width="15%"/>
	<acme:list-column code="developer.training-session.list.label.location" path="location" width="35%" />	
	<acme:list-column code="developer.training-session.list.label.instructor" path="instructor" width="20%"/>
	<acme:list-column code="developer.training-session.list.label.draftMode" path="draftMode" width="20%"/>
	<acme:list-column code="developer.training-session.list.label.module" path="module" width="20%" />

</acme:list>
<jstl:choose>	
	<jstl:when test="${_command == 'list' && mode == true}">
		<acme:button code="developer.training-session.list.button.create" action="/developer/training-session/create?trainingModuleId=${trainingModuleId}"/>
	</jstl:when>			
</jstl:choose>	
