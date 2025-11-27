/* ConditionalProcess.java */
/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Seccion: A
 * Descripcion:
 * Proceso de tipo Condicional (C)
 * Representa procesos que realizan evaluaciones de condiciones
 * Hereda de SimpleProcess y fija el tipo "C"
 */

package scheduler.processing;

public class ConditionalProcess extends SimpleProcess {

    /**
     * Constructor del proceso condicional.
     * @param id identificador unico del proceso
     * @param serviceTime tiempo total de servicio en milisegundos
     */
    public ConditionalProcess(int id, long serviceTime) {
        // "C" = Condicional
        super(id, serviceTime, "C");
    }
}
