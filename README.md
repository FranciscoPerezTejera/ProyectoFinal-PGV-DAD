# Proyecto de Monitoreo de Servidores
El objetivo de este proyecto es desarrollar un sistema de monitoreo para supervisar el estado de varios servidores y enviar alertas en tiempo real en caso de detectar problemas. El sistema consta de las siguientes funcionalidades clave:

Monitoreo de Servidores:
Utiliza hilos en el servidor central de monitoreo para recopilar datos de varios servidores simultáneamente.
Implementa scripts o utiliza la API de Java OSHI para obtener métricas como uso de CPU, memoria, espacio en disco y disponibilidad de servicios.
Sistema de Alertas:
Desarrolla un mecanismo que evalúa las métricas y genera alertas basadas en umbrales predefinidos.
Implementa una función de envío de alertas a través de sockets para que los clientes (interfaz de usuario) reciban notificaciones en tiempo real.
Interfaz de Usuario:
Crea una interfaz sencilla para recibir y mostrar alertas en tiempo real.
Puede ser una interfaz web o una aplicación de escritorio, utilizando Java Swing para la segunda opción.
Comunicación:
Utiliza sockets y TCP para una comunicación fiable entre el servidor central de monitoreo y los clientes.
