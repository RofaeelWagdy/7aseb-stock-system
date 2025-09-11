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
import javafx.scene.layout.VBox;


public class SharesTransactionScene implements Constants {
    private Scene sharesTransactionScene;

    private NavigationBar navBar;
    private VBox mainContainer;

    private VBox rootLayout;
    private GridPane sharesTransactionFormGridPane;
    private Button sharesTransactionSubmitButton;

    private void layoutInitializer() {
        navBar = new NavigationBar();
        mainContainer = new VBox();
        rootLayout = new VBox();
        sharesTransactionFormGridPane = new GridPane();
        sharesTransactionSubmitButton = new Button("Submit");
    }

    private void layoutOrganizer() {
        mainContainer.getChildren().addAll(navBar.getTitleBar(), rootLayout);
        mainContainer.setSpacing(80);

        rootLayout.getChildren().addAll(sharesTransactionFormGridPane, sharesTransactionSubmitButton);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setSpacing(30);
    }

    private void showBuySharesScene() {
        Label transactionTypeLabel = new Label("Transaction Type");
        Label buyerTeamSharesLabel = new Label("Buyer Team");
        Label fromTeamSharesLabel = new Label("From Team");
        Label quantitySharesLabel = new Label("Quantity");

        ObservableList<String> selectTransactionType = FXCollections.observableArrayList();
        selectTransactionType.addAll("Buy", "Sell");
        ComboBox<String> selectTransactionTypeComboBox = new ComboBox<>(selectTransactionType);
        selectTransactionTypeComboBox.getSelectionModel().selectFirst();
        selectTransactionTypeComboBox.setMinWidth(250);


        ObservableList<Team> selectTeam = FXCollections.observableArrayList();
        selectTeam.addAll(Main.getTeamsFromFrontEnd());

        ChoiceBox<Team> selectBuyerTeamComboBox = new ChoiceBox<>(selectTeam);
        selectBuyerTeamComboBox.setMinWidth(250);

        ComboBox<Team> selectFromTeamComboBox = new ComboBox<>(selectTeam);
        selectFromTeamComboBox.setMinWidth(250);

        TextField quantitySharesTextField = new TextField();
        quantitySharesTextField.setPromptText("Quantity");

        sharesTransactionFormGridPane.add(transactionTypeLabel, 0, 0);
        sharesTransactionFormGridPane.add(selectTransactionTypeComboBox, 1, 0);
        sharesTransactionFormGridPane.add(buyerTeamSharesLabel, 0, 1);
        sharesTransactionFormGridPane.add(selectBuyerTeamComboBox, 1, 1);
        sharesTransactionFormGridPane.add(fromTeamSharesLabel, 0, 2);
        sharesTransactionFormGridPane.add(selectFromTeamComboBox, 1, 2);
        sharesTransactionFormGridPane.add(quantitySharesLabel, 0, 3);
        sharesTransactionFormGridPane.add(quantitySharesTextField, 1, 3);
        sharesTransactionFormGridPane.setAlignment(Pos.CENTER);
        sharesTransactionFormGridPane.setHgap(20);
        sharesTransactionFormGridPane.setVgap(20);

        sharesTransactionSubmitButton.setOnAction(_ -> {
            if ((selectBuyerTeamComboBox.getValue() == null) || (selectFromTeamComboBox.getValue() == null) || (quantitySharesTextField.getText().isEmpty())) {
                PopUp.display("Empty Fields", "Empty Fields\nMake Sure You Have Filled All Fields", true);
            } else {
                if (selectTransactionTypeComboBox.getValue().equals("Buy")) {
                    buyShares(selectBuyerTeamComboBox.getValue(), selectFromTeamComboBox.getValue(), Integer.parseInt(quantitySharesTextField.getText()));
                } else {
                    sellShares(selectBuyerTeamComboBox.getValue(), selectFromTeamComboBox.getValue(), Integer.parseInt(quantitySharesTextField.getText()));
                }
                quantitySharesTextField.clear();
            }
        });
    }

    private void buyShares(Team buyerTeam, Team fromTeam, int quantity) {
//        buy shares validations
        switch (Main.buySharesFromFrontEnd(buyerTeam, fromTeam, quantity)) {
            case 0:
                PopUp.display("Shares Bought Successfully", "Shares Bought Successfully", false);
                Main.saveTeamsToFileFromFrontEnd();
                Main.saveSharesToFileFromFrontEnd();
                break;
            case 1:
                PopUp.display("Error Buying Shares", "\"Buyer Team\" not found", true);
                break;
            case 2:
                PopUp.display("Error Buying Shares", "\"From Team\" not found", true);
                break;
            case 3:
                PopUp.display("Error Buying Shares", "\"Buyer Team\" Doesn't Have Enough Balance", true);
                break;
            case 4:
                PopUp.display("Error Buying Shares", "\"From Team\" Doesn't Have Available Shares to Buy From", true);
                break;
            case 5:
                PopUp.display("Error Buying Shares", "You Have Bought From That Team Before\nYou Can Only Buy 20 Shares From Any Team", true);
                break;
            case 6:
                PopUp.display("Error Buying Shares", "You Can Only Buy 20 Shares From Any Team", true);
                break;
        }
    }

    private void sellShares(Team buyerTeam, Team fromTeam, int quantity) {
        switch (Main.sellSharesFromFrontEnd(buyerTeam, fromTeam, quantity)) {
            case 0:
                PopUp.display("Shares Sold Successfully", "Shares Sold Successfully", false);
                Main.saveTeamsToFileFromFrontEnd();
                Main.saveSharesToFileFromFrontEnd();
                break;
            case 1:
                PopUp.display("Error Selling Shares", "\"Buyer Team\" not found", true);
                break;
            case 2:
                PopUp.display("Error Selling Shares", "\"From Team\" not found", true);
                break;
            case 3:
                PopUp.display("Error Selling Shares", "This \"Buyer Team\" Hasn't Bought Any Shares From This \"From Team\"", true);
                break;
            case 4:
                PopUp.display("Error Selling Shares", "The Entered Quantity is More Than The Bought Quantity Of Shares", true);
                break;
        }
    }

    private void setScene() {
        sharesTransactionScene = new Scene(mainContainer, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        sharesTransactionScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }


    public Scene getScene() {
        layoutInitializer();
        layoutOrganizer();
        setScene();
        showBuySharesScene();
        return sharesTransactionScene;
    }
}
