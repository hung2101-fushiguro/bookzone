package chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.*;

public class GeminiClient {
    private static final String API_KEY = "AIzaSyAU4LL64Mw8t8jFWEwjHedAqobY87ZgMgs";
    private static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    public static String sendPrompt(String prompt) {
    try {
        String contextData = "";
        String systemPrompt = "Bạn là một AI tư vấn sách tại BookSZone. Hãy trả lời thật ngắn gọn và giới thiệu sách cụ thể trong kho sau tối đa 2 lượt trò chuyện. " +
                "Tránh hỏi lan man. Nếu người dùng nhắc đến một thể loại, hãy gợi ý trực tiếp các cuốn sách có thể có trong hệ thống.\n\n" +
                contextData;

        String fullPrompt = systemPrompt + "\n\nNgười dùng: " + prompt;

        HttpResponse<String> response = Unirest.post(URL)
            .header("Content-Type", "application/json")
            .body("{\"contents\":[{\"parts\":[{\"text\":\"" + fullPrompt + "\"}]}]}")
            .asString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        JsonNode textNode = root.path("candidates").get(0)
                                 .path("content")
                                 .path("parts").get(0)
                                 .path("text");

        return textNode.isMissingNode() ? "Không tìm thấy nội dung trả lời." : textNode.asText();
    } catch (Exception e) {
        e.printStackTrace();
        return "Lỗi gọi Gemini: " + e.getMessage();
    }
}
}
