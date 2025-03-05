package org.example.Apariencia;

import org.example.Database.DatabaseManagement;

import javax.swing.*;
import java.awt.*;

public class Perfil extends JPanel implements HistorialSelectionListener{
    private TarjetaIdentificacionPaciente tarjetaIdentificacion;
    private JPanel rightContainer;
    private JPanel leftContainer;
    private DatabaseManagement dbm;


    public Perfil(String dni) {
        setLayout(new GridLayout(1, 2)); // Dividir la pantalla en dos columnas
        this.dbm = new DatabaseManagement();
        // Contenedor izquierdo con GridLayout(2,1)
        leftContainer = new JPanel(new GridLayout(2, 1));
        leftContainer.setPreferredSize(new Dimension(400, 600));
        leftContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Agregar la Tarjeta de Identificación en la parte superior izquierda
        tarjetaIdentificacion = new TarjetaIdentificacionPaciente(dni);
        leftContainer.add(tarjetaIdentificacion);

        // Agregar un panel vacío en la parte inferior izquierda
        ListaHistorialClinico listaHistorialClinico = new ListaHistorialClinico(dni,this);

        // Contenedor derecho (puede usarse para más información en el futuro)
        rightContainer = new JPanel(new GridLayout(1, 1));
        rightContainer.setPreferredSize(new Dimension(400, 600));
        rightContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        leftContainer.add(listaHistorialClinico);
        // Agregar los contenedores al `Perfil`
        add(leftContainer);
        add(rightContainer);
    }

    @Override
    public void onHistorialSeleccionado(String dniPaciente, String dniTrabajador, String diagnostico) {
        // Obtener código de la historia clínica
        int codigoHistoria = dbm.obtenerCodigoHistoriaClinica(dniPaciente, dniTrabajador, diagnostico);

        if (codigoHistoria != -1) {
            rightContainer.removeAll(); // Limpiar cualquier contenido previo
            PanelHistoriaClinica panelHistoria = new PanelHistoriaClinica(dniPaciente, codigoHistoria);
            rightContainer.add(panelHistoria, BorderLayout.CENTER);
            rightContainer.revalidate();
            rightContainer.repaint();
        }
    }
}
