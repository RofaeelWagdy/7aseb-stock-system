package frontend.scenes;

import backend.Main;
import frontend.Constants;
import frontend.helpingClasses.NavigationBar;
import frontend.helpingClasses.PopUp;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateTeamScene {
    private Scene createTeamScene;

    private NavigationBar navBar;
    private VBox mainContainer;

    private HBox createTeamFormHBox;
    private VBox rootLayout;
    private Button createTeamSubmitButton;


    private void layoutInitializer() {
        mainContainer = new VBox();
        rootLayout = new VBox();
        createTeamFormHBox = new HBox();
        navBar = new NavigationBar();
        createTeamSubmitButton = new Button("Create Team");
    }

    private void layoutOrganizer() {
        mainContainer.getChildren().addAll(navBar.getTitleBar(), rootLayout);
        mainContainer.setSpacing(200);

        rootLayout.getChildren().addAll(createTeamFormHBox, createTeamSubmitButton);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setSpacing(30);
    }

    private void setScene() {
        createTeamScene = new Scene(mainContainer, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
    }

    private void showCreateTeamForm() {
        Label teamNameLabel = new Label("Team Name");

        TextField teamNameTextField = new TextField();
        teamNameTextField.setPromptText("Team Name");

        createTeamFormHBox.getChildren().addAll(teamNameLabel, teamNameTextField);
        createTeamFormHBox.setSpacing(20);
        createTeamFormHBox.setAlignment(Pos.CENTER);

        createTeamSubmitButton.setOnAction(_ -> {
            if (teamNameTextField.getText().isEmpty()) {
                PopUp.display("Enter Team Name", "Please Enter Team Name", true);
            } else {
                createTeam(teamNameTextField.getText());
                teamNameTextField.clear();
            }
        });
    }

    private void createTeam(String teamName) {
        Main.createTeamFromFrontEnd(teamName);
        Main.saveTeamsToFileFromFrontEnd();
        Main.saveSharesToFileFromFrontEnd();
        PopUp.display("Team Created", "Team Created Successfully", false);
    }

    public Scene getScene() {
//        this.mainWindow = mainWindow;
        layoutInitializer();
        layoutOrganizer();
        setScene();
        showCreateTeamForm();
        return createTeamScene;
    }
}
