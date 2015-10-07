<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>University Enrollments</title>

<style>
tr:first-child {
	font-weight: bold;
	background-color: #C6C9C4;
}
</style>

</head>


<body>
	<h2>List of Users</h2>
	<table>
		<tr>
			<td>First Name</td>
			<td>Last Name</td>
			<td>Joining Date</td>
			<td>Username</td>
			<td></td>
		</tr>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td>${user.joiningDate}</td>
				<td><a href="<c:url value='/user/edit-${user.username}-user' />">${user.username}</a></td>
				<td><a href="<c:url value='/user/delete-${user.username}-user' />">delete</a></td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<a href="<c:url value='/user/new' />">Add New User</a>
</body>
</html>