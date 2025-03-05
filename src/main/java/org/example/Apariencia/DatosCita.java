package org.example.Apariencia;

import org.example.Database.DatabaseManagement;

import javax.swing.*;
import java.awt.*;

public class DatosCita extends JPanel {
    private JTextArea comentarioTextArea;
    DatabaseManagement dbm = new DatabaseManagement();
    private JButton guardarDetallesDeCita;
    private int codigoCitaActual = -1;


    public void setCodigoCitaActual(int codigoCitaActual) {
        this.codigoCitaActual = codigoCitaActual;
    }

    public DatosCita(String dni) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Detalles de la Cita"));

        comentarioTextArea = new JTextArea(3, 30);
        comentarioTextArea.setLineWrap(true);
        comentarioTextArea.setWrapStyleWord(true);
        comentarioTextArea.setEditable(false);
//        if (dbm.obtenerTipoUsuario(dni).equalsIgnoreCase("Trabajador")) {
//            comentarioTextArea.setEditable(true);
//        } else {
//            comentarioTextArea.setEditable(false);
//        }
        JScrollPane scrollPane = new JScrollPane(comentarioTextArea);
        add(scrollPane, BorderLayout.CENTER);
        if (dbm.obtenerTipoUsuario(dni).equalsIgnoreCase("Trabajador")) {
            guardarDetallesDeCita = new JButton("Guardar");
            add(guardarDetallesDeCita, BorderLayout.SOUTH);
            guardarDetallesDeCita.addActionListener(e -> {
                String comentario = comentarioTextArea.getText();
                dbm.actualizarComentarioCita(codigoCitaActual,comentario);
            });
        }
    }
    public void habilitarComentario(boolean habilitar) {
        comentarioTextArea.setEditable(habilitar);
    }

    public JTextArea getComentarioTextArea() {
        return comentarioTextArea;
    }

    public void setTextIntoComentarioTextArea(String comentario) {
        comentarioTextArea.setText(comentario);
    }

    public JButton getGuardarDetallesDeCita() {
        return guardarDetallesDeCita;
    }

    public void setGuardarDetallesDeCita(JButton guardarDetallesDeCita) {
        this.guardarDetallesDeCita = guardarDetallesDeCita;
    }

    public void actualizaComentario(int codigoCita, String comentario) {
        dbm.actualizarComentarioCita(codigoCita, comentario);
    }
    public void muestraComentario(int codigoCita) {
        String comentario = dbm.obtenerComentarioCita(codigoCita);
        getComentarioTextArea().setText(comentario != null ? comentario : "Sin comentarios");
    }
}
