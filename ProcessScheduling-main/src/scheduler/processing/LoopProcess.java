/* LoopProcess.java */
/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Seccion: A
 * Descripcion:
 * Proceso de tipo Iterativo / Loop (L)
 * Representa procesos que repiten instrucciones (ciclos)
 * Hereda de SimpleProcess y fija el tipo L
 */

package scheduler.processing;

public class LoopProcess extends SimpleProcess {

    /**
     * Constructor del proceso iterativo.
     * @param id identificador unico del proceso
     * @param serviceTime tiempo total de servicio en milisegundos
     */
    public LoopProcess(int id, long serviceTime) {
        // "L" = Loop / Iterativo
        super(id, serviceTime, "L");
    }
}
