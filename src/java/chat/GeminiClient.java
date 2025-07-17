package chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.*;

import java.util.List;
import bookDao.BookDao;
import model.Book;

public class GeminiClient {

    private static final String API_KEY = "AIzaSyBWWsQspMM5WUZuQ0YuU9lm6xc7-EI24BU";
    private static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;
    private static final int MAX_RETRIES = 1;
    private static final int RETRY_DELAY_MS = 1000;

    public static String chatWith(String userMessage) {
        String bookData = getBookDataFromDB();

       String systemPrompt = """
Bạn là trợ lý AI thông minh tại BookZone, có nhiệm vụ tư vấn sách cho người dùng. Hãy tuân thủ các nguyên tắc sau:

1. Trả lời ngắn gọn (1–2 câu), đúng trọng tâm, dễ hiểu.
2. Nếu người dùng nhắc đến thể loại (ví dụ: trinh thám, kinh dị, học thuật, anime, manga...) hoặc nói "mình thích..." → phân tích sở thích và gợi ý 3–5 sách phù hợp từ dữ liệu theo định dạng:

1. Tên sách - Tác giả - Giá VND  
   Mô tả ngắn 1 dòng.
2. ...

3. Nếu người dùng hỏi về khoảng giá, tác giả hoặc tên sách → tìm các sách khớp trong dữ liệu.
4. Luôn cố gắng sử dụng thông tin từ dữ liệu có sẵn để tư vấn chính xác.
5. KHÔNG hỏi lại nếu đã có đủ dữ liệu để trả lời.
6. Nếu không tìm thấy sách phù hợp, hãy nói nhẹ nhàng rằng không có thông tin phù hợp trong kho dữ liệu hiện tại.

DỮ LIỆU SÁCH HIỆN CÓ:
""" + bookData;

        String fullPrompt = systemPrompt + "\n\nNGƯỜI DÙNG: " + userMessage + "\nTRỢ LÝ:";

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                HttpResponse<String> response = Unirest.post(URL)
                        .header("Content-Type", "application/json")
                        .body("{\"contents\":[{\"parts\":[{\"text\":\"" + escapeJson(fullPrompt) + "\"}]}]}")
                        .asString();

                JsonNode root = new ObjectMapper().readTree(response.getBody());

                if (root.has("error")) {
                    String errorMsg = root.get("error").get("message").asText();
                    if (errorMsg.contains("overloaded")) {
                        Thread.sleep(RETRY_DELAY_MS);
                        continue;
                    }
                    return "⚠️ Lỗi: " + errorMsg;
                }

                return extractGeminiResponse(root);
            } catch (Exception e) {
                e.printStackTrace();
                if (attempt < MAX_RETRIES) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    return "⚠️ Lỗi kết nối đến Gemini";
                }
            }
        }

        return "⚠️ Không thể kết nối sau " + MAX_RETRIES + " lần thử";
    }

    private static String getBookDataFromDB() {
        try {
            BookDao dao = new BookDao();
            List<Book> books = dao.selectAllBooksWithDetails();

            StringBuilder sb = new StringBuilder();
            for (Book b : books) {
                sb.append("- ")
                        .append(b.getTitle()).append(" - ")
                        .append("Tác giả: ").append(b.getAuthor()).append(" - ")
                        .append("Thể loại: ").append(b.getCategoryName()).append(" - ")
                        .append("Giá: ").append(b.getPrice()).append(" VND - ")
                        .append("Số lượng: ").append(b.getQuantity()).append(" - ")
                        .append("Mô tả: ").append(b.getDescription() == null ? "Không có" : b.getDescription())
                        .append("\n");
            }

            return sb.toString();
        } catch (Exception e) {
            return "Không thể tải dữ liệu sách.";
        }
    }

    private static String extractGeminiResponse(JsonNode root) {
        try {
            return root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText();
        } catch (Exception e) {
            return "⚠️ Không thể đọc phản hồi từ Gemini";
        }
    }

    private static String escapeJson(String input) {
        return input.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
    }
}
