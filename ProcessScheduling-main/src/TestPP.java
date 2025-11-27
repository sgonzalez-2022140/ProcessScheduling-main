import scheduler.processing.pp.PriorityIOProcess;

import scheduler.processing.Prioritizable;

import java.util.PriorityQueue;
import java.util.Comparator;

public class TestPP {
    public static void main(String[] args) {

        PriorityQueue<Prioritizable> pq = new PriorityQueue<>(
            Comparator.comparingInt(Prioritizable::getPriority)
        );
        // prioridad 2
        pq.add(new PriorityIOProcess(1, 400, 2));
        // prioridad 3 (más baja)
        pq.add(new PriorityIOProcess(3, 300, 3));
        // prioridad 1, llega despues
        pq.add(new PriorityIOProcess(4, 100, 1));

        System.out.println("=== Orden esperado por prioridad (1 primero) ===");
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }
}
