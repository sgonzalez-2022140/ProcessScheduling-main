/* SimpleProcess.java */
/**
 * Hecho por: Santiago
 * Carnet: 25000328
 * Seccion: A
 * PSDT: Literalmente solo es asignarle un tipo de servicio llamado IO
 */


package scheduler.processing;


public class IOProcess extends SimpleProcess {
    /**
     * Constructor del proceso IO
     * @param id identificador unico del proceso
     * @param serviceTime tiempo total de servicio en milisegundos
     */
    public IOProcess(int id, long serviceTime) {
        super(id, serviceTime, "IO");
    }
}