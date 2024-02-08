package com.company.proyectopgv.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ParseadorCSV {

    public static void guardarDatosEnCsv(String data) {

        FileWriter writer = null;
        String encabezado = "SistemaOperativo,PlacaBase,CPU,TemperaturaCPU,MemoriaRAM,MemoriaRAMtotal,MemoriaRAMdisponible,MemoriaRAMEnUso,UsedMemory,AvailableMemory";
        try {
            String path = "src/main/java/com/company/proyectopgv/csv/datos.csv";
            File csv = new File(path);
            if (csv.exists()) {
                csv.delete();
            }
            csv.createNewFile();
            writer = new FileWriter(csv);
            writer.write(encabezado);
            writer.write("\n");
            writer.write(data);
            writer.flush();
            writer.close();
            
            //Reporte reporte = new Reporte();
            //reporte.generarReporte("src/main/java/com/company/proyectopgv/csv/datos.csv", "src/main/java/com/company/proyectopgv/csv/informe_DAD_PGV.jrxml");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
