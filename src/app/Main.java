package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tải FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chat_ui.fxml"));
        AnchorPane root = loader.load();

        // Tạo Scene và thêm CSS
        Scene scene = new Scene(root);
        // Thêm tệp CSS vào Scene
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Thiết lập Stage
        primaryStage.setTitle("Chatbot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
