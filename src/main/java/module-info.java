module com.lukas.zoohandlungfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lukas.zoohandlungfx to javafx.fxml;
    exports com.lukas.zoohandlungfx;
}