<%-- 
    Document   : profile
    Created on : Jul 9, 2025, 5:22:30?PM
    Author     : Administrator
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thông tin cá nhân</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-6 bg-white p-4 rounded shadow-sm">

            <h3 class="mb-4 text-center">Thông tin cá nhân</h3>

            <%
                User user = (User) request.getAttribute("user");
            %>

            <form action="user" method="post">
                <input type="hidden" name="action" value="updateProfile">

                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <p class="form-control-plaintext fw-bold"><%= user.getEmail() %></p>
                </div>

                <div class="mb-3">
                    <label class="form-label">Tên người dùng</label>
                    <input type="text" class="form-control" name="name" value="<%= user.getName() %>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mật khẩu mới</label>
                    <input type="password" class="form-control" name="password" placeholder="Nhập password mới (bỏ trống nếu không đổi)">
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                </div>
            </form>

            <div class="mt-4 text-center">
                <a href="home" class="btn btn-link">⬅ Quay lại Trang chủ</a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

