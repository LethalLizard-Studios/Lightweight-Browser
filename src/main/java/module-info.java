module net.lelandcarter.testfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.json;

    opens net.lelandcarter.lightweightBrowser to javafx.fxml;
    exports net.lelandcarter.lightweightBrowser;
}