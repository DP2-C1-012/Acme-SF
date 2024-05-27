<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.shouldUs"/>
		</th>
		<td>
			<acme:print value="${numberOfShouldUs}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.couldUs"/>
		</th>
		<td>
			<acme:print value="${numberOfCouldUs}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.mustUs"/>
		</th>
		<td>
			<acme:print value="${numberOfMustUs}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.wontUs"/>
		</th>
		<td>
			<acme:print value="${numberOfWontUs}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.averageEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${averageUserStoryCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviationEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${deviationOfUserStoryCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimunEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${minimumUserStoryCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximumEstimatedCostUserStories"/>
		</th>
		<td>
			<acme:print value="${maximumUserStoryCost}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.averageCostProjects"/>
		</th>
		<td>
			<acme:print value="${averageProjectCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.deviationCostProjects"/>
		</th>
		<td>
			<acme:print value="${deviationProjectCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.minimunCostProjects"/>
		</th>
		<td>
			<acme:print value="${minimumProjectCost}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.dashboard.form.label.maximumCostProjects"/>
		</th>
		<td>
			<acme:print value="${maximumProjectCost}"/>
		</td>
	</tr>
</table>
