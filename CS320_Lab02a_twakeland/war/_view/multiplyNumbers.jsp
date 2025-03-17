<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Multiply Numbers</title>
		<style type="text/css">
		.error {
			color: red;
		}
		
		td.label {
			text-align: right;
		}
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
			<form action="${pageContext.servletContext.contextPath}/multiplyNumbers" method="post">
			<table>
				<tr>
					<td class="label">First number:</td>
					<td><input type="text" name="first" size="12" value="${first}" /></td>
				</tr>
				<tr>
					<td class="label">Second number:</td>
					<td><input type="text" name="second" size="12" value="${second}" /></td>
				</tr>
				<tr>
					<td class="label">Result:</td>
					<td>${result}</td>
				</tr>
			</table>
			<input type="Submit" name="submit" value="Multiply Numbers!">
		</form>
		</c:if>
		
		<c:if test="${empty errorMessage}">
			<form action="${pageContext.servletContext.contextPath}/multiplyNumbers" method="post">
			<table>
				<tr>
					<td class="label">First number:</td>
					<td><input type="text" name="first" size="12" value="${number.first}" /></td>
				</tr>
				<tr>
					<td class="label">Second number:</td>
					<td><input type="text" name="second" size="12" value="${number.second}" /></td>
				</tr>
				<tr>
					<td class="label">Result:</td>
					<td>${number.result}</td>
				</tr>
			</table>
			<input type="Submit" name="submit" value="Multiply Numbers!">
		</form>
		</c:if>
		
		<form action="${pageContext.servletContext.contextPath}/index" method="get">
			<input type="Submit" name="submit" value="Back To Index">
		</form>
		
	</body>
</html>