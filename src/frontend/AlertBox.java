package frontend;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label(message);

        VBox vBoxLayout = new VBox(10);
        vBoxLayout.getChildren().addAll(label);

        Scene scene = new Scene(vBoxLayout);
        window.setScene(scene);
        window.showAndWait();
    }
}
