package app.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.json.JSONArray;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class OpenAIClient {
    private static final String API_KEY;

    // ✅ Đọc API key từ file .env
    static {
        API_KEY = loadApiKeyFromEnv();
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new RuntimeException("Không thể đọc OpenAI API Key từ file .env");
        }
    }

    // ✅ Hàm đọc .env file
    private static String loadApiKeyFromEnv() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(".env"));
            for (String line : lines) {
                if (line.startsWith("OPENAI_API_KEY=")) {
                    return line.split("=", 2)[1].trim();
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc .env: " + e.getMessage());
        }
        return null;
    }

    public static String ask(String prompt) {
        try {
            JSONObject json = new JSONObject()
                    .put("model", "gpt-4o-mini") // hoặc gpt-3.5-turbo nếu bạn không dùng gpt-4
                    .put("messages", new JSONArray()
                            .put(new JSONObject().put("role", "user").put("content", prompt)));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Debug: In response để kiểm tra
            System.out.println("OpenAI Response:\n" + response.body());

            JSONObject result = new JSONObject(response.body());
            if (result.has("choices")) {
                JSONArray choices = result.getJSONArray("choices");
                if (choices.length() > 0) {
                    return choices.getJSONObject(0).getJSONObject("message").getString("content");
                }
            }
            return "Trường 'choices' không tồn tại trong phản hồi từ OpenAI.";
        } catch (Exception e) {
            return "Đã xảy ra lỗi khi gọi OpenAI: " + e.getMessage();
        }
    }
}
