package com.company.proyectopgv.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.software.os.OSProcess;
import oshi.software.os.OSService;

public class PrimaryController implements Initializable {

    @FXML
    private Label osLabel;
    @FXML
    private Label motherBoardLabel;
    @FXML
    private Label cpuLabel;
    @FXML
    private Label ramLabel;
    @FXML
    private Label discoDuroLabel;
    @FXML
    private TableView<OSProcess> tableViewProcess;
    @FXML
    private LineChart<?, ?> cpuGrafic;
    @FXML
    private Label totalMemoryRam;
    @FXML
    private Label totalUsageRam;
    @FXML
    private Label totalAvailableRam;
    @FXML
    private Label minUsageRam;
    @FXML
    private Label maxUsageRam;
    @FXML
    private PieChart pieDeUsoGrafic;
    @FXML
    private LineChart<?, ?> usoDeRamGrafic;
    @FXML
    private TableView<?> diskProcessActivityTable;
    @FXML
    private AreaChart<?, ?> diskActivityGrafic;
    @FXML
    private TableView<?> storageTable;
    @FXML
    private LineChart<?, ?> usageRedGrafic;
    @FXML
    private TableView<OSService> tableViewServices;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        SystemInfo sistema = new SystemInfo();
        List<HWDiskStore> discos = sistema.getHardware().getDiskStores();
        StringBuilder discosString = new StringBuilder();
        StringBuilder logicProssString = new StringBuilder();
        StringBuilder physicProssString = new StringBuilder();

        
        
        
        /**/
        //Lista de procesos
        List<OSProcess> procesos = sistema.getOperatingSystem().getProcesses();
        ObservableList<OSProcess> observableProcesos = FXCollections.observableArrayList(procesos);
        tableViewProcess.setItems(observableProcesos);

        TableColumn<OSProcess, String> pidColumn = new TableColumn<>("PID");
        pidColumn.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getProcessID())));

        TableColumn<OSProcess, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<OSProcess, String> stateColumn = new TableColumn<>("Estado");
        stateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getState().toString()));

        TableColumn<OSProcess, String> priorityColumn = new TableColumn<>("Prioridad");
        priorityColumn.setCellValueFactory(data -> {
            return new SimpleStringProperty(Integer.toString(data.getValue().getPriority()));
        });

        tableViewProcess.getColumns().addAll(nameColumn, pidColumn, stateColumn, priorityColumn);
        /**/
        
        //Lista de servicios
        /**/
        List<OSService> servicios = sistema.getOperatingSystem().getServices();
        ObservableList<OSService> observableServicios = FXCollections.observableArrayList(servicios);
        tableViewServices.setItems(observableServicios);

        TableColumn<OSService, String> nameServiceColumn = new TableColumn<>("Nombre");
        nameServiceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<OSService, String> servicePIDNameColumn = new TableColumn<>("PID");
        servicePIDNameColumn.setCellValueFactory(data -> {
        
            return new SimpleStringProperty(Integer.toString(data.getValue().getProcessID()));
        });

        TableColumn<OSService, String> serviceStateColumn = new TableColumn<>("Estado del servicio");
        serviceStateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getState().toString()));

        tableViewServices.getColumns().addAll(nameServiceColumn, servicePIDNameColumn, serviceStateColumn);
        /**/
        
        discos.forEach((disk) -> {
            discosString.append(disk.getModel() + " " + disk.getSize() + "\n");
        });

        osLabel.setText(sistema.getOperatingSystem().toString());
        motherBoardLabel.setText(sistema.getHardware().getComputerSystem().getBaseboard().getManufacturer() + " "
                + sistema.getHardware().getComputerSystem().getBaseboard().getModel() + " "
                + sistema.getHardware().getComputerSystem().getBaseboard().getSerialNumber());
        cpuLabel.setText(sistema.getHardware().getProcessor().toString());
        ramLabel.setText(sistema.getHardware().getMemory().toString());
        discoDuroLabel.setText(discosString.toString());

    }

}
