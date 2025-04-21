package app.controller;

import app.rag.RagProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField userInput;

    private final RagProcessor ragProcessor = new RagProcessor();

    @FXML
    private void handleSend() {
        String question = userInput.getText();
        if (question.isEmpty()) return;

        chatArea.appendText("Bạn: " + question + "\n");
        String answer = ragProcessor.answerQuestion(question);
        chatArea.appendText("Bot: " + answer + "\n");

        userInput.clear();
    }
}
