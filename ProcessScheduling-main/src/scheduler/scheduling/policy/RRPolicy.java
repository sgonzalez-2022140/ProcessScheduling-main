/* RRPolicy.java */
/**
 * Hecho por: Jonathan Garcia
 * Seccion: A
 *
 * Descripcion:
 * Política ROUND ROBIN (RR).
 * - Usa una cola FIFO (ConcurrentLinkedQueue).
 * - Tiene un quantum en milisegundos.
 * - Más adelante el Procesador usará este quantum para simular RR.
 */

package scheduler.scheduling.policy;
import java.util.concurrent.ConcurrentLinkedQueue;
import scheduler.processing.SimpleProcess;

public class RRPolicy extends Policy{
    private final ConcurrentLinkedQueue<SimpleProcess> cola = new ConcurrentLinkedQueue<>();
    private final long quantumMs;

    /**
     * @param quantumSegundos quantum en segundos (desde ProcessScheduler)
     */
    public RRPolicy(double quantumSegundos){
        this.quantumMs = (long) (quantumSegundos * 1000);
    }

    @Override
    public String getNombrePolitica(){
        return "Round Robin";
    }

    /** Devuelve el quantum en milisegundos. */
    public long getQuantumMs(){
        return quantumMs;
    }

    @Override
    public void enqueue(SimpleProcess p){
        cola.add(p);
    }

    @Override
    public SimpleProcess dequeue(){
        return cola.poll();
    }

    @Override
    public boolean isEmpty(){
        return cola.isEmpty();
    }

    /**
     * Imprime el contenido de la cola RR.
     */
    @Override
    public void imprimirCola(){
        if (cola.isEmpty()){
            System.out.println("[cola RR vacia]");
            return;
        }

        System.out.print("Cola RR: ");
        for (SimpleProcess p : cola){
            System.out.print(p + " <- ");
        }
        System.out.println();
    }

    @Override
    public int getQueueSize(){
        return cola.size();
    }
}
