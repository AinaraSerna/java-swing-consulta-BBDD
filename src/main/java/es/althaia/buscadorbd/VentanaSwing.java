/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package es.althaia.buscadorbd;

import es.althaia.buscadorbd.clases.Clientes;
import es.althaia.buscadorbd.clases.Expedientes;
import es.althaia.buscadorbd.controladores.ClientesJpaController;
import es.althaia.buscadorbd.controladores.ExpedientesJpaController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Ainara
 */
public class VentanaSwing extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaSwing.class.getName());
    private EntityManagerFactory emf;
    private ClientesJpaController daoClientes;
    private String textoBuscador;
    StyledDocument doc;
    Style styleNormal;
    Style styleBold;
    Style styleLarge;
    Style styleBoldLarge;

    public VentanaSwing() {
        initComponents();
        configurarListeners();
        abrirConexionBD();
    }

    private void configurarListeners() {
        // El botón se habilita si hay texto en el campo de texto
        buscadorTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                verificarTexto();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                verificarTexto();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                verificarTexto();
            }

            private void verificarTexto() {
                String texto = buscadorTextField.getText().trim();
                buscarButton.setEnabled(!texto.isEmpty());
            }
        });
        // Al pulsar 'Enter' se acciona el botón
        this.getRootPane().setDefaultButton(buscarButton);

        // Estilos para el text panel
        doc = cuadroInformativoTextPane.getStyledDocument();
        styleNormal = cuadroInformativoTextPane.addStyle("Normal", null);
        StyleConstants.setFontSize(styleNormal, 16);

        styleBold = cuadroInformativoTextPane.addStyle("Bold", styleNormal);
        StyleConstants.setBold(styleBold, true);

        styleLarge = cuadroInformativoTextPane.addStyle("Large", styleNormal);
        StyleConstants.setFontSize(styleLarge, 20);

        styleBoldLarge = cuadroInformativoTextPane.addStyle("BoldLarge", styleNormal);
        StyleConstants.setBold(styleBoldLarge, true);
        StyleConstants.setFontSize(styleBoldLarge, 24);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        cuadroInformativoTextPane = new javax.swing.JTextPane();
        buscadorLabel = new javax.swing.JLabel();
        buscadorTextField = new javax.swing.JTextField();
        buscarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Buscador de expedientes y clientes");
        setResizable(false);

        cuadroInformativoTextPane.setFocusCycleRoot(false);
        cuadroInformativoTextPane.setBackground(new java.awt.Color(153, 204, 255));

        cuadroInformativoTextPane.setEditable(false);
        jScrollPane2.setViewportView(cuadroInformativoTextPane);

        buscadorLabel.setFont(new java.awt.Font("Cambria Math", 1, 18)); // NOI18N
        buscadorLabel.setLabelFor(buscadorTextField);
        buscadorLabel.setText("Nº expediente, NIF/CIF o asunto:");

        buscadorTextField.setToolTipText("Escribe un nº de expediente, un NIF/CIF o el asunto de un expediente");

        getContentPane().setBackground(new java.awt.Color(153, 204, 255));
        buscarButton.setBackground(new java.awt.Color(204, 147, 6));
        buscarButton.setText("Buscar");
        buscarButton.setEnabled(false);
        buscarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buscadorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(buscadorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buscarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addGap(65, 65, 65))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscadorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscadorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarButtonActionPerformed
        textoBuscador = buscadorTextField.getText();
        if (esNExpedienteContenidoTextField()) {
            List<Expedientes> listaExpedientes = buscarExpedientePorId();
            mostrarExpedienteEnGUI(listaExpedientes);
        } else if (esNIFCIFContenidoTextField()) {
            Clientes clienteEncontrado = buscarClientePorNIF();
            mostrarClienteEnGUI(clienteEncontrado);
        } else {
            List<Expedientes> listaExpedientes = buscarClientesPorAsunto();
            mostrarExpedientesPorAsunto(listaExpedientes);
        }
    }//GEN-LAST:event_buscarButtonActionPerformed

    private void abrirConexionBD() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("AlthaiaBDPU");
            daoExpedientes = new ExpedientesJpaController(emf);
            daoClientes = new ClientesJpaController(emf);
        }
    }

    private boolean esNExpedienteContenidoTextField() {
        String regex = "[\\d{4}\\/]?\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textoBuscador);
        return matcher.find();
    }

    private boolean esNIFCIFContenidoTextField() {
        String regex = "[BJXYZ]?[-]?[0-9]{7,9}[A-Z]?";
        textoBuscador = textoBuscador.replace("-", "");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textoBuscador);
        return matcher.find();
    }

    private List<Expedientes> buscarExpedientePorId() {
        List<Expedientes> lista;
        try (EntityManager em = daoClientes.getEntityManager()) {
            String sentenciaSQL = "SELECT exp FROM Expedientes exp WHERE exp.nExpediente LIKE '%"
                    + textoBuscador + "%' ORDER BY exp.nExpediente ASC";
            Query query = em.createQuery(sentenciaSQL);
            lista = query.getResultList();
        }
        return lista;
    }

    private Clientes buscarClientePorNIF() {
        Clientes cliente;
        try (EntityManager em = daoClientes.getEntityManager()) {
            TypedQuery<Clientes> consultaClientes = em.createNamedQuery("Clientes.findByNif", Clientes.class);
            consultaClientes.setParameter("nif", textoBuscador.toUpperCase());
            cliente = consultaClientes.getSingleResult();
        } catch (NoResultException ex) {
            cliente = new Clientes();
        }
        return cliente;
    }

    private List<Expedientes> buscarClientesPorAsunto() {
        List<Expedientes> lista;
        try (EntityManager em = daoClientes.getEntityManager()) {
            String sentenciaSQL = "SELECT exp FROM Expedientes exp WHERE upper(exp.asunto) LIKE upper('%" + textoBuscador + "%')";
            Query query = em.createQuery(sentenciaSQL);
            lista = query.getResultList();
        }
        return lista;
    }

    private void mostrarExpedienteEnGUI(List<Expedientes> listaExpedientes) {
        cuadroInformativoTextPane.setText("");
        if (listaExpedientes != null) {
            for (Expedientes exp : listaExpedientes) {
                insertString(doc, "Nº de expediente: " + exp.getNExpediente() + "\n\n", styleBoldLarge);
                insertString(doc, "Cliente: " + exp.getIdCliente().getNif() + " - " + exp.getIdCliente().getNombre() + '\n', styleLarge);
                insertString(doc, "Asunto: " + exp.getAsunto(), styleNormal);
                if (exp.getFechaFin() != null) {
                    insertString(doc, "\nFecha(s): " + dateFormatter(exp.getFechaInicio()), styleBold);
                }
                if (exp.getFechaFin() != null) {
                    insertString(doc, " - " + dateFormatter(exp.getFechaFin()), styleBold);
                }
                insertString(doc, "\n\n" + "-".repeat(150), styleBold);
                insertString(doc, "\n\n\n", styleNormal);
            }
        } else {
            insertString(doc, "No se ha encontrado el expediente " + textoBuscador, styleBold);
        }
        cambiarFondoPanel();
    }

    private void mostrarClienteEnGUI(Clientes cli) {
        cuadroInformativoTextPane.setText("");
        if (cli.getIdCliente() != null) {
            insertString(doc, cli.getNombre() + "\n\n", styleBoldLarge);
            insertString(doc, "NIF/CIF: " + cli.getNif() + "\n", styleNormal);
            insertString(doc, "Dirección: " + cli.getDireccion() + "\n", styleNormal);
            insertString(doc, "Población: " + cli.getPoblacion() + "\n", styleNormal);
            insertString(doc, "Provincia: " + cli.getProvincia(), styleNormal);
            if (cli.getEmail() != null) {
                insertString(doc, "\nEmail: " + cli.getEmail(), styleNormal);
            }
            if (cli.getTfFax() != null) {
                insertString(doc, "\nTf/fax: " + cli.getTfFax(), styleNormal);
            }
            if (!cli.getExpedientesCollection().isEmpty()) {
                insertString(doc, "\nExpedientes:\n", styleNormal);
            }
            for (Expedientes exp : cli.getExpedientesCollection()) {
                insertString(doc, "\t· " + exp.getNExpediente() + " - " + exp.getAsunto() + " | " + dateFormatter(exp.getFechaInicio()) + "\n", styleNormal);
            }
        } else {
            insertString(doc, "No se ha encontrado el cliente con NIF/CIF " + textoBuscador, styleBold);
        }
        cambiarFondoPanel();
    }

    private void mostrarExpedientesPorAsunto(List<Expedientes> listaExpedientes) {
        cuadroInformativoTextPane.setText("");
        if (!listaExpedientes.isEmpty()) {
            for (Expedientes exp : listaExpedientes) {
                insertString(doc, "Nº de expediente: " + exp.getNExpediente() + "\n\n", styleBoldLarge);
                insertString(doc, "Cliente: " + exp.getIdCliente().getNif() + " - " + exp.getIdCliente().getNombre() + "\n", styleLarge);
                insertString(doc, "Asunto: " + exp.getAsunto(), styleNormal);
                if (exp.getFechaInicio() != null) {
                    insertString(doc, "\nFecha(s): " + dateFormatter(exp.getFechaInicio()), styleBold);
                }
                if (exp.getFechaFin() != null) {
                    insertString(doc, " - " + dateFormatter(exp.getFechaFin()), styleBold);
                }
                insertString(doc, "\n\n" + "-".repeat(150), styleBold);
                insertString(doc, "\n\n\n", styleNormal);
            }
        } else {
            insertString(doc, "No se ha encontrado ningún expediente que contenga la palabra '" + textoBuscador + "'", styleBold);
        }
        cambiarFondoPanel();
    }

    private void cambiarFondoPanel() {
        cuadroInformativoTextPane.setVisible(true);
        cuadroInformativoTextPane.setBackground(new java.awt.Color(255, 204, 204));
    }

    private static void insertString(StyledDocument doc, String str, Style style) {
        try {
            doc.insertString(doc.getLength(), str, style);
        } catch (BadLocationException ex) {
            System.getLogger(VentanaSwing.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private String dateFormatter(Date fecha) {
        return new SimpleDateFormat("dd/MM/YYYY").format(fecha);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new VentanaSwing().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel buscadorLabel;
    private javax.swing.JTextField buscadorTextField;
    private javax.swing.JButton buscarButton;
    private javax.swing.JTextPane cuadroInformativoTextPane;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

}
