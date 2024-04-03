
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.trainingModule.list.label.code" path="code"  width="40%"/>
	<acme:list-column code="developer.trainingModule.list.label.difficultyLevel" path="difficultyLevel" width="40%" />
	<acme:list-column code="developer.trainingModule.list.label.link" path="link" width="40%" />
</acme:list>