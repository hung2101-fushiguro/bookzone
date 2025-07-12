<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Gemini AI Chat</title>
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f0f8ff; /* Màu nền nhẹ nhàng như tiệm sách */
                color: #333;
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            h2 {
                text-align: center;
                margin-top: 20px;
                color: #2196F3; /* Màu xanh dương cho tiêu đề */
                font-size: 2em;
                font-weight: 600;
            }

            #chatContainer {
                display: flex;
                flex-direction: column;
                align-items: center;
                margin-top: 20px;
            }

            #chatBox {
                width: 70%;
                height: 400px;
                border: 2px solid #2196F3;
                border-radius: 10px;
                padding: 15px;
                background-color: #ffffff;
                overflow-y: auto;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            }

            .message {
                padding: 10px;
                margin: 10px;
                border-radius: 10px;
                max-width: 80%;
            }

            .user {
                background-color: #e3f2fd; /* Màu xanh dương nhạt cho người dùng */
                text-align: right;
                max-width: 100%;
                margin-left: 300px;
            }

            .bot {
                background-color: #bbdefb;
                text-align: left;
                
            }

            #msgInput {
                width: 70%;
                padding: 12px;
                margin: 10px;
                border: 2px solid #2196F3;
                border-radius: 10px;
                font-size: 16px;
                color: #333;
            }

            button {
                padding: 12px 20px;
                background-color: #2196F3;
                color: white;
                font-size: 16px;
                border: none;
                border-radius: 10px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                margin-top: 10px;
            }

            button:hover {
                background-color: #1976D2;
            }

            .suggestions {
                margin-top: 20px;
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
            }

            .suggestion-button {
                margin: 5px;
                padding: 12px 20px;
                background-color: #90caf9; /* Màu xanh dương nhẹ cho các nút gợi ý */
                border: none;
                border-radius: 20px;
                cursor: pointer;
                font-size: 14px;
                transition: background-color 0.3s ease, color 0.3s ease;
            }

            .suggestion-button:hover {
                background-color: #1976D2;
                color: white;
            }

            .suggestion-button:focus {
                outline: none;
            }
        </style>
    </head>
    <body>
        <h2>Gemini AI Chat</h2>
        <div id="chatContainer">
            <div id="chatBox"></div>
            <input type="text" id="msgInput" placeholder="Type your message..." onkeydown="if (event.key === 'Enter') {
                    sendMessage()
                }">
            <button onclick="sendMessage()">Send</button>
            <div class="suggestions">
                <button class="suggestion-button" onclick="suggestMessage('Sách tiểu thuyết')">Tiểu thuyết</button>
                <button class="suggestion-button" onclick="suggestMessage('Sách khoa học')">Khoa học</button>
                <button class="suggestion-button" onclick="suggestMessage('Sách lịch sử')">Lịch sử</button>
                <button class="suggestion-button" onclick="suggestMessage('Sách tâm lý')">Tâm lý</button>
                <button class="suggestion-button" onclick="suggestMessage('Sách thiếu nhi')">Thiếu nhi</button>
                <button class="suggestion-button" onclick="suggestMessage('Sách trinh thám')">Trinh thám</button>
                <button class="suggestion-button" onclick="suggestMessage('Sách kinh doanh')">Kinh doanh</button>
                <button class="suggestion-button" onclick="suggestMessage('Sách kĩ năng sống')">Kĩ năng sống</button>
                <button class="suggestion-button" onclick="suggestMessage('Sách văn học nước ngoài')">Văn học nước ngoài</button>
                <button class="suggestion-button" onclick="suggestMessage('Sách manga')">Manga</button>
            </div>
        </div>

        <script>
            // Hàm hiển thị lời chào tự động khi trang được tải
            document.addEventListener('DOMContentLoaded', function () {
                const chatBox = document.getElementById("chatBox");
                chatBox.innerHTML += "<div class='bot'>Gemini: Chào mừng bạn đến với BookZone, thiên đường sách dành cho những tâm hồn yêu sách! 📚✨<br>Hãy cho tôi biết bạn đang tìm gì hôm nay. Tại đây, bạn có thể khám phá những cuốn sách tuyệt vời từ tiểu thuyết lãng mạn, khoa học viễn tưởng, đến những tác phẩm triết học sâu sắc. Nếu bạn cần một người bạn đồng hành để tìm kiếm sách theo thể loại, tác giả yêu thích, hay đơn giản là tìm giá cả hợp lý, đừng ngần ngại chia sẻ với chúng tôi! Bạn có thể hỏi tôi về thể loại sách, tác giả, hoặc giá sách. Ví dụ: 'Sách tiểu thuyết', 'Sách ngôn tình', hoặc 'Sách dưới 100k'.</div>";
                chatBox.scrollTop = chatBox.scrollHeight;
            });

            // Hàm gửi tin nhắn và nhận phản hồi từ bot
            async function sendMessage() {
                const input = document.getElementById("msgInput");
                const message = input.value.trim();
                if (!message)
                    return;

                const chatBox = document.getElementById("chatBox");
                chatBox.innerHTML += "<div class='message user'>You: " + message + "</div>";

                const response = await fetch("chat", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: "msg=" + encodeURIComponent(message)
                });

                const reply = await response.text();
                chatBox.innerHTML += "<div class='message bot'>Gemini: " + reply + "</div>";
                chatBox.scrollTop = chatBox.scrollHeight;
                input.value = "";
            }

            // Hàm gợi ý tìm kiếm khi người dùng nhấn các nút gợi ý
            function suggestMessage(suggestion) {
                const input = document.getElementById("msgInput");
                input.value = suggestion;
                sendMessage();
            }
        </script>
    </body>
</html>
