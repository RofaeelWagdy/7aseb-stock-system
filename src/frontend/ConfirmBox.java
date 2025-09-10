package frontend;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class ConfirmBox {
    static boolean answer;
    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label alertMessage = new Label(message);

        Image logo = new Image(new File("src/resources/question.png").toURI().toString());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(50);
        logoView.setPreserveRatio(true);

        HBox messageLayout = new HBox(20);
        messageLayout.getChildren().add(logoView);
        messageLayout.getChildren().add(alertMessage);
        messageLayout.setAlignment(Pos.CENTER);


        Button yesButton = new Button("Yes");
        yesButton.setOnAction(_ -> {
            answer = true;
            window.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(_ -> {
            answer = false;
            window.close();
        });

        HBox buttonsLayout = new HBox(20);
        buttonsLayout.getChildren().addAll(yesButton, noButton);
        buttonsLayout.setAlignment(Pos.CENTER);

        VBox alertLayout = new VBox(20);
        alertLayout.getChildren().addAll(messageLayout, buttonsLayout);
        alertLayout.setAlignment(Pos.CENTER);

        Scene alertScene = new Scene(alertLayout, 500, 150);
//        alertScene.getStylesheets().add(AlertBox.class.getResource("styles.css").toExternalForm());

        window.setScene(alertScene);
        window.showAndWait();

        return answer;
    }
}
