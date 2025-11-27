package scheduler.scheduling;

import scheduler.processing.SimpleProcess;

/* SimpleProcess.java */
/**
 * Hecho por: Santiago
 * Carnet: 25000328
 * Seccion: A
 * Info: Nos dicen que debemos tener esta interfaz para todas las politicas
 */
public interface Enqueable {

    /** Agrega un proceso a la estructura de la política */
    void enqueue(SimpleProcess p);

    /** Saca el siguiente proceso según la política */
    SimpleProcess dequeue();

    /** Indica si ya no hay procesos por atender */
    boolean isEmpty();
}