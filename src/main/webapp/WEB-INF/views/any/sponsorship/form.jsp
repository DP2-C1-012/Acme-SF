<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.sponsorship.form.label.code" path="code"/>
	<acme:input-moment code="any.sponsorship.form.label.startDate" path="startDate"/>	
	<acme:input-moment code="any.sponsorship.form.label.endDate" path="endDate"/>	
	<acme:input-money code="any.sponsorship.form.label.amount" path="amount"/>	
	<acme:input-url code="any.sponsorship.form.label.link" path="link"/>	
	<acme:input-textbox code="any.sponsorship.form.label.contact" path="contact"/>
</acme:form>