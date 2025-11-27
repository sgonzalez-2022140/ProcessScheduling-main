/* Procesador.java */

/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Seccion A
 *
 * Procesador del simulador
 * Atiende procesos según la política seleccionada
 * 
 * Para RR:
 *   - Usa quantum
 *   - Consume solo parte del tiempo
 *   - Reencola si el proceso no ha terminado
 */

package planificador;

import scheduler.processing.SimpleProcess;
import scheduler.processing.rr.*;
import scheduler.scheduling.policy.*;

import javax.swing.*;
import java.awt.*;

public class Procesador implements Runnable {

    private final Policy politica;
    private boolean ejecutando = true;

    private int atendidos = 0;
    private long tiempoTotalAtencion = 0;

    // ----------- CAMPOS PARA GUI (opcionales) -------------
    private JTextArea areaColaGUI;
    private JLabel labelProcesoGUI;
    private JLabel labelAtendidosGUI;
    private boolean modoGUI = false;

    // ----------- CONSTRUCTOR ORIGINAL (consola) -------------
    public Procesador(Policy politica){
        this.politica = politica;
    }

    // ----------- CONSTRUCTOR PARA GUI -----------------------
    public Procesador(Policy politica, JTextArea cola, JLabel proceso, JLabel atendidos) {
        this.politica = politica;
        this.areaColaGUI = cola;
        this.labelProcesoGUI = proceso;
        this.labelAtendidosGUI = atendidos;
        this.modoGUI = true;
    }

    @Override
    public void run(){
        iniciar();
    }

    // ----------- MÉTODO PARA ACTUALIZAR GUI DE FORMA SEGURA ------------
    private void guiAppend(String txt){
        if (!modoGUI) return;
        SwingUtilities.invokeLater(() -> areaColaGUI.append(txt + "\n"));
    }

    private void guiSetProceso(String txt){
        if (!modoGUI) return;
        SwingUtilities.invokeLater(() -> labelProcesoGUI.setText("Atendiendo: " + txt));
    }

    private void guiSetAtendidos(){
        if (!modoGUI) return;
        SwingUtilities.invokeLater(() ->
            labelAtendidosGUI.setText("Procesos atendidos: " + atendidos)
        );
    }

    public void iniciar(){
        System.out.println("\n--- Procesador iniciado ---");
        System.out.println("Politica utilizada: " + politica.getNombrePolitica());
        guiAppend("Procesador iniciado");
        guiAppend("Política usada: " + politica.getNombrePolitica());
        boolean esRR = politica instanceof RRPolicy;
        long quantumMs = 0;
        if (esRR){
            quantumMs = ((RRPolicy) politica).getQuantumMs();
        }

        while (ejecutando){
            SimpleProcess proceso = politica.siguienteProceso();
            if (proceso == null){
                try{ 
                    Thread.sleep(200); 
                }catch(InterruptedException e){

                }
                continue;
            }

            System.out.println("\nAtendiendo proceso: " + proceso);
            guiAppend("\nAtendiendo proceso: " + proceso);
            guiSetProceso(proceso.toString());

            long inicio = System.currentTimeMillis();

            // ROUND ROBIN
            if (esRR && (
                proceso instanceof RRArithmeticProcess ||
                proceso instanceof RRIOProcess ||
                proceso instanceof RRConditionalProcess ||
                proceso instanceof RRLoopProcess)) 
                {

                long remaining = 0;
                if (proceso instanceof RRArithmeticProcess) remaining = ((RRArithmeticProcess) proceso).getRemainingTime();
                else if (proceso instanceof RRIOProcess) remaining = ((RRIOProcess) proceso).getRemainingTime();
                else if (proceso instanceof RRConditionalProcess) remaining = ((RRConditionalProcess) proceso).getRemainingTime();
                else if (proceso instanceof RRLoopProcess) remaining = ((RRLoopProcess) proceso).getRemainingTime();
                long tiempoAConsumir = Math.min(quantumMs, remaining);

                try{ 
                    Thread.sleep(tiempoAConsumir); 
                }catch(InterruptedException e){

                }

                if(proceso instanceof RRArithmeticProcess) ((RRArithmeticProcess) proceso).consume(quantumMs);
                else if (proceso instanceof RRIOProcess) ((RRIOProcess) proceso).consume(quantumMs);
                else if (proceso instanceof RRConditionalProcess) ((RRConditionalProcess) proceso).consume(quantumMs);
                else if (proceso instanceof RRLoopProcess) ((RRLoopProcess) proceso).consume(quantumMs);

                boolean terminado = false;

                if (proceso instanceof RRArithmeticProcess) terminado = ((RRArithmeticProcess) proceso).isDone();
                else if (proceso instanceof RRIOProcess) terminado = ((RRIOProcess) proceso).isDone();
                else if (proceso instanceof RRConditionalProcess) terminado = ((RRConditionalProcess) proceso).isDone();
                else if (proceso instanceof RRLoopProcess) terminado = ((RRLoopProcess) proceso).isDone();

                if (terminado){
                    atendidos++;
                    guiAppend(" Proceso completado");
                }else{
                    politica.encolarProceso(proceso);
                    guiAppend(" Quantum consumido y reencolado");
                }

            }else{
                //FCFS, LCFS, PP
                try{
                    Thread.sleep(proceso.getServiceTime());
                }catch(InterruptedException e){

                }

                atendidos++;
            }

            guiSetAtendidos();

            long fin = System.currentTimeMillis();
            tiempoTotalAtencion += (fin - inicio);
        }

        System.out.println("\n--- Procesador detenido ---");
        guiAppend("\n--- Procesador detenido ---");
    }

    public void detener(){
        ejecutando = false;
    }

    public int getProcesosAtendidos(){
        return atendidos;
    }

    public double getTiempoPromedioMs(){
        if (atendidos == 0) return 0;
        return (double) tiempoTotalAtencion / atendidos;
    }
}
