package com.company.proyectopgv.controller;

import com.company.proyectopgv.utils.ParseadorCSV;
import com.company.proyectopgv.utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.SoundCard;
import oshi.hardware.UsbDevice;
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
    private LineChart<Number, Number> cpuGrafic;
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
    private LineChart<Number, Number> usoDeRamGrafic;
    @FXML
    private TableView<HWDiskStore> diskProcessActivityTable;
    @FXML
    private AreaChart<Number, Number> diskActivityGrafic;
    @FXML
    private LineChart<Number, Number> usageRedGrafic;
    @FXML
    private TableView<OSService> tableViewServices;
    @FXML
    private Label tarjetaGraficaLabel;
    @FXML
    private Label tarjetaSonidoLabel;
    @FXML
    private TableView<UsbDevice> dispositivosUsbTable;
    @FXML
    private NumberAxis yMemory;
    @FXML
    private CategoryAxis xMemory;
    @FXML
    private Label temperaturaCPU;
    @FXML
    private Button busquedaDeServidores;

    private Utils utilidad;
    private SystemInfo sistema;
    private long totalMemory;
    private long availableMemory;
    private long usedMemory;
    private double valorMinimo;
    private double valorMaximo;
    private boolean inicializado;
    private int numeroDeGraficas;
    private double porcentajeUsoCPU;
    private long velocidadRed;
    private XYChart.Series seriesRam;
    private XYChart.Series seriesCPU;
    private XYChart.Series seriesRed;
    private long lectura;
    private long escritura;
    private StringBuilder discosString;
    private StringBuilder graphicCardString;
    private StringBuilder tarjetaSonidoString;
    private List<HWDiskStore> disks;
    List<XYChart.Series> discosSeries;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        utilidad = new Utils();
        sistema = new SystemInfo();
        inicializado = false;
        discosSeries = new ArrayList<>();
        numeroDeGraficas = 1;
        actualizarInformacionMemoria(inicializado);
        datosProcesos();
        datosServicios();
        datosDeDiscos();
        datosUSB();
        cargarPieDeMemoria();
        cargarXYChart();
        llenadoDatosEstaticos();

        Thread monitoreo = new Thread(() -> {
            while (true) {
                try {
                    Platform.runLater(() -> {

                        totalMemoryRam.setText(utilidad.formatBytes(totalMemory));
                        totalUsageRam.setText(utilidad.formatBytes(usedMemory));
                        totalAvailableRam.setText(utilidad.formatBytes(availableMemory));
                        minUsageRam.setText(utilidad.formatPercentage(valorMinimo));
                        maxUsageRam.setText(utilidad.formatPercentage(valorMaximo));
                        porcentajeUsoCPU = sistema.getHardware().getProcessor().getSystemCpuLoad(500) * 100;
                        refrescoDeDatosProceso();
                        refrescoDatosDeServicio();
                        refrescoDatosDeDiscos();
                    }
                    );

                    Thread.sleep(5000);

                } catch (InterruptedException ex) {
                    System.out.println("");
                }
                numeroDeGraficas++;
                actualizarInformacionMemoria(inicializado);
                utilidad.actualizarPieRAM(pieDeUsoGrafic, usedMemory, availableMemory);
                utilidad.actualizarGraficaUsoRAM(seriesRam, String.valueOf(numeroDeGraficas), utilidad.formatBytesToouble(usedMemory));
                utilidad.actualizarGraficaUsoCPU(seriesCPU, String.valueOf(numeroDeGraficas), (int) porcentajeUsoCPU);
                //utilidad.actualizarGraficaDisco(seriesDiscos, String.valueOf(numeroDeGraficas), lectura);
                //utilidad.actualizarGraficaRed(seriesRed, String.valueOf(numeroDeGraficas), velocidadRed);
            }
        }
        );
        monitoreo.setDaemon(true);
        monitoreo.start();
    }

    @FXML
    private void onSearchServer(ActionEvent event) {

    }

    @FXML
    private void onClickReporte(ActionEvent event) {
        
        String cpu = cpuLabel.getText();
        String memoria = ramLabel.getText();
        String cpuString = cpu.substring(cpu.indexOf("Inte") + 0,  cpu.indexOf("\n"));
        String memoriaString = memoria.replace(",", ".");

        String datosToCsv =  osLabel.getText() + ","
                + motherBoardLabel.getText() + ","
                + cpuString + ","
                + temperaturaCPU.getText() + ","
                + memoriaString + ","
                + totalMemoryRam.getText().replace(",", ".") + ","
                + totalAvailableRam.getText().replace(",", ".") + ","
                + totalUsageRam.getText().replace(",", ".") + ","
                + utilidad.formatBytesToDoubleJasper(usedMemory).replace(",", ".") + ","
                + utilidad.formatBytesToDoubleJasper(availableMemory).replace(",", ".");

        System.out.println(datosToCsv);
        ParseadorCSV.guardarDatosEnCsv(datosToCsv);

    }

    private void actualizarInformacionMemoria(boolean ini) {

        GlobalMemory memory = sistema.getHardware().getMemory();

        totalMemory = memory.getTotal();
        availableMemory = memory.getAvailable();
        usedMemory = totalMemory - availableMemory;
        double percentageUsed = (usedMemory * 100.0) / totalMemory;

        if (!inicializado) {
            valorMinimo = percentageUsed;
            valorMaximo = percentageUsed;
            inicializado = true;

        } else {
            if (valorMinimo > percentageUsed) {
                valorMinimo = percentageUsed;
            }
            if (valorMaximo < percentageUsed) {
                valorMaximo = percentageUsed;
            }
        }
    }

    private void datosProcesos() {

        refrescoDeDatosProceso();

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
        tableViewProcess.getSortOrder().add(nameColumn);
    }

    private void refrescoDeDatosProceso() {
        tableViewProcess.getItems().clear();
        List<OSProcess> procesos = sistema.getOperatingSystem().getProcesses();
        ObservableList<OSProcess> observableProcesos = FXCollections.observableArrayList(procesos);
        observableProcesos.sort(Comparator.comparing(oSProcess -> oSProcess.getPriority()));
        tableViewProcess.setItems(observableProcesos);
    }

    private void datosServicios() {

        refrescoDatosDeServicio();

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
        tableViewServices.getSortOrder().add(nameServiceColumn);
    }

    private void refrescoDatosDeServicio() {
        tableViewServices.getItems().clear();
        List<OSService> servicios = sistema.getOperatingSystem().getServices();
        ObservableList<OSService> observableServicios = FXCollections.observableArrayList(servicios);
        observableServicios.sort(Comparator.comparing(osService -> osService.getProcessID()));
        tableViewServices.setItems(observableServicios);
    }

    private void datosUSB() {

        List<UsbDevice> dispositivosUSB = sistema.getHardware().getUsbDevices(false);
        ObservableList<UsbDevice> observableUSBdevice = FXCollections.observableArrayList(dispositivosUSB);

        TableColumn<UsbDevice, String> nameUSBdevice = new TableColumn<>("Nombre");
        nameUSBdevice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<UsbDevice, String> idproducto = new TableColumn<>("Id producto");
        idproducto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductId()));

        TableColumn<UsbDevice, String> numeroSerial = new TableColumn<>("Número serial");
        numeroSerial.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSerialNumber()));

        TableColumn<UsbDevice, String> vendor = new TableColumn<>("Vendor");
        vendor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVendor()));

        dispositivosUsbTable.getColumns().addAll(nameUSBdevice, idproducto, numeroSerial, vendor);

    }

    private StringBuilder listaDeDiscos() {

        StringBuilder string = new StringBuilder();

        List<HWDiskStore> discos = sistema.getHardware().getDiskStores();

        discos.forEach((disk) -> {
            string.append(disk.getModel() + " " + utilidad.formatBytes(disk.getSize()) + "\n");
        });
        return string;
    }

    private StringBuilder listaDeTarjetasGraficas() {

        StringBuilder string = new StringBuilder();

        List<GraphicsCard> graphCard = sistema.getHardware().getGraphicsCards();

        graphCard.forEach((card) -> {
            string.append(card.getName() + " " + utilidad.formatBytes(card.getVRam()) + "\n");
        });
        return string;
    }

    private StringBuilder listaDeTarjetasSonido() {

        StringBuilder string = new StringBuilder();

        List<SoundCard> soundCard = sistema.getHardware().getSoundCards();

        soundCard.forEach((sCard) -> {
            string.append(sCard.getName() + " " + sCard.getCodec() + "\n");
        });
        return string;

    }

    private void cargarPieDeMemoria() {

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Memoria ocupada: ", usedMemory),
                new PieChart.Data("Memoria disponible: ", availableMemory)
        );
        pieData.forEach(data -> {
            data.nameProperty().bind(Bindings.concat(data.getName(), " ", utilidad.formatBytes(data.pieValueProperty().longValue())));
        });
        pieDeUsoGrafic.setData(pieData);

    }

    private void cargarXYChart() {

        /*Ejes de gráfica de Uso de RAM*/
        yMemory.setLabel("Porcentaje de RAM");
        seriesRam = new XYChart.Series();
        seriesRam.setName("Uso de la RAM");
        seriesRam.getData().add(new XYChart.Data(String.valueOf(numeroDeGraficas), utilidad.formatBytesToouble(usedMemory)));
        usoDeRamGrafic.getData().add(seriesRam);

        /*Eles de gráfica de Uso de CPU*/
        HardwareAbstractionLayer hardware = sistema.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        porcentajeUsoCPU = sistema.getHardware().getProcessor().getSystemCpuLoad(1) * 100;
        seriesCPU = new XYChart.Series();
        seriesCPU.setName("Uso de CPU");
        seriesCPU.getData().add(new XYChart.Data(String.valueOf(numeroDeGraficas), porcentajeUsoCPU));
        cpuGrafic.getData().add(seriesCPU);

        /*Ejes de gráfica de Lectura/Escritura Discos*/
 /*seriesDiscos = new XYChart.Series();
        seriesDiscos.setName("Escritura/Lectura de disco");
        seriesDiscos.getData().add(new XYChart.Data(String.valueOf(numeroDeGraficas), lectura));
        seriesDiscos.getData().add(new XYChart.Data(String.valueOf(numeroDeGraficas), escritura));
        diskActivityGrafic.getData().add(seriesDiscos);*/
 /*Ejes de gráfica de Uso de Red*/
        seriesRed = new XYChart.Series();
        seriesRed.setName("Escritura/Lectura de disco");
        seriesRed.getData().add(new XYChart.Data(String.valueOf(numeroDeGraficas), velocidadRed));
        usageRedGrafic.getData().add(seriesRed);

    }

    private void llenadoDatosEstaticos() {

        discosString = listaDeDiscos();
        graphicCardString = listaDeTarjetasGraficas();
        tarjetaSonidoString = listaDeTarjetasSonido();

        totalMemoryRam.setText(utilidad.formatBytes(totalMemory));
        totalAvailableRam.setText(utilidad.formatBytes(availableMemory));
        totalUsageRam.setText(utilidad.formatBytes(usedMemory));
        minUsageRam.setText(utilidad.formatPercentage(valorMinimo));
        maxUsageRam.setText(utilidad.formatPercentage(valorMaximo));

        osLabel.setText(sistema.getOperatingSystem().toString());
        motherBoardLabel.setText(sistema.getHardware().getComputerSystem().getBaseboard().getManufacturer() + " "
                + sistema.getHardware().getComputerSystem().getBaseboard().getModel() + " "
                + sistema.getHardware().getComputerSystem().getBaseboard().getSerialNumber());
        cpuLabel.setText(sistema.getHardware().getProcessor().toString());
        ramLabel.setText(sistema.getHardware().getMemory().toString());
        discoDuroLabel.setText(discosString.toString());
        tarjetaGraficaLabel.setText(graphicCardString.toString());
        tarjetaSonidoLabel.setText(tarjetaSonidoString.toString());
        temperaturaCPU.setText(String.valueOf(sistema.getHardware().getSensors().getCpuTemperature()) + " ºC");
    }

    private void datosDeDiscos() {

        refrescoDatosDeDiscos();

        TableColumn<HWDiskStore, String> name = new TableColumn<>("Nombre");
        name.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModel()));

        TableColumn<HWDiskStore, String> lectura = new TableColumn<>("Lectura/s");
        lectura.setCellValueFactory(data -> {

            return new SimpleStringProperty(utilidad.formatBytesKb(data.getValue().getReadBytes()));
        });

        TableColumn<HWDiskStore, String> escritura = new TableColumn<>("Escritura/s");
        escritura.setCellValueFactory(data -> {
            return new SimpleStringProperty(utilidad.formatBytesKb(data.getValue().getWrites()));
        });

        TableColumn<HWDiskStore, String> tamaño = new TableColumn<>("Tamaño");
        tamaño.setCellValueFactory(data -> {
            return new SimpleStringProperty(utilidad.formatBytes(data.getValue().getSize()));
        });

        TableColumn<HWDiskStore, String> tiempo = new TableColumn<>("Tiempo activo");
        tiempo.setCellValueFactory(data -> {
            return new SimpleStringProperty(String.valueOf(data.getValue().getTransferTime()));
        });

        diskProcessActivityTable.getColumns().addAll(name, lectura, escritura, tamaño, tiempo);
        diskProcessActivityTable.getSortOrder().add(name);

    }

    private void refrescoDatosDeDiscos() {
        diskProcessActivityTable.getItems().clear();
        disks = sistema.getHardware().getDiskStores();

        ObservableList<HWDiskStore> observableProcesosDisco = FXCollections.observableArrayList(disks);
        observableProcesosDisco.sort(Comparator.comparing(osService -> osService.getSize()));
        diskProcessActivityTable.setItems(observableProcesosDisco);

    }
}
