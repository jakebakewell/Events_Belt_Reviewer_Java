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
<title>Update Event</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
</head>
<body class="bg-info p-5">
	<h1 class="text-center mb-3"><c:out value="${updateEvent.name}"/></h1>
	<div class="container text-center mt-5 bg-light p-3 rounded">
		<p class="text-danger"><form:errors path="updateEvent.*"/></p>
		<form:form method="POST" action="/events/${updateEvent.id}" modelAttribute="updateEvent" class="form-group">
		<input type="hidden" name="_method" value="put">
			<p>
			    <form:label path="name">Event Name:</form:label>
			    <form:input type="text" path="name" cssClass="form-control"/>
			</p>
			<p>
			    <form:label path="date">Event Date:</form:label>
			    <form:input type="date" path="date" cssClass="form-control"/>
			</p>
			<p>
			    <form:label path="location">Event Location:</form:label>
			    <form:input type="text" path="location" cssClass="form-control"/>
			</p>
			<p>
			<p>
				<form:label path="state">State:</form:label>
			    <form:select cssClass="form-select" path="state">
			<form:option value="">--Select--</form:option>
			<form:option value="AK">Alaska</form:option>
			<form:option value="AL">Alabama</form:option>
			<form:option value="AR">Arkansas</form:option>
			<form:option value="AZ">Arizona</form:option>
			<form:option value="CA">California</form:option>
			<form:option value="CO">Colorado</form:option>
			<form:option value="CT">Connecticut</form:option>
			<form:option value="DC">District of Columbia</form:option>
			<form:option value="DE">Delaware</form:option>
			<form:option value="FL">Florida</form:option>
			<form:option value="GA">Georgia</form:option>
			<form:option value="HI">Hawaii</form:option>
			<form:option value="IA">Iowa</form:option>
			<form:option value="ID">Idaho</form:option>
			<form:option value="IL">Illinois</form:option>
			<form:option value="IN">Indiana</form:option>
			<form:option value="KS">Kansas</form:option>
			<form:option value="KY">Kentucky</form:option>
			<form:option value="LA">Louisiana</form:option>
			<form:option value="MA">Massachusetts</form:option>
			<form:option value="MD">Maryland</form:option>
			<form:option value="ME">Maine</form:option>
			<form:option value="MI">Michigan</form:option>
			<form:option value="MN">Minnesota</form:option>
			<form:option value="MO">Missouri</form:option>
			<form:option value="MS">Mississippi</form:option>
			<form:option value="MT">Montana</form:option>
			<form:option value="NC">North Carolina</form:option>
			<form:option value="ND">North Dakota</form:option>
			<form:option value="NE">Nebraska</form:option>
			<form:option value="NH">New Hampshire</form:option>
			<form:option value="NJ">New Jersey</form:option>
			<form:option value="NM">New Mexico</form:option>
			<form:option value="NV">Nevada</form:option>
			<form:option value="NY">New York</form:option>
			<form:option value="OH">Ohio</form:option>
			<form:option value="OK">Oklahoma</form:option>
			<form:option value="OR">Oregon</form:option>
			<form:option value="PA">Pennsylvania</form:option>
			<form:option value="PR">Puerto Rico</form:option>
			<form:option value="RI">Rhode Island</form:option>
			<form:option value="SC">South Carolina</form:option>
			<form:option value="SD">South Dakota</form:option>
			<form:option value="TN">Tennessee</form:option>
			<form:option value="TX">Texas</form:option>
			<form:option value="UT">Utah</form:option>
			<form:option value="VA">Virginia</form:option>
			<form:option value="VT">Vermont</form:option>
			<form:option value="WA">Washington</form:option>
			<form:option value="WI">Wisconsin</form:option>
			<form:option value="WV">West Virginia</form:option>
			<form:option value="WY">Wyoming</form:option>
			</form:select>
			</p>
			<form:hidden value="${user.id}" path="creator"/>
			<input type="submit" value="Update Event" class="btn btn-primary mt-4"/>
		</form:form>
	</div>
</body>
</html>