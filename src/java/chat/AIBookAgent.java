package chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.*;

import java.sql.SQLException;
import java.util.*;
import model.Book;

public class AIBookAgent {

    private BookRecommender recommender;
    private Map<String, String> conversationHistory = new HashMap<>();  // Lưu lại lịch sử trò chuyện

    public AIBookAgent(BookRecommender recommender) {
        this.recommender = recommender;
    }

    public String handlePrompt(String userMessage) {
        try {
            saveSearchHistory(userMessage);

            // Sử dụng Gemini API để trả lời câu hỏi từ người dùng
            String geminiResponse = GeminiClient.sendPrompt(userMessage);
            if (geminiResponse != null && !geminiResponse.isEmpty()) {
                return geminiResponse;
            }

            // Trả lời yêu cầu thể loại sách
            String genreResponse = handleGenreRequest(userMessage);
            if (!genreResponse.isEmpty()) {
                return genreResponse;
            }

            // Trả lời yêu cầu sách theo tác giả
            String authorResponse = handleAuthorRequest(userMessage);
            if (!authorResponse.isEmpty()) {
                return authorResponse;
            }

            // Trả lời yêu cầu sách theo mô tả
            String descriptionResponse = handleDescriptionRequest(userMessage);
            if (!descriptionResponse.isEmpty()) {
                return descriptionResponse;
            }

            return "Xin lỗi, tôi không nhận diện được yêu cầu của bạn.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi xử lý AI: " + e.getMessage();
        }
    }

    private void saveSearchHistory(String userMessage) {
        conversationHistory.put(String.valueOf(System.currentTimeMillis()), userMessage);
    }

    // Tự động phát hiện từ khóa thể loại sách từ câu hỏi của người dùng
    private String extractKeyword(String msg) {
        msg = msg.toLowerCase();
        String[] knownKeywords = {
            "tiểu thuyết", "khoa học", "lịch sử", "tâm lý",
            "thiếu nhi", "trinh thám", "kinh doanh", "kỹ năng sống", "văn học nước ngoài", "manga", "ngôn tình"
        };
        for (String kw : knownKeywords) {
            if (msg.contains(kw)) {
                return kw;
            }
        }
        return null;
    }

    // Xử lý câu hỏi về thể loại sách
    private String handleGenreRequest(String msg) {
        String genre = extractKeyword(msg);
        if (genre != null) {
            try {
                List<String> books = recommender.recommendByGenre(genre);
                if (books.isEmpty()) {
                    return "Xin lỗi, không có sách nào trong thể loại '" + genre + "' trong kho của chúng tôi.";
                }
                return formatBookListResponse(books, "thể loại '" + genre + "'");
            } catch (Exception e) {
                return "Lỗi khi truy vấn sách thể loại: " + e.getMessage();
            }
        }
        return "";
    }

    // Xử lý yêu cầu sách theo tác giả
    private String handleAuthorRequest(String msg) {
        String author = extractKeyword(msg);
        if (author != null) {
            try {
                List<Book> books = recommender.recommendByAuthor(author);
                if (books.isEmpty()) {
                    return "Không tìm thấy sách nào của tác giả '" + author + "' trong kho của chúng tôi.";
                }
                return formatBookAuthorResponse(books, "tác giả '" + author + "'");
            } catch (Exception e) {
                return "Lỗi khi truy vấn sách của tác giả: " + e.getMessage();
            }
        }
        return ""; 
    }

    // Trả về danh sách sách của tác giả và thêm <br> cho xuống dòng
    private String formatBookAuthorResponse(List<Book> books, String context) {
        StringBuilder response = new StringBuilder();
        response.append("Dưới đây là danh sách sách của " + context + ":<br>");
        for (Book book : books) {
            response.append("- " + book.getTitle() + ": " + book.getDescription() + "<br>");
        }
        return response.toString();  
    }

    // Xử lý yêu cầu sách theo tên (title) sách và trả về mô tả cụ thể
    public String handleDescriptionRequest(String msg) {
        try {
            List<Book> books = searchBooksInMessage(msg);
            if (books.isEmpty()) {
                return "Không tìm thấy sách với tên '" + msg + "' trong kho của chúng tôi.";
            }
            Book book = books.get(0);
            return "Thông tin sách '" + book.getTitle() + "':<br>"
                + "Tác giả: " + book.getAuthor() + "<br>"
                + "Giá: " + book.getPrice() + " VND<br>"
                + "Mô tả: " + book.getDescription() + "<br>";
        } catch (SQLException e) {
            return "Lỗi khi truy vấn sách: " + e.getMessage();
        }
    }

    // Tìm sách trong câu người dùng
    private List<Book> searchBooksInMessage(String msg) throws SQLException {
        List<Book> books = new ArrayList<>();
        List<String> bookTitles = recommender.recommendByKeyword("");
        String foundTitle = null;
        for (String title : bookTitles) {
            if (msg.toLowerCase().contains(title.toLowerCase())) {
                foundTitle = title;
                break;
            }
        }
        if (foundTitle != null) {
            books = recommender.searchBooks(foundTitle);
        }

        return books;
    }

    // Trả về mô tả sách dưới dạng chuỗi
    private String formatBookDescriptionResponse(List<Book> books, String context) {
        StringBuilder response = new StringBuilder();
        response.append("Dưới đây là danh sách sách " + context + ":\n");
        for (Book book : books) {
            response.append(book.getTitle() + ": " + book.getDescription() + "\n");
        }
        return response.toString();
    }

    // Trả về danh sách sách dưới dạng chuỗi
    private String formatBookListResponse(List<String> books, String context) {
        StringBuilder response = new StringBuilder();
        response.append("Dưới đây là danh sách sách " + context + ":\n");
        for (String title : books) {
            response.append("- ").append(title).append("\n");
        }
        return response.toString();
    }

}
