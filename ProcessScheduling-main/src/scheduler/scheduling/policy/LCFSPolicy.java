/* LCFSPolicy.java */

/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Seccion: A
 *
 * Descripcion:
 * Implementación de la politica LAST COME FIRST SERVED LCFS
 * El último proceso que entra es el primero en ser atendido
 * Internamente usa una pila Stack como estructura LIFO
 */

package scheduler.scheduling.policy;

import java.util.Stack;
import scheduler.processing.SimpleProcess;

public class LCFSPolicy extends Policy{

    private final Stack<SimpleProcess> pila = new Stack<>();

    @Override
    public String getNombrePolitica(){
        return "Last Come First Served";
    }

    @Override
    public void enqueue(SimpleProcess p){
        pila.push(p);
    }

    @Override
    public SimpleProcess dequeue(){
        if (pila.isEmpty()) return null;
        return pila.pop();
    }

    @Override
    public boolean isEmpty(){
        return pila.isEmpty();
    }

    /**
     * Imprime la pila de procesos LIFO
     * Muestra el tope de la pila primero
     */
    @Override
    public void imprimirCola(){
        if (pila.isEmpty()){
            System.out.println("[cola/pila vacia]");
            return;
        }

        System.out.print("Pila LCFS (tope primero): ");
        //recorremos desde el tope hacia abajo
        for (int i = pila.size() - 1; i >= 0; i--){
            System.out.print(pila.get(i) + " <- ");
        }
        System.out.println();
    }

    @Override
    public int getQueueSize(){
        return pila.size();
    }
}