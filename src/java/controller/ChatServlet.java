package controller;

import chat.AIBookAgent;
import chat.GeminiClient;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy nội dung người dùng gửi lên
        String userMessage = request.getParameter("message");

        // Tạo prompt từ dữ liệu sách và nội dung người dùng
        String prompt = AIBookAgent.generatePrompt(userMessage);

        // Gửi prompt tới Gemini và nhận phản hồi
        String aiResponse = GeminiClient.chatWith(prompt);

        // Gửi phản hồi tới trang JSP
        request.setAttribute("response", aiResponse);
        request.setAttribute("message", userMessage); // Nếu muốn hiển thị lại message người dùng

        // Điều hướng về giao diện chat
        request.getRequestDispatcher("chat/chat.jsp").forward(request, response);
    }

    // (Không cần xử lý GET – hoặc có thể redirect về home nếu cần)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp"); // hoặc chat.jsp nếu bạn muốn
    }
}
