package controller;

import bookDao.BookDao;
import chat.AIBookAgent;
import chat.BookRecommender;
import dao.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends HttpServlet {

    private AIBookAgent agent;

    @Override
    public void init() {
        try {
            var conn = DBConnection.getConnection();
            if (conn != null) {
                BookDao bookDao = new BookDao(conn);
                BookRecommender recommender = new BookRecommender(bookDao);
                agent = new AIBookAgent(recommender);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userMessage = request.getParameter("msg");
        String reply = (agent != null) ? agent.handlePrompt(userMessage) : "⚠️ Agent chưa được khởi tạo. Vui lòng thử lại sau.";

        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(reply);
        out.flush();
    }
}
