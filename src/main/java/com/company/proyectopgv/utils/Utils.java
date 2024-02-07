package com.company.proyectopgv.utils;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class Utils {

    public void llenadoListaProcesos() {

    }

    public String formatBytes(long bytes) {
        double kilobytes = bytes / 1024.0;
        double megabytes = kilobytes / 1024.0;
        double gigabytes = megabytes / 1024.0;

        return String.format("%.2f GB", gigabytes);
    }

    public Double formatBytesToouble(long bytes) {
        double kilobytes = bytes / 1024.0;
        double megabytes = kilobytes / 1024.0;
        double gigabytes = megabytes / 1024.0;

        return gigabytes;
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

    public void actualizarPieRAM(PieChart pieChart, long usedMemory, long availableMemory) {

        Platform.runLater(() -> {
            pieChart.getData().get(0).nameProperty().bind(Bindings.concat("Memoria ocupada: ", " ", formatBytes(usedMemory)));
            pieChart.getData().get(1).nameProperty().bind(Bindings.concat("Memoria disponible: ", " ", formatBytes(availableMemory)));
        });
    }
}
