package frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        Label stockMarketTitle = new Label("Stock Market System");
        Button stockMarketButton = new Button("Stock Market");
        stockMarketButton.setOnAction(e -> AlertBox.display("Stock Market System", "Stock Market System has been changed!"));

        StackPane root = new StackPane();
        root.getChildren().addAll(stockMarketTitle, stockMarketButton);

        Scene scene = new Scene(root, 400, 400);
        window.setScene(scene);
        window.setTitle("7aseb Stock Market System");
        window.show();
    }
}
