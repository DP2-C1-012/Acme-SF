<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.risk.form.label.reference" path="reference" />
	<acme:input-moment code="authenticated.risk.form.label.identificationDate" path="identificationDate" />
	<acme:input-moment code="authenticated.risk.form.label.impact" path="impact"/>	
	<acme:input-moment code="authenticated.risk.form.label.probability" path="probability" />
	<acme:input-moment code="authenticated.risk.form.label.description" path="description" />
	<acme:input-moment code="authenticated.risk.form.label.link" path="link" />
</acme:form>