/* GeneradorProcesos.java */

/** 
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Seccion: A
 * 
 * Descripcion:
 * Genera procesos aleatorios de tipo:
 *   - Aritmetico
 *   - IO
 *   - Condicional
 *   - Loop
 * 
 * Compatible con TODAS las politicas:
 *   - FCFS
 *   - LCFS
 *   - Priority Policy (PP)
 *   - Round Robin (RR)
 */

package planificador;

import java.util.Random;
import scheduler.scheduling.policy.*;
import scheduler.processing.*;
import scheduler.processing.pp.*;
import scheduler.processing.rr.*;

public class GeneradorProcesos implements Runnable {
    private Policy politica;
    private double minTiempo;
    private double maxTiempo;
    private int idContador = 1;
    private boolean activo = true;
    private Random random = new Random();

    //tiempos en segundos
    private double tiempoArit;
    private double tiempoIO;
    private double tiempoCond;
    private double tiempoLoop;

    public GeneradorProcesos(
        Policy politica,
        String rango,
        double arith,
        double io,
        double cond,
        double loop
    ) {
        this.politica = politica;

        String[] partes = rango.split("-");
        this.minTiempo = Double.parseDouble(partes[0]);
        this.maxTiempo = Double.parseDouble(partes[1]);

        this.tiempoArit = arith;
        this.tiempoIO = io;
        this.tiempoCond = cond;
        this.tiempoLoop = loop;
    }

    /** Retorna un tipo aleatorio entre 0 y 3 */
    private int tipoAleatorio(){
        return random.nextInt(4);
    }

    /** Tiempo aleatorio entre minTiempo y maxTiempo */
    private double tiempoAleatorio(){
        return minTiempo + (maxTiempo - minTiempo) * random.nextDouble();
    }

    @Override
    public void run(){
        System.out.println("\n--- Generador de procesos activo ---");
        while (activo) {
            int tipo = tipoAleatorio();
            int id = idContador++;
            long tiempoMs;
            SimpleProcess nuevo = null;
            //detectar politica
            boolean esPP = politica instanceof PriorityPolicy;
            boolean esRR = politica instanceof RRPolicy;
            //si es prioridad genera prioridad 1–3
            int prio = random.nextInt(3) + 1;
            switch (tipo) {
                case 0: //aritmetico
                    tiempoMs = (long) (tiempoArit * 1000);
                    nuevo = esPP ? new PriorityArithmeticProcess(id, tiempoMs, prio) : esRR ? new RRArithmeticProcess(id, tiempoMs) : new ArithmeticProcess(id, tiempoMs);
                    break;
                case 1: //IO
                    tiempoMs = (long) (tiempoIO * 1000);
                    nuevo = esPP ? new PriorityIOProcess(id, tiempoMs, prio) : esRR ? new RRIOProcess(id, tiempoMs) : new IOProcess(id, tiempoMs);
                    break;
                case 2: //condicional
                    tiempoMs = (long) (tiempoCond * 1000);
                    nuevo = esPP ? new PriorityConditionalProcess(id, tiempoMs, prio) : esRR ? new RRConditionalProcess(id, tiempoMs) : new ConditionalProcess(id, tiempoMs);
                    break;
                case 3: //loop
                    tiempoMs = (long) (tiempoLoop * 1000);
                    nuevo = esPP ? new PriorityLoopProcess(id, tiempoMs, prio) : esRR ? new RRLoopProcess(id, tiempoMs) : new LoopProcess(id, tiempoMs);
                    break;
            }
            //mostrar proceso
            System.out.println("Nuevo proceso generado: " + nuevo);
            //encolarlo
            politica.encolarProceso(nuevo);
            //esperar
            try {
                Thread.sleep((long) (tiempoAleatorio() * 1000));
            } catch (InterruptedException e) {}
        }
        System.out.println("--- Generador de procesos detenido ---");

    }

    public void detener() {
        activo = false;
    }
}
