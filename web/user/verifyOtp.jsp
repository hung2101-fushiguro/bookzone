<%-- 
    Document   : verifyOtp
    Created on : Jul 17, 2025, 2:14:13 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Xác minh OTP | BookZone</title>
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

            input[type="text"] {
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
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Xác minh OTP</h2>
            <form action="${pageContext.request.contextPath}/verify-otp" method="post">
                <label>Nhập mã OTP đã gửi:</label>
                <input type="text" name="otp" required />
                <button type="submit">Xác minh</button>
            </form>

            <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
            </c:if>
        </div>
    </body>
</html>

