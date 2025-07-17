<%-- 
    Document   : listUser
    Created on : Jul 9, 2025, 5:26:36 PM
    Author     : Administrator
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách người dùng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<div class="d-flex justify-content-between mb-3">
    <h2 class="mb-0">Danh sách người dùng</h2>
    <a href="admin/admin.jsp" class="btn btn-secondary">⬅ Quay lại Trang chủ</a>
</div>

<table class="table table-bordered table-hover align-middle">
    <thead class="table-light text-center">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Email</th>
        <th>Mật khẩu (Hashed)</th>
        <th>Quyền</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>

    <%
        List<User> users = (List<User>) request.getAttribute("users");
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
    %>
    <tr>
        <td class="text-center"><%= user.getId() %></td>
        <td><%= user.getName() %></td>
        <td><%= user.getEmail() %></td>
        <td class="text-break" style="max-width: 250px;">****</td>
        <td class="text-center"><%= user.getRole() %></td>
        <td class="text-center">
            <a href="user?action=delete&id=<%= user.getId() %>"
               class="btn btn-sm btn-danger"
               onclick="return confirm('Bạn có chắc muốn xóa người dùng này không?');">
                Xóa
            </a>
        </td>
    </tr>
    <%
            }
        } else {
    %>
    <tr>
        <td colspan="6" class="text-center text-muted">Không có người dùng nào trong hệ thống.</td>
    </tr>
    <%
        }
    %>

    </tbody>
</table>

</body>
</html>
