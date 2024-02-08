package com.company.proyectopgv.utils;


import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class Utils {

    public void llenadoListaProcesos() {

    }
    
    public String formatBytesToDoubleJasper(long bytes) {
        double kilobytes = bytes / 1024.0;
        double megabytes = kilobytes / 1024.0;
        double gigabytes = megabytes / 1024.0;

        return String.format("%.2f", gigabytes);
    }
    

    public String formatBytes(long bytes) {
        double kilobytes = bytes / 1024.0;
        double megabytes = kilobytes / 1024.0;
        double gigabytes = megabytes / 1024.0;

        return String.format("%.2f GB", gigabytes);
    }
    
    public Double formatBytesMega(long bytes) {
        double kilobytes = bytes / 1024.0;
        double megabytes = kilobytes / 1024.0;
        return megabytes;
    }
    
    public Double formatBytesToouble(long bytes) {
        double kilobytes = bytes / 1024.0;
        double megabytes = kilobytes / 1024.0;
        double gigabytes = megabytes / 1024.0;

        return gigabytes;
    }
    
    public String formatBytesKb(long bytes) {
        double kilobytes = bytes / 1024.0;
        return String.format("%.2f KB", kilobytes);
    }

    public String formatPercentage(double percentageUsed) {
        return String.format("%.0f%%", percentageUsed);
    }

    public void actualizarGraficaUsoRAM(XYChart.Series series, String contador, double valor) {

        Platform.runLater(() -> {
            series.getData().add(new XYChart.Data(contador, valor));
        });
    }

    public void actualizarGraficaUsoCPU(XYChart.Series series, String contador, double valor) {

        Platform.runLater(() -> {
            series.getData().add(new XYChart.Data(contador, valor));
        });
    }
    
    /*public void actualizarGraficaDisco(XYChart.Series series, String nombre, double lectura) {

         Platform.runLater(() -> {
        // Verificar si la serie ya existe para este disco
        boolean serieExiste = false;

             for (Iterator it = series.getData().iterator(); it.hasNext();) {
                 XYChart.Data data = (XYChart.Data) it.next();
                 if (data.getXValue().equals(nombre)) {
                     // Actualizar el valor de lectura para este disco
                     data.setYValue(lectura);
                     serieExiste = true;
                     break;
                 }}

        // Si no existe una serie para este disco, crear una nueva
        if (!serieExiste) {
            series.getData().add(new XYChart.Data(nombre, lectura));
        }
    });
    }*/
    
    public void actualizarGraficaRed(XYChart.Series series, String valor, long velocidadRed) {

        Platform.runLater(() -> {
            series.getData().add(new XYChart.Data(valor, velocidadRed));
        });
        
    }

    public void actualizarPieRAM(PieChart pieChart, long usedMemory, long availableMemory) {

        Platform.runLater(() -> {
            pieChart.getData().get(0).nameProperty().bind(Bindings.concat("Memoria ocupada: ", " ", formatBytes(usedMemory)));
            pieChart.getData().get(1).nameProperty().bind(Bindings.concat("Memoria disponible: ", " ", formatBytes(availableMemory)));
        });
    }
}
