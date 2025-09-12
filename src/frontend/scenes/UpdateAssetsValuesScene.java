package frontend.scenes;

import backend.Main;
import backend.Team;
import frontend.Constants;
import frontend.helpingClasses.NavigationBar;
import frontend.helpingClasses.PopUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UpdateAssetsValuesScene implements Constants {
    private Scene updateAssestsValuesScene;

    private NavigationBar navBar;
    private VBox mainContainer;

    private HBox rootLayout;
    private VBox changeShareCustomPriceFormVBox;
    private GridPane changeShareCustomPriceFormGridPane;
    private Button changeShareCustomPriceSubmitButton;

    private VBox eventConsequencesVBox;
    private GridPane eventConsequencesGridPane;
    private Button eventConsequencesSubmitButton;

    private void layoutInitializer() {
        navBar = new NavigationBar();
        mainContainer = new VBox();
        rootLayout = new HBox();

        changeShareCustomPriceFormVBox = new VBox();
        changeShareCustomPriceFormGridPane = new GridPane();
        changeShareCustomPriceSubmitButton = new Button("Submit");

        eventConsequencesVBox = new VBox();
        eventConsequencesGridPane = new GridPane();
        eventConsequencesSubmitButton = new Button("Submit");
    }

    private void layoutOrganizer() {
        mainContainer.getChildren().addAll(navBar.getTitleBar(), rootLayout);
        mainContainer.setSpacing(120);

        rootLayout.getChildren().addAll(changeShareCustomPriceFormVBox, eventConsequencesVBox);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setSpacing(100);
    }

    private void showChangeShareCustomPriceFormScene() {
        changeShareCustomPriceFormVBox.getChildren().addAll(changeShareCustomPriceFormGridPane, changeShareCustomPriceSubmitButton);
        changeShareCustomPriceFormVBox.setAlignment(Pos.CENTER);
        changeShareCustomPriceFormVBox.setSpacing(50);

        Label typeOfOperationLabel = new Label("Type of operation:");
        Label teamLabel = new Label("Choose Team");
        Label valueOfPercentLabel = new Label("Value of %");

        ObservableList<String> selectOperationTypeObservableList = FXCollections.observableArrayList();
        selectOperationTypeObservableList.addAll("Add", "Minus");
        ComboBox<String> selectOperationTypeComboBox = new ComboBox<>(selectOperationTypeObservableList);
        selectOperationTypeComboBox.getSelectionModel().selectFirst();
        selectOperationTypeComboBox.setMinWidth(250);

        ObservableList<Team> selectTeam = FXCollections.observableArrayList();
        selectTeam.addAll(Main.getTeamsFromFrontEnd());
        ChoiceBox<Team> selectTeamComboBox = new ChoiceBox<>(selectTeam);
        selectTeamComboBox.setMinWidth(250);

        TextField valueOfPercentTextField = new TextField();
        valueOfPercentTextField.setPromptText("Value of %");

        changeShareCustomPriceFormGridPane.add(typeOfOperationLabel, 0, 0);
        changeShareCustomPriceFormGridPane.add(selectOperationTypeComboBox, 1, 0);
        changeShareCustomPriceFormGridPane.add(teamLabel, 0, 1);
        changeShareCustomPriceFormGridPane.add(selectTeamComboBox, 1, 1);
        changeShareCustomPriceFormGridPane.add(valueOfPercentLabel, 0, 2);
        changeShareCustomPriceFormGridPane.add(valueOfPercentTextField, 1, 2);
        changeShareCustomPriceFormGridPane.setAlignment(Pos.CENTER);
        changeShareCustomPriceFormGridPane.setHgap(20);
        changeShareCustomPriceFormGridPane.setVgap(20);

        changeShareCustomPriceSubmitButton.setOnAction(_ -> {
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

    private void showEventConsequencesScene() {
        eventConsequencesVBox.getChildren().addAll(eventConsequencesGridPane, eventConsequencesSubmitButton);
        eventConsequencesVBox.setAlignment(Pos.CENTER);
        eventConsequencesVBox.setSpacing(50);

        Label teamLabel = new Label("Choose Team");
        Label eventLabel = new Label("Choose Event");

        ObservableList<Team> selectTeam = FXCollections.observableArrayList();
        selectTeam.addAll(Main.getTeamsFromFrontEnd());
        ChoiceBox<Team> selectTeamComboBox = new ChoiceBox<>(selectTeam);
        selectTeamComboBox.setMinWidth(250);

        ObservableList<String> selectEventObservableList = FXCollections.observableArrayList();
        selectEventObservableList.addAll("Lecture Task", "Workshop Task", "Gathering", "Winning Game", "Attending Prayer", "Not Submitting Task");
        ComboBox<String> selectEventComboBox = new ComboBox<>(selectEventObservableList);
        selectEventComboBox.setMinWidth(250);

        eventConsequencesGridPane.add(teamLabel, 0, 0);
        eventConsequencesGridPane.add(selectTeamComboBox, 1, 0);
        eventConsequencesGridPane.add(eventLabel, 0, 1);
        eventConsequencesGridPane.add(selectEventComboBox, 1, 1);
        eventConsequencesGridPane.setAlignment(Pos.CENTER);
        eventConsequencesGridPane.setHgap(20);
        eventConsequencesGridPane.setVgap(20);

        eventConsequencesSubmitButton.setOnAction(_ -> {
            if ((selectTeamComboBox.getValue() == null) || (selectEventComboBox.getValue() == null)) {
                PopUp.display("Empty Fields", "Empty Fields\nMake Sure You Have Filled All Fields", true);
            } else {
                switch (selectEventComboBox.getValue()) {
                    case "Lecture Task":
                        addPercentToSharePrice(selectTeamComboBox.getValue(), 25);
                        addBalance(selectTeamComboBox.getValue(), 500);
                        break;
                    case "Workshop Task", "Winning Game":
                        addPercentToSharePrice(selectTeamComboBox.getValue(), 20);
                        addBalance(selectTeamComboBox.getValue(), 300);
                        break;
                    case "Gathering":
                        addPercentToSharePrice(selectTeamComboBox.getValue(), 5);
                        addBalance(selectTeamComboBox.getValue(), 100);
                        break;
                    case "Attending Prayer":
                        addBalance(selectTeamComboBox.getValue(), 200);
                        break;
                    case "Not Submitting Task":
                        subtractBalance(selectTeamComboBox.getValue(), 200);
                        break;
                }
                selectEventComboBox.getSelectionModel().clearSelection();
                selectTeamComboBox.getSelectionModel().clearSelection();
            }
        });
    }

    private void addPercentToSharePrice(Team team, int added_percent) {
        switch (Main.addPercentToSharePriceFromFrontEnd(team, added_percent)) {
            case 0:
                PopUp.display("Percent Added Successfully", "Percent Added Successfully", false);
                Main.saveTeamsToFileFromFrontEnd();
                Main.saveSharesToFileFromFrontEnd();
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
                Main.saveTeamsToFileFromFrontEnd();
                Main.saveSharesToFileFromFrontEnd();
                break;
            case 1:
                PopUp.display("Error Subtracting Percent", "Selected \"Team\" not found", true);
                break;
        }
    }

    private void addBalance(Team team, double added_balance) {
        switch (Main.addBalanceToTeamFromFrontEnd(team, added_balance)) {
            case 0:
                PopUp.display("Balance Added Successfully", "Balance Added Successfully", false);
                Main.saveTeamsToFileFromFrontEnd();
                Main.saveSharesToFileFromFrontEnd();
                break;
            case 1:
                PopUp.display("Error Adding Balance", "Invalid Amount", true);
                break;
        }
    }

    private void subtractBalance(Team team, double subtracted_balance) {
        switch (Main.subtractBalanceFromTeamFromFrontEnd(team, subtracted_balance)) {
            case 0:
                PopUp.display("Balance Subtracted Successfully", "Balance Subtracted Successfully", false);
                Main.saveTeamsToFileFromFrontEnd();
                Main.saveSharesToFileFromFrontEnd();
                break;
            case 1:
                PopUp.display("Error Subtracting Balance", "Invalid Amount", true);
                break;
            case 2:
                PopUp.display("Error Subtracting Balance", "Insufficient Balance", true);
                break;
        }
    }

    private void setScene() {
        updateAssestsValuesScene = new Scene(mainContainer, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        updateAssestsValuesScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }

    public Scene getScene() {
        layoutInitializer();
        layoutOrganizer();
        showChangeShareCustomPriceFormScene();
        showEventConsequencesScene();
        setScene();
        return updateAssestsValuesScene;
    }
}
