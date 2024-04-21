<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="auditor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.totalDynamicCodeAudits"/>
		</th>
		<td>
			<acme:print value="${totalDynamicCodeAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.totalStaticCodeAudits"/>
		</th>
		<td>
			<acme:print value="${totalStaticCodeAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.minAuditRecords"/>
		</th>
		<td>
			<acme:print value="${minAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.maxAuditRecords"/>
		</th>
		<td>
			<acme:print value="${maxAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.averageNumAuditRecords"/>
		</th>
		<td>
			<acme:print value="${averageNumAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.deviationNumAuditRecords"/>
		</th>
		<td>
			<acme:print value="${deviationNumAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.minPeriodLength"/>
		</th>
		<td>
			<acme:print value="${minPeriodLength}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.maxPeriodLength"/>
		</th>
		<td>
			<acme:print value="${maxPeriodLength}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.averageNumPeriodLength"/>
		</th>
		<td>
			<acme:print value="${averageNumPeriodLength}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.deviationNumPeriodLength"/>
		</th>
		<td>
			<acme:print value="${deviationNumPeriodLength}"/>
		</td>
	</tr>
</table>
<acme:return/>