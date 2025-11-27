/* ArithmeticProcess.java */

/* 
    Esta es una clase modelo de los procesos que nos piden crear y representa el tipo aritmético
(Osea operaciones matematicas dentro del Scheduler)


 ESTE HEREDA DE SimpleProcess

*/


/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Seccion: A
 * Proceso de tipo aritmético (operaciones matemáticas).
 */

package scheduler.processing;

public class ArithmeticProcess extends SimpleProcess {

    /**
     * Constructor del proceso aritmetico.
     * @param id          identificador unico del proceso
     * @param serviceTime tiempo total de servicio en milisegundos
     */

    public ArithmeticProcess(int id, long serviceTime) {
        super(id, serviceTime, "A");
    }
}

