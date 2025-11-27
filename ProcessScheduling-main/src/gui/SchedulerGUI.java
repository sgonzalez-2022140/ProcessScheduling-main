package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import planificador.GeneradorProcesos;
import planificador.Procesador;
import scheduler.scheduling.policy.*;

/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Seccion: A
 *
 * Interfaz grafica es como un tipo simulador de Process Scheduling
 * Permite:
 *  - Elegir política (FCFS, LCFS, PP, RR)
 *  - Configurar rango, tiempos de procesos y quantum
 *  - Ver la cola/procesos atendidos en un JTextArea
 *  - Ver el proceso actual y número de procesos atendidos en labels
 */
public class SchedulerGUI extends JFrame {

    //controles de configuracion
    private JComboBox<String> comboPolitica;
    private JTextField txtRango;
    private JTextField txtArith;
    private JTextField txtIO;
    private JTextField txtCond;
    private JTextField txtLoop;
    private JTextField txtQuantum;

    // Botones
    private JButton btnIniciar;
    private JButton btnDetener;

    //area para mostrar cola y log
    private JTextArea areaCola;

    //labels de estado
    private JLabel lblProceso;
    private JLabel lblAtendidos;
    private JLabel lblEstado;

    //objetos del simulador
    private Thread hiloGen;
    private Thread hiloProc;
    private GeneradorProcesos generador;
    private Procesador procesador;
    private Policy politica;
    private volatile boolean corriendo = false;

    public SchedulerGUI() {
        setTitle("Simulador de Process Scheduler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        //panel superior
        JPanel panelConfig = new JPanel();
        panelConfig.setLayout(new GridLayout(3, 6, 5, 5));
        panelConfig.setBorder(BorderFactory.createTitledBorder("Configuración"));

        comboPolitica = new JComboBox<>(new String[] {"First Come First Served (FCFS)", "Last Come First Served (LCFS)", "Priority Policy (PP)", "Round Robin (RR)"});

        txtRango = new JTextField("1-3");
        txtArith = new JTextField("1");
        txtIO = new JTextField("1");
        txtCond = new JTextField("1");
        txtLoop = new JTextField("1");
        txtQuantum = new JTextField("0.5");
        txtQuantum.setEnabled(false);

        panelConfig.add(new JLabel("Política:"));
        panelConfig.add(comboPolitica);
        panelConfig.add(new JLabel(""));
        panelConfig.add(new JLabel(""));
        panelConfig.add(new JLabel(""));
        panelConfig.add(new JLabel(""));

        panelConfig.add(new JLabel("Rango ingreso:"));
        panelConfig.add(txtRango);
        panelConfig.add(new JLabel("Arith (s):"));
        panelConfig.add(txtArith);
        panelConfig.add(new JLabel("IO (s):"));
        panelConfig.add(txtIO);

        panelConfig.add(new JLabel("Cond (s):"));
        panelConfig.add(txtCond);
        panelConfig.add(new JLabel("Loop (s):"));
        panelConfig.add(txtLoop);
        panelConfig.add(new JLabel("Quantum (s):"));
        panelConfig.add(txtQuantum);

        //habilitar y deshabilitar quantum segun politica
        comboPolitica.addActionListener(e -> {
            String sel = (String) comboPolitica.getSelectedItem();
            boolean esRR = sel != null && sel.startsWith("Round Robin");
            txtQuantum.setEnabled(esRR);
        });

        //panel central area de cola y log
        areaCola = new JTextArea();
        areaCola.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaCola);
        scroll.setBorder(BorderFactory.createTitledBorder("Cola / Log"));

        //panel inferior estado con los botones
        JPanel panelEstado = new JPanel(new GridLayout(2, 1));
        JPanel panelLabels = new JPanel(new GridLayout(1, 3));
        lblProceso = new JLabel("Atendiendo: (ninguno)");
        lblAtendidos = new JLabel("Procesos atendidos: 0");
        lblEstado = new JLabel("Estado: Detenido");

        panelLabels.add(lblProceso);
        panelLabels.add(lblAtendidos);
        panelLabels.add(lblEstado);

        JPanel panelBotones = new JPanel();
        btnIniciar = new JButton("Iniciar simulacion");
        btnDetener = new JButton("Detener");

        btnDetener.setEnabled(false);

        panelBotones.add(btnIniciar);
        panelBotones.add(btnDetener);

        panelEstado.add(panelLabels);
        panelEstado.add(panelBotones);

        //listeners de botones
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSimulacion();
            }
        });

        btnDetener.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detenerSimulacion();
            }
        });

        //layout principal
        setLayout(new BorderLayout(5, 5));
        add(panelConfig, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelEstado, BorderLayout.SOUTH);
    }

    private void iniciarSimulacion() {
        if (corriendo) {
            JOptionPane.showMessageDialog(this, "La simulacion se esta ejecutando", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            //leer configuracion
            String rango = txtRango.getText().trim();
            double arith = Double.parseDouble(txtArith.getText().trim());
            double io = Double.parseDouble(txtIO.getText().trim());
            double cond = Double.parseDouble(txtCond.getText().trim());
            double loop = Double.parseDouble(txtLoop.getText().trim());
            double quantum = 0;
            String sel = (String) comboPolitica.getSelectedItem();

            //crear politica
            if (sel.startsWith("First Come")) {
                politica = new FCFSPolicy();
            } else if (sel.startsWith("Last Come")) {
                politica = new LCFSPolicy();
            } else if (sel.startsWith("Priority Policy")) {
                politica = new PriorityPolicy();
            } else if (sel.startsWith("Round Robin")) {
                quantum = Double.parseDouble(txtQuantum.getText().trim());
                politica = new RRPolicy(quantum);
            } else {
                JOptionPane.showMessageDialog(this, "No se reconocio la politica", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            areaCola.setText("");
            lblProceso.setText("Atendiendo: (ninguno)");
            lblAtendidos.setText("Procesos atendidos: 0");
            lblEstado.setText("Estado: Corriendo");

            //crear generador y procesador con soporte GUI
            generador = new GeneradorProcesos(politica, rango, arith, io, cond, loop);
            procesador = new Procesador(politica, areaCola, lblProceso, lblAtendidos);

            //crear e iniciar hilos
            hiloGen  = new Thread(generador, "GeneradorProcesos");
            hiloProc = new Thread(procesador, "Procesador");

            corriendo = true;
            hiloGen.start();
            hiloProc.start();

            btnIniciar.setEnabled(false);
            btnDetener.setEnabled(true);

        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this,
                    "Error en los valores numericos, verifique tiempos y quantum",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al iniciar la simulacion: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void detenerSimulacion(){
        if (!corriendo) return;
        corriendo = false;
        lblEstado.setText("Estado: Deteniendo...");

        try{
            if (generador != null){
                generador.detener();
            }
            if (procesador != null){
                procesador.detener();
            }

            if (hiloGen != null){
                hiloGen.join(2000);
            }
            if (hiloProc != null){
                hiloProc.join(2000);
            }

        }catch(InterruptedException e){
            //ignoramos
        }

        lblEstado.setText("Estado: Detenido");
        btnIniciar.setEnabled(true);
        btnDetener.setEnabled(false);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            SchedulerGUI gui = new SchedulerGUI();
            gui.setVisible(true);
        });
    }
}