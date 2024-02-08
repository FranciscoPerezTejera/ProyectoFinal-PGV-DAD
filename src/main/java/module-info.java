module com.company.proyectopgv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.github.oshi;
    
    opens com.company.proyectopgv to javafx.fxml;
    opens com.company.proyectopgv.controller to javafx.fxml;
    
    exports com.company.proyectopgv;
    exports com.company.proyectopgv.controller to javafx.fxml;
    

}
