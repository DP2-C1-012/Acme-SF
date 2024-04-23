<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.systemConfiguration.form.label.defaultCurrency" path="defaultCurrency"/>
	<acme:input-textbox code="administrator.systemConfiguration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	<acme:submit code="administrator.systemConfiguration.form.button.update" action="/administrator/system-configuration/update"/>
</acme:form>