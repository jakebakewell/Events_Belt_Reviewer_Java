<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Event</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
</head>
<body class="bg-info p-5">
	<div class="container">
		<div class="container text-center mb-5 bg-light p-3 rounded">
			<h1 class="mb-5"><c:out value="${event.name}" /></h1>
			<h5 class="mb-1">Host: <c:out value="${event.creator.firstName}" /> <c:out value="${event.creator.lastName}" /></h5>
			<h5 class="mb-1">Date: <fmt:formatDate pattern = "MMMMMMMMM dd, yyyy" value = "${event.date}" /></h5>
			<h5 class="mb-1">Location: <c:out value="${event.location}" />, <c:out value="${event.state}" /></h5>
			<h5 class="mb-5">People who are participating in this event: <c:out value="${count}" /></h5>
			<table class="table">
				<thead class="thead-dark">
					<tr>
						<th>Name</th>
						<th>Location</th>
					</tr>
				</thead>
					<c:forEach items="${event.participants}" var="participant">
					<tr>
						<td>${participant.firstName} ${participant.lastName}</td>
						<td>${participant.location}, ${participant.state}</td>
					</tr>
					</c:forEach>
				<tbody>
				</tbody>
			</table>
		</div>
		<div class="container text-center mb-5 bg-light p-3 rounded">
			<h2>Message Wall</h2>
			<div class="overflow-auto h-50">
				<c:forEach items="${messages}" var="message">
					<c:if test="${message.event == event}">
						<p><c:out value="${message.text}"/></p>
						<p><c:out value="${message.user.firstName}"/> <c:out value="${message.user.lastName}"/></p>
						<p>*----------------*----------------*----------------*</p>
					</c:if>
				</c:forEach>
			</div>
			<p class="text-danger"><form:errors path="newMessage.*"/></p>
			<h6>Add Message:</h6>
			<form method="POST" action="/messages/create/${event.id}" class="form-group">
				<p>
				    <label for="text">Message Text:</label>
				    <input type="text" name="text" class="form-control"/>
				</p>
				<p>
				<input type="hidden" value="${user.id}" name="user"/>
				<input type="submit" value="Create Message" class="btn btn-primary mt-4"/>
			</form>
		</div>
	</div>
</body>
</html>