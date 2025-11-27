/* Policy.java */
package scheduler.scheduling.policy;
import scheduler.processing.SimpleProcess;
import scheduler.scheduling.Enqueable;

/**
 * Hecho por: Jonathan Garcia / Santiago
 * Seccion: A
 *
 * Clase abstracta base para las politicas de planificacion
 * Implementa Enqueable y da metodos de ayuda para el simulador:
 *  - encolarProceso()
 *  - siguienteProceso()
 *  - procesoTerminado()
 */

public abstract class Policy implements Enqueable{

    private int totalProcessed = 0;

    /** Nombre legible de la política (FCFS, LCFS, etc.) */
    public abstract String getNombrePolitica();

    /** Cada política debe saber cómo imprimir su cola interna. */
    public abstract void imprimirCola();

    /** Cada política debe saber cuántos procesos quedan encolados. */
    public abstract int getQueueSize();

    /** Envuelve enqueue() para usar un nombre más "amigable". */
    public void encolarProceso(SimpleProcess proceso){
        enqueue(proceso);
    }

    /** Envuelve dequeue() con un nombre más descriptivo. */
    public SimpleProcess siguienteProceso(){
        return dequeue();
    }

    /** Llamado cuando un proceso terminó de ser atendido. */
    public void procesoTerminado(SimpleProcess proceso){
        totalProcessed++;
    }

    public int getTotalProcessed(){
        return totalProcessed;
    }
}
