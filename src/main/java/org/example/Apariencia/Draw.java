package org.example.Apariencia;

import javax.swing.*;
import java.awt.*;

public class Draw {
    static ImageIcon calendar = new ImageIcon("images/calendar.png");
    static ImageIcon miscitas = new ImageIcon("images/miscitas.png");
    static ImageIcon patient = new ImageIcon("images/patient.png");
    static ImageIcon setting = new ImageIcon("images/settings.png");


    public static ImageIcon scaleIcon(ImageIcon icon ,int width, int height) {
        Image image = icon.getImage(); // Obtener el objeto Image
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Cambiar tamaño
        return new ImageIcon(scaledImage);
    }

}
