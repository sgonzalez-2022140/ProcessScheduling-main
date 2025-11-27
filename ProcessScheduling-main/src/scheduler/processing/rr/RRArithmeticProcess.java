/* RRArithmeticProcess.java */

/**
 ** Hecho por: Rong-yih Tong Tsai
 ** Carnet: 25006044
 ** Seccion: A
 ** Proceso aritmetico adaptado para Round Robin
 */

package scheduler.processing.rr;

import scheduler.processing.ArithmeticProcess;

public class RRArithmeticProcess extends ArithmeticProcess {

    private long remainingTime;

    public RRArithmeticProcess(int id, long serviceTime) {
        super(id, serviceTime);
        this.remainingTime = serviceTime;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void consume(long quantum) {
        remainingTime -= quantum;
        if (remainingTime < 0) remainingTime = 0;
    }

    public boolean isDone() {
        return remainingTime <= 0;
    }

    @Override
    public String toString() {
        return super.toString() + " | remaining=" + remainingTime + "ms";
    }
}

