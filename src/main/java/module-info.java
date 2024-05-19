module com.oopporject.railsheba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;
    requires com.jfoenix;

    opens com.oopporject.railsheba to javafx.fxml;
    exports com.oopporject.railsheba;
    opens com.oopporject.railsheba.controller.auth to javafx.fxml;
    exports com.oopporject.railsheba.controller.auth;
    exports com.oopporject.railsheba.model;
    exports com.oopporject.railsheba.sql;
    exports com.oopporject.railsheba.email;
    // exports com.oopporject.railsheba.controller.auth
    // exports com.oopporject.railsheba.controller.admin;
    exports com.oopporject.railsheba.controller.user;
    opens com.oopporject.railsheba.controller.user to javafx.fxml;
    exports com.oopporject.railsheba.controller.admin;
    opens com.oopporject.railsheba.controller.admin to javafx.fxml;
}