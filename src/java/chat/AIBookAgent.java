package chat;

import bookDao.BookDao;
import model.Book;
import model.ChatMessage;

import java.util.List;

public class AIBookAgent {

    public static String generatePrompt(String userMessage, List<ChatMessage> history) {
        BookDao dao = new BookDao();
        List<Book> books = dao.selectAllBooksWithDetails();

        StringBuilder prompt = new StringBuilder();

        prompt.append("Bạn là trợ lý AI tư vấn sách tại BookZone. Trả lời ngắn gọn, đúng trọng tâm.\n");

        // 🧠 Thêm các tin nhắn trước đó vào prompt
        if (history != null && !history.isEmpty()) {
            prompt.append("\nLịch sử hội thoại:\n");
            int maxHistory = Math.min(5, history.size());
            for (int i = history.size() - maxHistory; i < history.size(); i++) {
                ChatMessage msg = history.get(i);
                prompt.append("Người dùng: ").append(msg.getUser()).append("\n");
                prompt.append("Trợ lý: ").append(msg.getBot()).append("\n");
            }
        }

        // 🆕 Thêm câu hỏi hiện tại
        prompt.append("\nNgười dùng: ").append(userMessage).append("\n");

        // 📚 Gắn dữ liệu sách
        prompt.append("\nDanh sách sách hiện có:\n");
        for (Book b : books) {
            prompt.append("- ")
                    .append(b.getTitle()).append(" - ")
                    .append("Tác giả: ").append(b.getAuthor()).append(" - ")
                    .append("Thể loại: ").append(b.getCategoryName()).append(" - ")
                    .append("Giá: ").append(b.getPrice()).append(" VND")
                    .append("\n");
        }

        prompt.append("\nHãy dựa vào hội thoại và danh sách sách để trả lời.\n");

        return prompt.toString();
    }
}
