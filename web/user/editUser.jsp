<%-- 
    Document   : editUser
    Created on : Jul 9, 2025, 5:27:19 PM
    Author     : Administrator
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<%
    User user = (User) request.getAttribute("user");
    boolean isEdit = (user != null);
%>
<h1><%= isEdit ? "Edit User" : "Add New User" %></h1>

<form action="user" method="post">
    <% if (isEdit) { %>
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%= user.getId() %>">
    <% } else { %>
        <input type="hidden" name="action" value="add">
    <% } %>

    <p>Name: <input type="text" name="name" value="<%= isEdit ? user.getName() : "" %>"></p>
    <p>Email: <input type="email" name="email" value="<%= isEdit ? user.getEmail() : "" %>"></p>
    <p>Password: <input type="password" name="password" value=""></p>
    <p>Role: <input type="text" name="role" value="<%= isEdit ? user.getRole() : "" %>"></p>

    <button type="submit">Save</button>
</form>

<p><a href="user">⬅ Back to List</a></p>
</body>
</html>

