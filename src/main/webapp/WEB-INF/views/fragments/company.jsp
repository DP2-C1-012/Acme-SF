<%--
- company.jsp
-
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
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h1><acme:message code="master.company.title"/></h1>

<p><acme:message code="master.company.text"/></p>

<address>
  <strong><acme:message code="master.company.name"/></strong> <br/>
  <span class="fas fa-map-marker"> &nbsp; </span><acme:message code="master.company.address"/> <br/>
  <span class="fa fa-phone"></span> &nbsp; <acme:message code="master.company.phone"/><br/>
  <span class="fa fa-at"></span> &nbsp; <a href="mailto:<acme:message code="master.company.email"/>"><acme:message code="master.company.email"/></a> <br/>
</address>

