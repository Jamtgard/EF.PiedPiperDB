module com.example.piedpiperdb {
    requires javafx.controls;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens com.example.piedpiperdb.Entities to org.hibernate.orm.core;
//    exports com.example.piedpiperdb;
    exports com.example.piedpiperdb.View;
}