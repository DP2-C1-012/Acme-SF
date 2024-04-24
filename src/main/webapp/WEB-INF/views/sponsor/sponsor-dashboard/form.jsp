

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<h2>
	<acme:message code="sponsor.dashboard.form.title.sponsorships"/>
</h2>
<table class="table table-sm">
	<thead>
		<th><acme:message code="sponsor.dashboard.form.label.metrics"/></th>
		<th> <acme:print value="${acceptedCurrencies.get(0)}"/> </th>
		<th> <acme:print value="${acceptedCurrencies.get(1)}"/> </th>
		<th> <acme:print value="${acceptedCurrencies.get(2)}"/> </th>
	</thead>
	<tbody>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.average-sponsorship-amount"/>
			</th>
			<td>
				<acme:print value="${averageAmountOfSponsorships}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.min-sponsorship-amount"/>
			</th>
			<td>
				<acme:print value="${minimumAmountOfSponsorships}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.max-sponsorship-amount"/>
			</th>
			<td>
				<acme:print value="${maximumAmountOfSponsorships}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.lin-dev-sponsorship-amount"/>
			</th>
			<td>
				<acme:print value="${deviationAmountOfSponsorships}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.link-sponsorship"/>
			</th>
			<td>
				<acme:print value="${numberOfSponsorshipsWithALink}"/>
			</td>
		</tr>
	
	</tbody>
	</table>
	
<h2>
	<acme:message code="sponsor.dashboard.form.title.invoices"/>
</h2>

<table class="table table-sm">
	<thead>
		<th><acme:message code="sponsor.dashboard.form.label.metrics"/></th>
		<th> <acme:print value="${acceptedCurrencies.get(0)}"/> </th>
		<th> <acme:print value="${acceptedCurrencies.get(1)}"/> </th>
		<th> <acme:print value="${acceptedCurrencies.get(2)}"/> </th>
	</thead>
	<tbody>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.average-invoice-amount"/>
			</th>
			<td>
				<acme:print value="${averageQuantityOfInvoices}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.max-invoice-amount"/>
			</th>
			<td>
				<acme:print value="${maximumQuantityOfInvoices}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.min-invoice-amount"/>
			</th>
			<td>
				<acme:print value="${minimumQuantityOfInvoices}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.lin-dev-invoice-amount"/>
			</th>
			<td>
				<acme:print value="${deviationQuantityOfInvoices}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.21orless-invoice"/>
			</th>
			<td>
				<acme:print value="${numberOfInvoicesWithATaskLessOrEqualThan21}"/>
			</td>
		</tr>
	</tbody>
	</table>
<acme:return/>