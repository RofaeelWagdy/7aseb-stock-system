package frontend.scenes;

import backend.Main;
import backend.Share;
import backend.Team;
import frontend.Constants;
import frontend.helpingClasses.NavigationBar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ShowBoughtSharesScene implements Constants{
    private Scene boughtSharesScene;

    private NavigationBar navBar;
    private VBox mainContainer;

    private VBox rootLayout;
    private HBox chooseTeamLayout;
    private TableView<Share> teamDetailsTable;
    private TableColumn<Share, String> fromTeamNameColumn;
    private TableColumn<Share, Integer> shareQuantityColumn;
    private TableColumn<Share, Double> singleSharePriceColumn;
    private TableColumn<Share, Double> totalSharePriceColumn;

    private void layoutInitializer(){
        navBar = new NavigationBar();
        mainContainer = new VBox();
        rootLayout = new VBox();
        chooseTeamLayout = new HBox();
        teamDetailsTable = new TableView<>();
        fromTeamNameColumn = new TableColumn<>("\"From Team\" Name");
        shareQuantityColumn = new TableColumn<>("Share Quantity");
        singleSharePriceColumn = new TableColumn<>("Single Share Price");
        totalSharePriceColumn = new TableColumn<>("Total Share Price");
    }

    private void layoutOrganizer(){
        mainContainer.getChildren().addAll(navBar.getTitleBar(), rootLayout);
        mainContainer.setSpacing(50);

        rootLayout.getChildren().addAll(chooseTeamLayout, teamDetailsTable);
        rootLayout.setSpacing(20);
    }

    private void setScene(){
        boughtSharesScene = new Scene(mainContainer, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        boughtSharesScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }

    private void showChooseBuyerTeamLayout(){
        Label chooseTeamLabel = new Label("Choose Team");

        ObservableList<Team> selectTeamObservableList = FXCollections.observableArrayList();
        selectTeamObservableList.addAll(Main.getTeamsFromFrontEnd());
        ChoiceBox<Team> selectTeamComboBox = new ChoiceBox<>(selectTeamObservableList);
        selectTeamComboBox.setMinWidth(250);

        Button chooseTeamSubmitButton = new Button("Submit");
        chooseTeamSubmitButton.setOnAction(_ -> {
            Team selectedTeam = selectTeamComboBox.getValue();
            showBoughtSharesDetailsTable(selectedTeam);
        });

        chooseTeamLayout.getChildren().addAll(chooseTeamLabel, selectTeamComboBox, chooseTeamSubmitButton);
        chooseTeamLayout.setSpacing(20);
        chooseTeamLayout.setAlignment(Pos.CENTER);
    }

    private void showBoughtSharesDetailsTable(Team team){
        teamDetailsTable.getItems().clear();
        teamDetailsTable.getColumns().clear();

        ObservableList<Share> boughtSharesFromBackend = FXCollections.observableArrayList();
        boughtSharesFromBackend.addAll(Main.getBoughtSharesFromBackend(team));

        double columnWidth = (Constants.SCENE_WIDTH - 20) / 4;

        fromTeamNameColumn.setPrefWidth(columnWidth);
        fromTeamNameColumn.setCellValueFactory(new PropertyValueFactory<>("from_team"));

        shareQuantityColumn.setPrefWidth(columnWidth);
        shareQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        singleSharePriceColumn.setPrefWidth(columnWidth);
        singleSharePriceColumn.setCellValueFactory(new PropertyValueFactory<>("single_share_price"));

        totalSharePriceColumn.setPrefWidth(columnWidth);
        totalSharePriceColumn.setCellValueFactory(new PropertyValueFactory<>("total_share_price"));

        teamDetailsTable.setItems(boughtSharesFromBackend);
        teamDetailsTable.getStyleClass().add("stock-market-table");
        teamDetailsTable.getColumns().addAll(fromTeamNameColumn, shareQuantityColumn, singleSharePriceColumn, totalSharePriceColumn);

        teamDetailsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public Scene getScene(){
        layoutInitializer();
        layoutOrganizer();
        setScene();
        showChooseBuyerTeamLayout();
//        showBoughtSharesDetailsTable();
        return boughtSharesScene;
    }
}
