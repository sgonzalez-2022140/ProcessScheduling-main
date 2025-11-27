/* PriorityIOProcess.java */

/**
 ** Hecho por: Rong-yih Tong Tsai
 ** Carnet: 25006044
 ** Seccion: A
 ** Proceso Loop
 */


package scheduler.processing.pp;

import scheduler.processing.IOProcess;
import scheduler.processing.Prioritizable;

public class PriorityIOProcess extends IOProcess implements Prioritizable {
    private final int priority;

    public PriorityIOProcess(int id, long serviceTime, int priority) {
        super(id, serviceTime);
        this.priority = priority;
    }

    @Override
    public int getPriority(){
        return priority;
    }

    @Override
    public String toString() {
        return super.toString() + " | p=" + priority;
    }
}