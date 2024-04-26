<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="developer.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.num-tm-developer"/>
		</th>
		<td>
			<acme:print value="${numOfTrainingModuleOfDeveloper}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.num-ts-link"/>
		</th>
		<td>
			<acme:print value="${numTrainingSessionsWithLink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.avg-tm-time"/>
		</th>
		<td>
			<acme:print value="${averageTrainingModuleTime}"/>
		</td>
	</tr>

	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.min-tm-time"/>
		</th>
		<td>
			<acme:print value="${minTrainingModuleTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.max-tm-time"/>
		</th>
		<td>
			<acme:print value="${maxTrainingModuleTime}"/>
		</td>
	</tr>
</table>
<acme:return/>