package frontend.scenes;

import backend.Main;
import backend.Team;
import frontend.Constants;
import frontend.helpingClasses.NavigationBar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


public class StockMarketTableScene implements Constants {
    private Scene stockMarketTableScene;

    private NavigationBar navBar;
    private VBox mainContainer;

    private TableView<Team> stockMarketTable;
    private TableColumn<Team, String> teamNameColumn;
    private TableColumn<Team, Double> sharePriceColumn;
    private TableColumn<Team, Double> balanceColumn;
    private TableColumn<Team, Double> totalAssetsColumn;

    private ObservableList<Team> teamsFromBackEnd;
    private void layoutInitializer(){
        navBar = new NavigationBar();
        mainContainer = new VBox();
        stockMarketTable = new TableView<>();
        teamNameColumn = new TableColumn<>("Team Name");
        sharePriceColumn = new TableColumn<>("Share Price");
        balanceColumn = new TableColumn<>("Balance");
        totalAssetsColumn = new TableColumn<>("Total Assets");
    }
    private void layoutOrganizer(){
        mainContainer.getChildren().addAll(navBar.getTitleBar(), stockMarketTable);
        mainContainer.setSpacing(50);

    }
    private void setScene(){
        stockMarketTableScene = new Scene(mainContainer, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
    }
    private void tableOrganizer(){
        teamsFromBackEnd = FXCollections.observableArrayList();
        teamsFromBackEnd.addAll(Main.getTeamsFromFrontEnd());

        teamNameColumn.setMinWidth(200);
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("team_name"));

        sharePriceColumn.setMinWidth(200);
        sharePriceColumn.setCellValueFactory(new PropertyValueFactory<>("self_share_price"));

        balanceColumn.setMinWidth(200);
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        totalAssetsColumn.setMinWidth(200);
        totalAssetsColumn.setCellValueFactory(new PropertyValueFactory<>("total_assets"));

        stockMarketTable.setItems(teamsFromBackEnd);
        stockMarketTable.getColumns().addAll(teamNameColumn, sharePriceColumn, balanceColumn, totalAssetsColumn);
    }

    public Scene getScene(){
        layoutInitializer();
        layoutOrganizer();
        setScene();
        tableOrganizer();
        return stockMarketTableScene;
    }
}


