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
                background: linear-gradient(to right, #89f7fe, #66a6ff);
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            .register-container {
                background-color: #fff;
                padding: 30px 40px;
                border-radius: 16px;
                box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
                width: 400px;
                animation: slideDown 0.5s ease-in-out;
            }

            @keyframes slideDown {
                from {
                    opacity: 0;
                    transform: translateY(-20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            h2 {
                text-align: center;
                margin-bottom: 24px;
                color: #3366cc;
            }

            label {
                display: block;
                margin-top: 12px;
                font-weight: 500;
            }

            input[type="text"], input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-top: 6px;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 14px;
                box-sizing: border-box;
                transition: border-color 0.3s;
            }

            input:focus {
                border-color: #3366cc;
                outline: none;
            }

            input[type="submit"] {
                width: 100%;
                padding: 12px;
                margin-top: 20px;
                background-color: #3366cc;
                color: white;
                border: none;
                border-radius: 8px;
                font-size: 16px;
                cursor: pointer;
            }

            input[type="submit"]:hover {
                background-color: #254f9d;
            }

            .error-message {
                margin-top: 16px;
                background-color: #ffdddd;
                color: #c00;
                padding: 10px;
                border-radius: 8px;
                text-align: center;
            }

            .login-link {
                margin-top: 14px;
                text-align: center;
                font-size: 14px;
            }

            .login-link a {
                color: #3366cc;
                text-decoration: none;
                font-weight: bold;
            }

            .login-link a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="register-container">
            <h2>Đăng ký tài khoản</h2>
            <form action="register" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username"
                       value="${param.username}" required />

                <label for="email">Email:</label>
                <input type="text" id="email" name="email"
                       value="${param.email}" required />

                <label for="name">Tên hiển thị:</label>
                <input type="text" id="name" name="name"
                       value="${param.name}" required />

                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required />

                <input type="submit" value="Đăng ký" />
            </form>
            <div style="text-align:center; margin: 20px 0;">
                <a href="https://accounts.google.com/o/oauth2/auth?client_id=19340180186-sm87uhblq9epkqme3jhgvt4fef4sb45k.apps.googleusercontent.com&redirect_uri=http://localhost:9999/bookzone3/oauth2callback&response_type=code&scope=openid%20email%20profile&&prompt=select_account">
                    <img src="https://developers.google.com/identity/images/btn_google_signin_light_normal_web.png"
                         alt="Google Sign-In" style="height:40px;">
                </a>
            </div>
            <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
            </c:if>

            <div class="login-link">
                Đã có tài khoản?
                <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            </div>
        </div>
    </body>
</html>
