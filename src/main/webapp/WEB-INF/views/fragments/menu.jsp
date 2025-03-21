<%--
- menu.jsp
-
-
- This project is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike License 4.0 
- International (CC BY-NC-SA 4.0), allowing its use, adaptation and distribution for academic purposes. 
- However, it is important to emphasize that this repository is not intended for personal use and is exclusively 
- academic. In addition, it is strictly forbidden for university colleagues to use this project to
- copy or plagiarize.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.fav-link" action="http://www.example.com/"/>
			<%-- <acme:menu-suboption code="master.menu.anonymous.nicolas-link" action="https://www.leagueoflegends.com/es-es/"/>
			<acme:menu-suboption code="master.menu.anonymous.alvaro-link" action="https://www.us.es/estudiar/que-estudiar/oferta-de-grados/grado-en-ingenieria-informatica-ingenieria-del-software/2050049"/>
			<acme:menu-suboption code="master.menu.anonymous.ronald-link" action="https://www.m4sevilla.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.jaime-link" action="https://ev.us.es/"/>
			<acme:menu-suboption code="master.menu.anonymous.juanjo-link" action="https://yourmom.zip"/> --%>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.any">
			<acme:menu-suboption code="master.menu.any.sponsorship" action="/any/sponsorship/list"/>
			<acme:menu-suboption code="master.menu.any.claim" action="/any/claim/list"/>
			<acme:menu-suboption code="master.menu.any.training-module" action="/any/training-module/list"/>
			<acme:menu-suboption code= "master.menu.any.list-contracts" action="/any/contract/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.banner" action="/administrator/banner/list"/>
			<acme:menu-suboption code="master.menu.administrator.system-configuration" action="/administrator/system-configuration/show"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/system/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/system/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/system/shut-down"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.manager" access="hasRole('Manager')">
			<acme:menu-suboption code="master.menu.manager.project" action="/manager/project/list"/>
			<acme:menu-suboption code="master.menu.manager.user-story" action="/manager/user-story/list-all"/>
			<acme:menu-suboption code="master.menu.manager.dashboard" action="/manager/manager-dashboard/show"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
					</acme:menu-option>


		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.codeAudits" action="/authenticated/code-audit/list"/>
			<acme:menu-suboption code="master.menu.authenticated.risks" action="/authenticated/risk/list"/>
			<acme:menu-suboption code="master.menu.authenticated.objectives" action="/authenticated/objective/list"/>

		</acme:menu-option>
		<acme:menu-option code="master.menu.developer" access="hasRole('Developer')">
			<acme:menu-suboption code="master.menu.developer.training-module" action="/developer/training-module/list"/>
			<acme:menu-suboption code="master.menu.developer.training-session" action="/developer/training-session/list-all"/>
			<acme:menu-suboption code="master.menu.developer.dashboard" action="/developer/developer-dashboard/show"/>
		</acme:menu-option>
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.codeAudit" action="/auditor/code-audit/list"/>
			<acme:menu-suboption code="master.menu.auditor.auditRecord" action="/auditor/audit-record/create"/>
			<acme:menu-suboption code="master.menu.auditor.dashboard" action="/auditor/auditor-dashboard/show"/>
		</acme:menu-option>
		<acme:menu-option code="master.menu.client" access="hasRole('Client')">
			<acme:menu-suboption code="master.menu.client.list-contract" action="/client/contract/list-mine"/>
			<acme:menu-suboption code="master.menu.client.dashboard" action="/client/client-dashboard/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.sponsor" access="hasRole('Sponsor')">
			<acme:menu-suboption code="master.menu.sponsor.sponsorship" action="/sponsor/sponsorship/list"/>
			<acme:menu-suboption code="master.menu.sponsor.dashboard" action="/sponsor/sponsor-dashboard/show"/>
		</acme:menu-option>
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/anonymous/system/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-developer" action="/authenticated/developer/create" access="!hasRole('Developer')"/>
			<acme:menu-suboption code="master.menu.user-account.developer" action="/authenticated/developer/update" access="hasRole('Developer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-sponsor" action="/authenticated/sponsor/create" access="!hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.sponsor" action="/authenticated/sponsor/update" access="hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-client" action="/authenticated/client/create" access="!hasRole('Client')"/>
			<acme:menu-suboption code="master.menu.user-account.client" action="/authenticated/client/update" access="hasRole('Client')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/authenticated/system/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

