package chat;

import bookDao.BookDao;
import model.Book;
import model.ChatMessage;

import java.util.List;
import model.User;

public class AIBookAgent {

    public static String generatePrompt(String userMessage, List<ChatMessage> history, User user) {
        BookDao dao = new BookDao();
        List<Book> books = dao.selectAllBooksWithDetails();

        StringBuilder prompt = new StringBuilder();

        // 🧠 Giới thiệu ngữ cảnh
        prompt.append("Bạn là trợ lý AI tư vấn sách tại BookZone. Trả lời ngắn gọn, đúng trọng tâm.\n");

        // 🧍‍♂️ Thêm thông tin người dùng nếu có
        if (user != null) {
            prompt.append("\nThông tin người dùng:\n");
            prompt.append("- Họ tên: ").append(user.getName()).append("\n");
            prompt.append("- Email: ").append(user.getEmail()).append("\n");
            prompt.append("- Địa chỉ: ").append(user.getAddress()).append("\n");
            prompt.append("- Số điện thoại: ").append(user.getPhone()).append("\n");
        }

        // 🧠 Thêm lịch sử hội thoại
        if (history != null && !history.isEmpty()) {
            prompt.append("\nLịch sử hội thoại gần đây:\n");
            int maxHistory = Math.min(5, history.size());
            for (int i = history.size() - maxHistory; i < history.size(); i++) {
                ChatMessage msg = history.get(i);
                prompt.append("Người dùng: ").append(msg.getUserMessage()).append("\n");
                prompt.append("Trợ lý: ").append(msg.getAiResponse()).append("\n");
            }
        }

        // 🆕 Thêm câu hỏi hiện tại
        prompt.append("\nNgười dùng: ").append(userMessage).append("\n");

        // 📚 Danh sách sách để AI tư vấn
        prompt.append("\nDanh sách sách hiện có:\n");
        for (Book b : books) {
            prompt.append("- ")
                    .append(b.getTitle()).append(" | Tác giả: ").append(b.getAuthor())
                    .append(" | Thể loại: ").append(b.getCategoryName())
                    .append(" | Giá: ").append(b.getPrice()).append(" VND")
                    .append("\n");
        }

        prompt.append("\nHãy dựa vào hội thoại, thông tin người dùng và sách để tư vấn sách phù hợp.\n");

        return prompt.toString();
    }
}
