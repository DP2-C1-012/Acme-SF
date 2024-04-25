

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<h2>
	<acme:message code="sponsor.dashboard.form.title.sponsorships"/>
</h2>
<table class="table table-sm">
	<thead>
	<tr>
			<th id="metrics"><acme:message code="sponsor.dashboard.form.label.metrics"/></th>
			<th id="EUR"> <acme:print value="${acceptedCurrencies.get(0)}"/> </th>
			<th id="GBP"> <acme:print value="${acceptedCurrencies.get(1)}"/> </th>
			<th id="USD"> <acme:print value="${acceptedCurrencies.get(2)}"/> </th>
			<th id="total"><acme:message code="sponsor.dashboard.form.label.total"/></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.average-sponsorship"/>
			</th>
			<td>
				<acme:print value="${averageAmountOfSponsorships.get(0)}"/>
			</td>
			<td>
				<acme:print value="${averageAmountOfSponsorships.get(1)}"/>
			</td>
			<td>
				<acme:print value="${averageAmountOfSponsorships.get(2)}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.min-sponsorship"/>
			</th>
			<td>
				<acme:print value="${minimumAmountOfSponsorships.get(0)}"/>
			</td>
			<td>
				<acme:print value="${minimumAmountOfSponsorships.get(1)}"/>
			</td>
			<td>
				<acme:print value="${minimumAmountOfSponsorships.get(2)}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.max-sponsorship"/>
			</th>
			<td>
				<acme:print value="${maximumAmountOfSponsorships.get(0)}"/>
			</td>
			<td>
				<acme:print value="${maximumAmountOfSponsorships.get(1)}"/>
			</td>
			<td>
				<acme:print value="${maximumAmountOfSponsorships.get(2)}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.dev-sponsorship"/>
			</th>
			<td>
				<acme:print value="${deviationAmountOfSponsorships.get(0)}"/>
			</td>
			<td>
				<acme:print value="${deviationAmountOfSponsorships.get(1)}"/>
			</td>
			<td>
				<acme:print value="${deviationAmountOfSponsorships.get(2)}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.link-sponsorship"/>
			</th>
			<td></td>
			<td></td>
			<td></td>
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
		<tr>
			<th id="metrics"><acme:message code="sponsor.dashboard.form.label.metrics"/></th>
			<th id="EUR"> <acme:print value="${acceptedCurrencies.get(0)}"/> </th>
			<th id="GBP"> <acme:print value="${acceptedCurrencies.get(1)}"/> </th>
			<th id="USD"> <acme:print value="${acceptedCurrencies.get(2)}"/> </th>
			<th id="total"><acme:message code="sponsor.dashboard.form.label.total"/></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.average-invoice"/>
			</th>
			<td>
				<acme:print value="${averageQuantityOfInvoices.get(0)}"/>
			</td>
			<td>
				<acme:print value="${averageQuantityOfInvoices.get(1)}"/>
			</td>
			<td>
				<acme:print value="${averageQuantityOfInvoices.get(2)}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.max-invoice"/>
			</th>
			<td>
				<acme:print value="${maximumQuantityOfInvoices.get(0)}"/>
			</td>
			<td>
				<acme:print value="${maximumQuantityOfInvoices.get(1)}"/>
			</td>
			<td>
				<acme:print value="${maximumQuantityOfInvoices.get(2)}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.min-invoice"/>
			</th>
			<td>
				<acme:print value="${minimumQuantityOfInvoices.get(0)}"/>
			</td>
			<td>
				<acme:print value="${minimumQuantityOfInvoices.get(1)}"/>
			</td>
			<td>
				<acme:print value="${minimumQuantityOfInvoices.get(2)}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.dev-invoice"/>
			</th>
			<td>
				<acme:print value="${deviationQuantityOfInvoices.get(0)}"/>
			</td>
			<td>
				<acme:print value="${deviationQuantityOfInvoices.get(1)}"/>
			</td>
			<td>
				<acme:print value="${deviationQuantityOfInvoices.get(2)}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="sponsor.dashboard.form.label.tax-invoice"/>
			</th>
			<td></td>
			<td></td>
			<td></td>
			<td>
				<acme:print value="${numberOfInvoicesWithATaskLessOrEqualThan21}"/>
			</td>
		</tr>
	</tbody>
	</table>
<acme:return/>