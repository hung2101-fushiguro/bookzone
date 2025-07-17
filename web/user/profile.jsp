<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    boolean editMode = "true".equals(request.getParameter("edit"));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông tin cá nhân</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f5faff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .box {
            background: white;
            padding: 40px 30px;
            border-radius: 20px;
            box-shadow: 0 12px 25px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
        }

        h2 {
            text-align: center;
            color: #007cf0;
        }

        label {
            font-weight: bold;
            margin-top: 15px;
            display: block;
            color: #444;
        }

        p, input {
            margin-bottom: 15px;
        }

        input {
            width: 100%;
            padding: 10px;
            border-radius: 10px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        input:focus {
            border-color: #00c9a7;
            outline: none;
        }

        .btn {
            display: inline-block;
            padding: 10px 20px;
            background: linear-gradient(to right, #007cf0, #00dfd8);
            color: white;
            text-align: center;
            border: none;
            border-radius: 30px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 20px;
            text-decoration: none;
        }

        .btn:hover {
            background: linear-gradient(to right, #0053c7, #00a2a1);
        }

        .btn-back {
            margin-left: 10px;
            background: #ccc;
            color: #333;
        }
    </style>
</head>
<body>

<div class="box">
    <h2>Thông tin cá nhân</h2>

    <% if (editMode) { %>
        <form action="<%= request.getContextPath() %>/user?action=updateProfile" method="post">
            <label>Email:</label>
            <p><strong><%= user.getEmail() %></strong></p>

            <label>Tên đăng nhập:</label>
            <input type="text" name="username" value="<%= user.getUsername() %>" required>

            <label>Tên người dùng:</label>
            <input type="text" name="name" value="<%= user.getName() %>" required>

            <label>Địa chỉ:</label>
            <input type="text" name="address" value="<%= user.getAddress() == null ? "" : user.getAddress() %>" required>

            <label>Số điện thoại:</label>
            <input type="text" name="phone" value="<%= user.getPhone() == null ? "" : user.getPhone() %>" required>

            <label>Mật khẩu mới:</label>
            <input type="password" name="password" placeholder="Để trống nếu không đổi mật khẩu">

            <button type="submit" class="btn">Lưu thay đổi</button>
            <a class="btn btn-back" href="profile.jsp">Hủy</a>
        </form>
    <% } else { %>

        <label>Email:</label>
        <p><%= user.getEmail() %></p>

        <label>Tên đăng nhập:</label>
        <p><%= user.getUsername() %></p>

        <label>Tên người dùng:</label>
        <p><%= user.getName() %></p>

        <label>Địa chỉ:</label>
        <p><%= user.getAddress() %></p>

        <label>Số điện thoại:</label>
        <p><%= user.getPhone() %></p>

        <div style="display: flex; justify-content: center; gap: 20px; margin-top: 20px; width: 100%;">
  <a href="<%= request.getContextPath() %>/user/profile.jsp?edit=true" 
     class="btn" 
     style="background: linear-gradient(to right, #007cf0, #00dfd8); 
            color: white; 
            padding: 10px 25px; 
            border-radius: 30px; 
            font-weight: 600; 
            display: inline-block; 
            text-align: center;
            transition: background 0.3s ease;">
     Sửa hồ sơ
  </a>
  <a href="<%= request.getContextPath() %>/home" 
     class="btn" 
     style="background: linear-gradient(to right, #007cf0, #00dfd8); 
            color: white; 
            padding: 10px 25px; 
            border-radius: 30px; 
            font-weight: 600; 
            display: inline-block; 
            text-align: center;
            transition: background 0.3s ease;">
    Quay lại trang chủ
  </a>
</div>
    <% } %>
</div>

</body>
</html>
