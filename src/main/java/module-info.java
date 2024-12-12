module com.example.piedpiperdb {
    requires javafx.controls;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.desktop;

    //GEFP-19-AA (la till javafx.base
    opens com.example.piedpiperdb.Entities to org.hibernate.orm.core, javafx.base;
//    exports com.example.piedpiperdb;
    exports com.example.piedpiperdb.View;
}