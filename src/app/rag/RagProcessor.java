package app.rag;

import app.util.OpenAIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RagProcessor {
    private final String context;

    public RagProcessor() {
        context = loadProductData();
    }

    private String loadProductData() {
        try {
            String json = Files.readString(Paths.get("src/app/data/products.json"));
            JSONArray arr = new JSONArray(json);
            StringBuilder sb = new StringBuilder("Dữ liệu sản phẩm:\n");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                sb.append("- ")
                        .append(o.getString("type")).append(" ")
                        .append(o.getString("brand")).append(": ")
                        .append(o.getString("description")).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Không thể tải dữ liệu.";
        }
    }

    public String answerQuestion(String question) {
        String prompt = context + "\n\nTrả lời câu hỏi sau dựa trên thông tin trên:\n" + question;
        return OpenAIClient.ask(prompt);
    }
}
