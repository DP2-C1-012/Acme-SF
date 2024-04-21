<%@ page language="java" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:list>
    <acme:list-column code="contract.list.label.moment" path="moment" width="20%"/>
    <acme:list-column code="contract.list.label.provider" path="provider" width="20%"/>
    <acme:list-column code="contract.list.label.customer" path="customer" width="20%"/>
    <acme:list-column code="contract.list.label.goals" path="goals" width="20%"/>
    <acme:list-column code="contract.list.label.budget" path="budget" width="20%"/>
</acme:list>