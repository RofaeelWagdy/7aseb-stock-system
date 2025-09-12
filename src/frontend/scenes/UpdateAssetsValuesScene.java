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

    private VBox eventConsequencesVBox;
    private GridPane eventConsequencesGridPane;
    private Button eventConsequencesSubmitButton;

    private VBox customUpdateAssetsFormVBox;
    private GridPane customUpdateAssetsFormGridPane;
    private Button customUpdateAssetsSubmitButton;

    private void layoutInitializer() {
        navBar = new NavigationBar();
        mainContainer = new VBox();
        rootLayout = new HBox();

        eventConsequencesVBox = new VBox();
        eventConsequencesGridPane = new GridPane();
        eventConsequencesSubmitButton = new Button("Submit");

        customUpdateAssetsFormVBox = new VBox();
        customUpdateAssetsFormGridPane = new GridPane();
        customUpdateAssetsSubmitButton = new Button("Submit");
    }

    private void layoutOrganizer() {
        mainContainer.getChildren().addAll(navBar.getTitleBar(), rootLayout);
        mainContainer.setSpacing(120);

        rootLayout.getChildren().addAll(eventConsequencesVBox, customUpdateAssetsFormVBox);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setSpacing(120);
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
        selectEventObservableList.addAll("Lecture Task", "Workshop Task", "Gathering", "1st Game Winner", "2nd Game Winner", "3rd Game Winner", "Rest Winning Teams", "Attending Prayer", "Not Submitting Task");
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
                    case "Workshop Task", "1st Game Winner":
                        addPercentToSharePrice(selectTeamComboBox.getValue(), 20);
                        addBalance(selectTeamComboBox.getValue(), 300);
                        break;
                    case "2nd Game Winner":
                        addPercentToSharePrice(selectTeamComboBox.getValue(), 15);
                        addBalance(selectTeamComboBox.getValue(), 250);
                        break;
                    case "3rd Game Winner":
                        addPercentToSharePrice(selectTeamComboBox.getValue(), 10);
                        addBalance(selectTeamComboBox.getValue(), 200);
                        break;
                    case "Rest Winning Teams", "Gathering":
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

    private void showCustomUpdateAssetsFormScene() {
        customUpdateAssetsFormVBox.getChildren().addAll(customUpdateAssetsFormGridPane, customUpdateAssetsSubmitButton);
        customUpdateAssetsFormVBox.setAlignment(Pos.CENTER);
        customUpdateAssetsFormVBox.setSpacing(50);

        Label typeOfOperationLabel = new Label("Operation Type: ");
        Label teamLabel = new Label("Choose Team: ");
        Label valueOfPercentLabel = new Label("Value: ");

        ObservableList<String> selectOperationTypeObservableList = FXCollections.observableArrayList();
        selectOperationTypeObservableList.addAll("Add Balance", "Minus Balance", "Add Percent to Share", "Minus Percent from Share");
        ComboBox<String> selectOperationTypeComboBox = new ComboBox<>(selectOperationTypeObservableList);
        selectOperationTypeComboBox.setMinWidth(250);

        ObservableList<Team> selectTeam = FXCollections.observableArrayList();
        selectTeam.addAll(Main.getTeamsFromFrontEnd());
        ChoiceBox<Team> selectTeamComboBox = new ChoiceBox<>(selectTeam);
        selectTeamComboBox.setMinWidth(250);

        TextField valueTextField = new TextField();
        valueTextField.setPromptText("Value");

        customUpdateAssetsFormGridPane.add(typeOfOperationLabel, 0, 0);
        customUpdateAssetsFormGridPane.add(selectOperationTypeComboBox, 1, 0);
        customUpdateAssetsFormGridPane.add(teamLabel, 0, 1);
        customUpdateAssetsFormGridPane.add(selectTeamComboBox, 1, 1);
        customUpdateAssetsFormGridPane.add(valueOfPercentLabel, 0, 2);
        customUpdateAssetsFormGridPane.add(valueTextField, 1, 2);
        customUpdateAssetsFormGridPane.setAlignment(Pos.CENTER);
        customUpdateAssetsFormGridPane.setHgap(20);
        customUpdateAssetsFormGridPane.setVgap(20);

        customUpdateAssetsSubmitButton.setOnAction(_ -> {
            if ((selectTeamComboBox.getValue() == null) || (valueTextField.getText().isEmpty())) {
                PopUp.display("Empty Fields", "Empty Fields\nMake Sure You Have Filled All Fields", true);
            } else {
                switch (selectOperationTypeComboBox.getValue()) {
                    case "Add Balance":
                        addBalance(selectTeamComboBox.getValue(), Double.parseDouble(valueTextField.getText()));
                        break;
                    case "Minus Balance":
                        subtractBalance(selectTeamComboBox.getValue(), Double.parseDouble(valueTextField.getText()));
                        break;
                    case "Add Percent to Share":
                        addPercentToSharePrice(selectTeamComboBox.getValue(), Integer.parseInt(valueTextField.getText()));
                        break;
                    case "Minus Percent from Share":
                        subtractPercentFromSharePrice(selectTeamComboBox.getValue(), Integer.parseInt(valueTextField.getText()));
                        break;
                }
                valueTextField.clear();
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
                PopUp.display("Error Adding Balance", "Selected \"Team\" not found", true);
                break;
            case 2:
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
                PopUp.display("Error Subtracting Balance", "Selected \"Team\" not found", true);
                break;
            case 2:
                PopUp.display("Error Subtracting Balance", "Invalid Amount", true);
                break;
            case 3:
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
        showCustomUpdateAssetsFormScene();
        showEventConsequencesScene();
        setScene();
        return updateAssestsValuesScene;
    }
}
