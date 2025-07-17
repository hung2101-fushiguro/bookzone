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
                max-width: 700px;
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

            .clearfix::after {
                content: "";
                display: table;
                clear: both;
            }

            #chat-messages {
                max-height: 400px;
                overflow-y: auto;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div class="chat-box">
            <h2>💬 Tư vấn sách BookZone</h2>

            <div id="chat-messages">
                <c:if test="${not empty sessionScope.chatHistory}">
                    <c:forEach var="msg" items="${sessionScope.chatHistory}">
                        <div class="message">
                            <div class="user">👤 Bạn:</div>
                            <div>${msg.user}</div>
                            <div class="user">🤖 Trợ lý AI:</div>
                            <div class="bot">${msg.bot}</div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>

            <form action="${pageContext.request.contextPath}/chat" method="post">
                <textarea name="message" placeholder="Nhập câu hỏi của bạn..."></textarea>
                <button type="submit">Gửi</button>
            </form>

            <form action="chat" method="get">
                <button type="submit" style="background-color: #dc3545;">🔄 Xóa lịch sử</button>
            </form>

        </div>
    </body>
</html>
