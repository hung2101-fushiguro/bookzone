<%-- 
    Document   : registerUser
    Created on : Jun 24, 2025, 1:13:48 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register | BookZone</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #a1c4fd, #c2e9fb);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-container {
            background-color: #fff;
            padding: 30px 40px;
            border-radius: 16px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
            width: 360px;
            animation: fadeIn 0.5s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .login-container h2 {
            text-align: center;
            margin-bottom: 24px;
            color: #3b5998;
        }

        label {
            display: block;
            margin-top: 12px;
            font-weight: 500;
        }

        input[type="text"], input[type="password"], input[type="email"] {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus,
        input[type="password"]:focus,
        input[type="email"]:focus {
            border-color: #3b5998;
            outline: none;
        }

        input[type="submit"] {
            width: 100%;
            padding: 12px;
            margin-top: 20px;
            background-color: #3b5998;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #2d4373;
        }

        .error-message {
            margin-top: 16px;
            background-color: #ffcccc;
            color: #b20000;
            padding: 10px;
            border-radius: 8px;
            text-align: center;
        }

        .signup-link {
            margin-top: 14px;
            text-align: center;
            font-size: 14px;
            color: #333;
        }

        .signup-link a {
            color: #3b5998;
            text-decoration: none;
            font-weight: bold;
            margin-left: 4px;
        }

        .signup-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>BookZone Register</h2>
        <form action="user" method="post">
            <input type="hidden" name="action" value="create" />

            <label for="username">Tên đăng nhập:</label>
            <input type="text" id="username" name="username" required />

            <label for="name">Họ và tên:</label>
            <input type="text" id="name" name="name" required />

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required />

            <label for="password">Mật khẩu:</label>
            <input type="password" id="password" name="password" required />

            <input type="submit" value="Đăng ký" />
        </form>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>

        <div class="signup-link">
            Đã có tài khoản?
            <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
        </div>
    </div>
</body>
</html>

