package frontend.helpingClasses;

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

public class PopUp {
    public static void display(String title, String message, boolean error) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Image logo;
        if (error) {
            logo = new Image(new File("src/resources/crc_error.png").toURI().toString());
        } else {
            logo = new Image(new File("src/resources/crc.png").toURI().toString());
        }
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(50);
        logoView.setPreserveRatio(true);

        Label alertMessage = new Label(message);
        HBox messageLayout = new HBox(20);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(_ -> window.close());

        VBox alertLayout = new VBox(20);
        alertLayout.getChildren().addAll(messageLayout, closeButton);
        alertLayout.setAlignment(Pos.CENTER);

        Scene alertScene = new Scene(alertLayout, 500, 150);
//        alertScene.getStylesheets().add(AlertBox.class.getResource("styles.css").toExternalForm());

        messageLayout.getChildren().add(logoView);
        messageLayout.getChildren().add(alertMessage);
        messageLayout.setAlignment(Pos.CENTER);

        window.setScene(alertScene);
        window.showAndWait();

    }
}
