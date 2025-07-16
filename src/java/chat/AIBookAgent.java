package chat;

import bookDao.BookDao;
import model.Book;

import java.util.List;

public class AIBookAgent {

    public static String generatePrompt(String userMessage) {
        BookDao dao = new BookDao();
        List<Book> books = dao.selectAllBooksWithDetails(); // nên đổi hàm này sang JOIN category như đã hướng dẫn

        StringBuilder prompt = new StringBuilder();
        prompt.append("Người dùng hỏi: ").append(userMessage).append("\n\n");

        prompt.append("Danh sách sách trong thư viện:\n");
        for (Book b : books) {
            prompt.append("- Tên: ").append(b.getTitle())
                    .append("\n  Tác giả: ").append(b.getAuthor())
                    .append("\n  Thể loại: ").append(b.getCategoryName())
                    .append("\n  Mô tả: ").append(b.getDescription())
                    .append("\n  Giá: ").append(b.getPrice()).append(" VND")
                    .append("Số lượng: ").append(b.getQuantity()).append(" - ")
                    .append("\n\n");
        }

        prompt.append("Dựa vào danh sách trên, nếu người dùng nhắc tới tên sách hoặc thể loại, hãy gợi ý hoặc hiển thị thông tin chi tiết phù hợp. Nếu không tìm thấy, hãy nói rằng sách đó chưa có trong thư viện.");

        return prompt.toString();
    }
}
