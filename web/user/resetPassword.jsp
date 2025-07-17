<%-- 
    Document   : resetPassword
    Created on : Jul 17, 2025, 2:17:41 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đặt lại mật khẩu | BookZone</title>
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

            .container {
                background-color: #fff;
                padding: 30px 40px;
                border-radius: 16px;
                box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
                width: 360px;
                animation: fadeIn 0.5s ease-in-out;
            }

            h2 {
                text-align: center;
                margin-bottom: 24px;
                color: #3b5998;
            }

            label {
                display: block;
                margin-top: 12px;
                font-weight: 500;
            }

            input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-top: 6px;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 14px;
                box-sizing: border-box;
            }

            button {
                width: 100%;
                padding: 12px;
                margin-top: 20px;
                background-color: #3b5998;
                color: white;
                border: none;
                border-radius: 8px;
                font-size: 16px;
                cursor: pointer;
            }

            button:hover {
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

            .error-message:empty {
                display: none;
            }

        </style>
    </head>
    <body>
        <div class="container">
            <h2>Đặt lại mật khẩu</h2>
            <form action="${pageContext.request.contextPath}/reset-password" method="post">
                <label>Mật khẩu mới:</label>
                <input type="password" name="newPassword" required />

                <label>Nhập lại mật khẩu:</label>
                <input type="password" name="confirmPassword" required />

                <button type="submit">Đặt lại</button>
            </form>

            <c:if test="${not empty errorMessage and fn:length(fn:trim(errorMessage)) > 0}">
                <div class="error-message">${errorMessage}</div>
            </c:if>


        </div>
    </body>
</html>

