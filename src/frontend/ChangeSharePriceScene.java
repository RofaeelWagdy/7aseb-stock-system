package frontend;

import backend.Main;
import backend.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ChangeSharePriceScene implements Constants {
    private Scene changeSharePriceScene;

    private NavigationBar navBar;
    private VBox mainContainer;

    private VBox rootLayout;
    private GridPane changeSharePriceFormGridPane;
    private Button changeSharePriceSubmitButton;

    private void layoutInitializer() {
        navBar = new NavigationBar();
        mainContainer = new VBox();
        rootLayout = new VBox();
        changeSharePriceFormGridPane = new GridPane();
        changeSharePriceSubmitButton = new Button("Submit");
    }

    private void layoutOrganizer() {
        mainContainer.getChildren().addAll(navBar.getTitleBar(), rootLayout);
        mainContainer.setSpacing(200);

        rootLayout.getChildren().addAll(changeSharePriceFormGridPane, changeSharePriceSubmitButton);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setSpacing(50);
    }

    private void showChangeSharePriceFormScene() {
        Label typeOfOperationLabel = new Label("Type of operation:");
        Label teamLabel = new Label("Choose Team");
        Label valueOfPercentLabel = new Label("Value of %");

        ObservableList<String> selectOperationType = FXCollections.observableArrayList();
        selectOperationType.addAll("Add", "Minus");
        ComboBox<String> selectOperationTypeComboBox = new ComboBox<>(selectOperationType);
        selectOperationTypeComboBox.getSelectionModel().selectFirst();
        selectOperationTypeComboBox.setMinWidth(250);

        ObservableList<Team> selectTeam = FXCollections.observableArrayList();
        selectTeam.addAll(Main.getTeamsFromFrontEnd());
        ChoiceBox<Team> selectTeamComboBox = new ChoiceBox<>(selectTeam);
        selectTeamComboBox.setMinWidth(250);

        TextField valueOfPercentTextField = new TextField();
        valueOfPercentTextField.setPromptText("Value of %");

        changeSharePriceFormGridPane.add(typeOfOperationLabel, 0, 0);
        changeSharePriceFormGridPane.add(selectOperationTypeComboBox, 1, 0);
        changeSharePriceFormGridPane.add(teamLabel, 0, 1);
        changeSharePriceFormGridPane.add(selectTeamComboBox, 1, 1);
        changeSharePriceFormGridPane.add(valueOfPercentLabel, 0, 2);
        changeSharePriceFormGridPane.add(valueOfPercentTextField, 1, 2);
        changeSharePriceFormGridPane.setAlignment(Pos.CENTER);
        changeSharePriceFormGridPane.setHgap(20);
        changeSharePriceFormGridPane.setVgap(20);

        changeSharePriceSubmitButton.setOnAction(_ -> {
            if ((selectTeamComboBox.getValue() == null) || (valueOfPercentTextField.getText().isEmpty())) {
                PopUp.display("Empty Fields", "Empty Fields\nMake Sure You Have Filled All Fields", true);
            } else {
                if (selectOperationTypeComboBox.getValue().equals("Add")) {
                    addPercentToSharePrice(selectTeamComboBox.getValue(), Integer.parseInt(valueOfPercentTextField.getText()));
                } else {
                    subtractPercentFromSharePrice(selectTeamComboBox.getValue(), Integer.parseInt(valueOfPercentTextField.getText()));
                }
                valueOfPercentTextField.clear();
                selectTeamComboBox.getSelectionModel().clearSelection();
            }
        });
    }

    private void addPercentToSharePrice(Team team, int added_percent) {
        switch (Main.addPercentToSharePriceFromFrontEnd(team, added_percent)) {
            case 0:
                PopUp.display("Percent Added Successfully", "Percent Added Successfully", false);
                break;
            case 1:
                PopUp.display("Error Adding Percent", "Selected \"Team\" not found", true);
                break;
        }
    }

    private void subtractPercentFromSharePrice(Team team, int subtracted_percent) {
        switch (Main.subtractPercentFromSharePriceFromFrontEnd(team, subtracted_percent)) {
            case 0:
                PopUp.display("Percent Subtracted Successfully", "Percent Subtracted Successfully", false);
                break;
            case 1:
                PopUp.display("Error Subtracting Percent", "Selected \"Team\" not found", true);
                break;
        }
    }

    private void setScene() {
        changeSharePriceScene = new Scene(mainContainer, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
    }

    public Scene getScene() {
        layoutInitializer();
        layoutOrganizer();
        showChangeSharePriceFormScene();
        setScene();
        return changeSharePriceScene;
    }
}
