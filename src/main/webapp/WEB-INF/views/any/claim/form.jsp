<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
		<jstl:if test="${_command == 'show'}">
			<acme:input-moment code="any.claim.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
		</jstl:if>
		<acme:input-textbox code="any.claim.form.label.code" path="code"/>	
		<acme:input-moment code="any.claim.form.label.instantiationMoment" path="instantiationMoment"/>	
		<acme:input-textbox code="any.claim.form.label.heading" path="heading"/>	
		<acme:input-textbox code="any.claim.form.label.description" path="description"/>	
		<acme:input-textbox code="any.claim.form.label.department" path="department"/>	
		<acme:input-email code="any.claim.form.label.email" path="email"/>	
		<acme:input-url code="any.claim.form.label.link" path="link"/>
	<jstl:if test="${_command == 'publish'}">
		<acme:input-checkbox code="any.claim.form.label.confirmation" path="confirmation" />
	</jstl:if>
	
	
	<jstl:if test="${_command == 'publish'}">
		<acme:submit code="any.claim.form.button.publish" action="/any/claim/publish" />
	</jstl:if>	
</acme:form>