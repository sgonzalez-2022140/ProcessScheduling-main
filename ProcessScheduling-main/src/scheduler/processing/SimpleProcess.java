/* SimpleProcess.java */
/**
 * Hecho por: Santiago
 * Carnet: 25000328
 * Seccion: A
 * PSDT: Lo hago porque si o si se necesita el id, el tiempo de serivicio y el tipo en cualquier proceso
 * por lo cual creo que necesitamos obligar a cada clase tener esto
 */

package scheduler.processing;
public abstract class SimpleProcess {

    //cada proceso debe tener estos campos obligatoriamente
    protected final int id;
    protected final long serviceTime;   
    protected final String typeName;

    public SimpleProcess(int id, long serviceTime, String typeName) {
        this.id = id;
        this.serviceTime = serviceTime;
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public long getServiceTime() {
        return serviceTime;
    }

    public String getTypeName() {
        return typeName;
    }

    //los transformamos a String para saber su id, tipo y los ms del servicio
    @Override
    public String toString() {
        return "[" + id + " | " + typeName + " | " + serviceTime + "ms]";
    }
}