/* PriorityConditionalProcess.java */

/**
 ** Hecho por: Rong-yih Tong Tsai
 ** Carnet: 25006044
 ** Seccion: A
 ** Proceso Loop
 */


package scheduler.processing.pp;

import scheduler.processing.ConditionalProcess;
import scheduler.processing.Prioritizable;

public class PriorityConditionalProcess extends ConditionalProcess implements Prioritizable {

    private final int priority;

    public PriorityConditionalProcess(int id, long serviceTime, int priority) {
        super(id, serviceTime);
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return super.toString() + " | p=" + priority;
    }
}
