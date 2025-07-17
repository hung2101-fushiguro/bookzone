package controller;

import chat.AIBookAgent;
import chat.GeminiClient;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.ChatMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.User;

@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tin nhắn từ người dùng
        String userMessage = request.getParameter("message");

        // Lấy lịch sử từ session (nếu có)
        HttpSession session = request.getSession();
        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("chatHistory");
        if (history == null) {
            history = new ArrayList<>();
        }
        
        User user = (User) session.getAttribute("user");

        // Tạo prompt có chứa hội thoại trước đó + tin nhắn hiện tại
        String prompt = AIBookAgent.generatePrompt(userMessage, history, user);

        // Gọi Gemini API để lấy phản hồi
        String aiResponse = GeminiClient.chatWith(prompt);

        // Lưu tin nhắn mới vào lịch sử
        history.add(new ChatMessage(userMessage, aiResponse));
        session.setAttribute("chatHistory", history);

        // Trả phản hồi dạng plain text để hiển thị trên popup
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(aiResponse);
    }

    // GET: reset lịch sử trò chuyện nếu cần
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().removeAttribute("chatHistory");
        request.getRequestDispatcher("chat/chat.jsp").forward(request, response);
    }
}
