package com.company.proyectopgv.utils;

import javafx.scene.control.TableColumn;


public class LlenadoDeDatos {
    
    public void llenadoListaProcesos() {
    
        
    }
    public String formatBytes(long bytes) {
        double kilobytes = bytes / 1024.0;
        double megabytes = kilobytes / 1024.0;
        double gigabytes = megabytes / 1024.0;

        return String.format("%.2f GB", gigabytes);
    }

    public String formatPercentage(double percentageUsed) {
        return String.format("%.0f%%", percentageUsed);
    }
    
    
}
