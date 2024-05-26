<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.average-contract-budget"/>
		</th>
		<td><acme:print value="${averageBudget}"/></td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.min-contract-budget"/>
		</th>
		<td><acme:print value="${minimumBudget}"/></td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.max-contract-budget"/>
		</th>
		<td><acme:print value="${maximunBudget}"/></td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.lin-dev-contract-budget"/>
		</th>
		<td><acme:print value="${desviationBudget}"/></td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.progress-Log-with-less25"/>
		</th>
		<td><acme:print value="${numPLCompletenessBelow25}"/></td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.progress-Log-25to50"/>
		</th>
		<td><acme:print value="${numPLCompletenessBetween25To50}"/></td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.progress-Log-50to75"/>
		</th>
		<td><acme:print value="${numPLCompletenessBetween50To75}"/></td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="client.clientDashboard.form.label.progress-Log-with-over75"/>
		</th>
		<td><acme:print value="${numPLCompletenessAbove75}"/></td>
	</tr>	
</table>

<acme:return/>