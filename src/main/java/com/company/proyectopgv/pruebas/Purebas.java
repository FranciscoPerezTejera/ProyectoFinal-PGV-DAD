package com.company.proyectopgv.pruebas;

import oshi.SystemInfo;

public class Purebas {
    public static void main(String[] args) {
        SystemInfo sistema = new SystemInfo();
       /*List <UsbDevice> procesos = sistema.getHardware().get;
        procesos.forEach((e) -> {
            System.out.println(e.toString());
        });*/
        System.out.println(sistema.getOperatingSystem());
/*  
     SystemInfo sistema = new SystemInfo();
        
    sistema.getOperatingSystem().getNetworkParams(): devuelve los datos de RED del equipo
    sistema.getOperatingSystem(): devuelve el sistema operativo del equipo
    sistema.getOperatingSystem().getProcesses(): devuelve una lista de todos los procesos
        del equipo
    sistema.getOperatingSystem().getServices(): devuelve una lista de todos los servicios
        del equipo
    sistema.getHardware().getComputerSystem().getBaseboard(): devuelve un objeto Baseboard
        que nos da la información de la placa
    sistema.getHardware().getDiskStores(): devuelve una lista de los discos duros detectados
        en el equipo
    sistema.getHardware().getDisplays(): devuelve una lista de pantallas conectadas al equipo
    sistema.getHardware().getGraphicsCards(): devuelve una lista de tarjetas gráficas instaladas
        en el equipo
    sistema.getHardware().getNetworkIFs(): devuelve una lista de interfaces de red 
        (Network Interface) disponibles en el sistema
    sistema.getHardware().getPowerSources(): devuelve una lista con información sobre las 
        fuentes de alimentación del sistema
    sistema.getHardware().getSoundCards(): develve una lista con información de tarjetas de 
        sonido del equipo
    sistema.getHardware().getUsbDevices(false): devuelve una lista de los dispositivos USB
        conectados al equipo. Si pones true en ves de false te devuelve la misma lista pero
        en formato tree (árbol).
    sistema.getHardware().getMemory(): devuelve toda la información de la memoria RAM
    sistema.getHardware().getProcessor(): devuelve toda la información de la CPU
    sistema.getHardware().getSensors(): devuelve la información de temperatura de la CPU, voltaje
        y velocidad de los ventiladores
    */
    }
    
}
