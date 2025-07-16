<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Trợ lý AI - BookZone</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f7f7f7;
                padding: 30px;
            }

            .chat-box {
                max-width: 600px;
                margin: auto;
                background: white;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                padding: 20px;
            }

            .message {
                margin-bottom: 15px;
            }

            .user {
                font-weight: bold;
                color: #007bff;
            }

            .bot {
                color: #333;
                margin-top: 5px;
            }

            textarea {
                width: 100%;
                height: 70px;
                padding: 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
                resize: none;
            }

            button {
                margin-top: 10px;
                background-color: #007bff;
                color: white;
                padding: 10px 16px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                float: right;
            }

            button:hover {
                background-color: #0056b3;
            }

        </style>
    </head>
    <body>
        <div class="chat-box">
            <h2>💬 Tư vấn sách BookZone</h2>

            <form action="chat" method="post">
                <textarea name="message" placeholder="Nhập câu hỏi của bạn..."></textarea>
                <button type="submit">Gửi</button>
            </form>

            <c:if test="${not empty message}">
                <div class="message">
                    <div class="user">👤 Bạn:</div>
                    <div>${message}</div>
                </div>
            </c:if>

            <c:if test="${not empty response}">
                <div class="message">
                    <div class="user">🤖 Trợ lý AI:</div>
                    <div class="bot">${response}</div>
                </div>
            </c:if>
        </div>
    </body>
</html>
