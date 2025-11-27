package scheduler.processing.rr;

import scheduler.processing.IOProcess;

/* SimpleProcess.java */
/**
 ** Hecho por: Santiago
 ** Carnet: 25000328
 ** Seccion: A
 
 Info: Heredamos el proceso llamado IO y agregamos remainging Time
 
 
 **/
public class RRIOProcess extends IOProcess {

    private long remainingTime;

    /*
     * id identificador único del proceso
     * serviceTime tiempo total de servicio del proceso IO
     */
    public RRIOProcess(int id, long serviceTime) {
        super(id, serviceTime);
        this.remainingTime = serviceTime;
    }

    /*
     * retorna el tiempo restante
     */
    public long getRemainingTime() {
        return remainingTime;
    }

    /*     
     * Resta un quantum al tiempo restante.
     *  quantum --> tiempo a consumir
     */
    public void consume(long quantum) {
        remainingTime -= quantum;
        if (remainingTime < 0) remainingTime = 0;
    }

    /**
     *Returna true si termina
     */
    public boolean isDone() {
        return remainingTime <= 0;
    }

    @Override
    public String toString() {
        return super.toString() + " | remaining=" + remainingTime + "ms";
    }
}
