package com.company.proyectopgv.utils;

/*import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.view.JasperViewer;



public class Reporte {

    public static void generarReporte(String datosPath, String jasperPath) {

        try {
            JRCsvDataSource dataSource = new JRCsvDataSource(datosPath, "Windows-1252");
            dataSource.setFieldDelimiter(',');
            dataSource.setUseFirstRowAsHeader(true);

            JasperReport reporte = JasperCompileManager.compileReport(jasperPath);

            HashMap<String, Object> parametros = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, dataSource);

            JasperViewer.viewReport(jasperPrint, false);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/Reporte.pdf");

        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

    }

}*/
