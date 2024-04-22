<%@ page language="java" %>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:list>
    <acme:list-column code="authenticated.codeAudit.list.label.code" path="code"  width="40%" />
    <acme:list-column code="authenticated.codeAudit.list.label.type" path="type" width="20%" />
    <acme:list-column code="authenticated.codeAudit.list.label.mark" path="mark" width="20%" />
    <acme:list-column code="authenticated.codeAudit.form.label.project" path="project" width="40%" />
</acme:list>