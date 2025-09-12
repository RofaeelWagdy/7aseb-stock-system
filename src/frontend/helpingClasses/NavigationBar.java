package frontend.helpingClasses;

import frontend.MainWindow;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NavigationBar {
    private final VBox rootLayout;

    public NavigationBar() {
        this.rootLayout = new VBox();
        setupNavigationBar();
    }

    private void setupNavigationBar(){
        Label stockMarketTitle = new Label("7aseb Stock Market System");
        stockMarketTitle.getStyleClass().add("stock-market-title");

        Button createTeamButton = new Button("Create Team");
        createTeamButton.setOnAction(_ -> MainWindow.showCreateTeamScene());
        Button makeSharesTransactionsButton = new Button("Buy/Sell Shares");
        makeSharesTransactionsButton.setOnAction(_ -> MainWindow.showSharesTransactionScene());
        Button stockMarketButton = new Button("Show Stocks Market");
        stockMarketButton.setOnAction(_ -> MainWindow.showStockMarketScene());
        Button updateAssetsButton = new Button("Update Assets");
        updateAssetsButton.setOnAction(_ -> MainWindow.showUpdateAssetsScene());
        Button showSpecificTeamDetailsButton = new Button("Show Bought Shares");
        showSpecificTeamDetailsButton.setOnAction(_ -> MainWindow.showSpecificTeamDetailsScene());

        HBox buttonsLayout = new HBox(20);
        buttonsLayout.getChildren().addAll(stockMarketButton, makeSharesTransactionsButton, updateAssetsButton, createTeamButton, showSpecificTeamDetailsButton);
        buttonsLayout.setAlignment(Pos.CENTER);

        rootLayout.getChildren().addAll(stockMarketTitle,buttonsLayout);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setSpacing(30);
    }

    public VBox getTitleBar(){
        return rootLayout;
    }

}
