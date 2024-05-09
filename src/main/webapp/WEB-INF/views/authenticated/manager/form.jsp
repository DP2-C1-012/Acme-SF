<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.manager.form.label.degree" path="degree"/>
	<acme:input-textbox code="authenticated.manager.form.label.overview" path="overview"/>
	<acme:input-textbox code="authenticated.manager.form.label.certifications" path="certifications"/>
	<acme:input-url code="authenticated.manager.form.label.link" path="link"/>

	<acme:submit test="${_command == 'create'}" code="authenticated.manager.form.button.create" action="/authenticated/manager/create"/>
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="authenticated.manager.form.button.update" action="/authenticated/manager/update"/>
	</jstl:if>	
</acme:form>