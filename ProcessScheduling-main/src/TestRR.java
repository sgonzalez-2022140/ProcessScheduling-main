import scheduler.processing.IOProcess;
import scheduler.processing.SimpleProcess;
import scheduler.processing.rr.RRIOProcess;

public class TestRR {

    public static void main(String[] args) throws InterruptedException {

        // 1) Probar IOProcess como SimpleProcess (polimorfismo)
        IOProcess io = new IOProcess(1, 200);  // id=1, dura 200ms
        SimpleProcess sp1 = io;

        System.out.println("=== IOProcess ===");
        System.out.println("Como SimpleProcess: " + sp1);
        System.out.println("ID: " + sp1.getId());
        System.out.println("ServiceTime: " + sp1.getServiceTime() + "ms");
        System.out.println();

        // 2) Probar RRIOProcess
        RRIOProcess rrio = new RRIOProcess(2, 500); // id=2, dura 500ms total
        SimpleProcess sp2 = rrio;

        System.out.println("=== RRIOProcess ===");
        System.out.println("Como SimpleProcess: " + sp2);
        System.out.println("Remaining inicial: " + rrio.getRemainingTime() + "ms");
        System.out.println();

        // 3) Simular Round Robin con quantum quemado
        long quantum = 200;

        System.out.println("=== Simulación RR con quantum=" + quantum + "ms ===");

        while (!rrio.isDone()) {
            long before = rrio.getRemainingTime();
            long slice = Math.min(quantum, before);

            System.out.println("Atendiendo " + slice + "ms ...");
            Thread.sleep(slice);        // simula CPU atendiendo
            rrio.consume(quantum);      // consume quantum completo (tu clase lo limita a 0)

            System.out.println("Remaining ahora: " + rrio.getRemainingTime() + "ms");
            System.out.println("---------------------------");
        }

        System.out.println("Proceso RRIO terminado");
    }
}
