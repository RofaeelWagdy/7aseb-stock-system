module stocksystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.logging;
    requires transitive javafx.base;
    requires jdk.unsupported.desktop;

    exports frontend;
    exports backend;
    exports frontend.scenes;
    exports frontend.helpingClasses;
}