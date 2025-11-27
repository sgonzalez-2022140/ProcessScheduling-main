/*ProcessScheduler.java*/ 

/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Seccion: A
 * Clase Main del Proyecto #2 – Process Scheduling
 * Esta clase interpreta los argumentos enviados desde consola, selecciona
 * la política indicada y ejecuta la simulación hasta que el usuario
 * presione la tecla q 
 */

import java.io.*; //InputStreamReader y BufferedReader
import scheduler.scheduling.policy.*; //FCFSPolicy, LCFSPolicy, etc.
import planificador.GeneradorProcesos;
import planificador.Procesador;

public class ProcessScheduler {
    public static void main(String[] args){
        try {
            if (args.length < 6) {
                System.out.println("\nUso:");
                System.out.println("java ProcessScheduler -fcfs rango arith io cond loop");
                System.out.println("java ProcessScheduler -lcfs rango arith io cond loop");
                System.out.println("java ProcessScheduler -pp rango arith io cond loop");
                System.out.println("java ProcessScheduler -rr rango arith io cond loop quantum");
                return;
            }

            //politica
            String politicaFlag = args[0];
            boolean esRR = politicaFlag.equalsIgnoreCase("-rr");
            if (esRR && args.length < 7){
                System.out.println("Dio un error para el Round Robin RR, se debe agregar un quantum");
                return;
            }

            //rango de ingreso, ejemplo: 1.5-3
            String rango = args[1];
            String[] rparts = rango.split("-");
            double rangoMin = Double.parseDouble(rparts[0]);
            double rangoMax = Double.parseDouble(rparts[1]);

            //tiempos por tipo de proceso en segundos
            double timeArith = Double.parseDouble(args[2]);
            double timeIO = Double.parseDouble(args[3]);
            double timeCond = Double.parseDouble(args[4]);
            double timeLoop = Double.parseDouble(args[5]);

            //quantum para RR
            double quantum = 0;
            if (esRR){
                quantum = Double.parseDouble(args[6]);
            }

            //crear la política concreta
            Policy politicaUsada = null;
            switch (politicaFlag.toLowerCase()){
                case "-fcfs":
                    System.out.println("Politica escogida: FIRST COME FIRST SERVED");
                    politicaUsada = new FCFSPolicy();
                    break;
                case "-lcfs":
                    System.out.println("Politica escogida: LAST COME FIRST SERVED");
                    politicaUsada = new LCFSPolicy();
                    break;
                case "-pp":
                    System.out.println("Politica escogida: PRIORITY POLICY");
                    politicaUsada = new PriorityPolicy();
                    break;
                case "-rr":
                    System.out.println("Politica escogida: ROUND ROBIN");
                    politicaUsada = new RRPolicy(quantum);
                    break;
                default:
                    System.out.println("No se conoce esta politica");
                    return;
            }

            System.out.println("\nInicio de la ejecucion");
            System.out.println("Rango de ingreso: " + rangoMin + " - " + rangoMax + " segundos");
            System.out.println("Tiempo en Arith: " + timeArith);
            System.out.println("Tiempo en IO: " + timeIO);
            System.out.println("Tiempo en Cond: " + timeCond);
            System.out.println("Tiempo en Loop: " + timeLoop);
            if(esRR){
                System.out.println("Quantum: " + quantum + " segundos");
            }

            //crear generador y procesador
            GeneradorProcesos generador = new GeneradorProcesos(
                politicaUsada,
                rango,
                timeArith,
                timeIO,
                timeCond,
                timeLoop
            );

            //crear hilos
            Procesador procesador = new Procesador(politicaUsada);
            Thread hiloGen  = new Thread(generador);
            Thread hiloProc = new Thread(procesador);

            //iniciar hilos
            hiloGen.start();
            hiloProc.start();

            System.out.println("\nSe esta ejecutando el programa");
            System.out.println("Presione 'q' + ENTER para detener la ejecucion\n");

            //esperar a que el usuario presione q
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            String linea;
            while (true){
                linea = buffer.readLine();
                if (linea != null && linea.trim().equalsIgnoreCase("q")){
                    System.out.println("\nSe detuvo la ejecucion");
                    break;
                }
            }
            //detener hilos
            generador.detener();
            procesador.detener();

            try{
                //esperamos hasta 2 segundos a que termine
                hiloGen.join(2000);  
                hiloProc.join(2000);
            }catch(InterruptedException e){

            }
            System.out.println("------------------------------------");
            System.out.println("Termino la ejecucion del programa");
            System.out.println("------------------------------------");
            System.out.println("Procesos atendidos: " + procesador.getProcesosAtendidos());
            System.out.println("Procesos en cola sin atender: " + politicaUsada.getQueueSize());
            System.out.println("Tiempo promedio de atencion (ms): " + procesador.getTiempoPromedioMs());
            System.out.println("Politica usada: " + politicaUsada.getNombrePolitica());
        }catch (Exception exception){
            System.out.println("Error de ejecucion: ");
            exception.printStackTrace();
        }
    }
}
