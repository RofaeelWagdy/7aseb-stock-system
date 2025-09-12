package frontend;

import backend.Main;
import frontend.helpingClasses.ConfirmBox;
import frontend.helpingClasses.PopUp;
import frontend.scenes.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainWindow extends Application implements Constants {

    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        mainStage = window;
        mainStage.setTitle("7aseb Stock System");
        mainStage.setOnCloseRequest(e -> {
            e.consume();
            if (ConfirmBox.display("Exit", "Are You Sure U Want to Exit ?")) {
                Main.saveTeamsToFileFromFrontEnd();
                Main.saveSharesToFileFromFrontEnd();
                window.close();
            }
        });

        if (!Main.loadTeamsFromFileFromFrontEnd())
            PopUp.display("Error", "Error loading teams from file", true);
        if (!Main.loadSharesFromFileFromFrontEnd())
            PopUp.display("Error", "Error loading shares from file", true);

        StockMarketTableScene stockMarketTableScene = new StockMarketTableScene();
        window.setScene(stockMarketTableScene.getScene());
        window.show();
    }


    public static void showCreateTeamScene() {
        CreateTeamScene createTeamScene = new CreateTeamScene();
        mainStage.setScene(createTeamScene.getScene());
        mainStage.show();
    }

    public static void showStockMarketScene() {
        StockMarketTableScene stockMarketTableScene = new StockMarketTableScene();
        mainStage.setScene(stockMarketTableScene.getScene());
        mainStage.show();
    }

    public static void showUpdateAssetsScene() {
        UpdateAssetsValuesScene updateAssetsValuesScene = new UpdateAssetsValuesScene();
        mainStage.setScene(updateAssetsValuesScene.getScene());
        mainStage.show();
    }

    public static void showSharesTransactionScene() {
        SharesTransactionScene sharesTransactionScene = new SharesTransactionScene();
        mainStage.setScene(sharesTransactionScene.getScene());
        mainStage.show();
    }

    public static void showSpecificTeamDetailsScene() {
        ShowBoughtSharesScene showBoughtSharesScene = new ShowBoughtSharesScene();
        mainStage.setScene(showBoughtSharesScene.getScene());
        mainStage.show();
    }
}
