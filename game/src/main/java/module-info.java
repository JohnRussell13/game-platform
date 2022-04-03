module com.goldrush {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.goldrush to javafx.fxml;
    exports com.goldrush;
}
