package org.example.ClasesNativas;

import org.example.Database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
// primera cita, visita sucesiva, telefonica
public class Cita {
    private Paciente paciente;
    private Trabajador trabajador;
    private Time hora;
    private LocalDate fecha;



    public Cita(Paciente paciente, Trabajador trabajador, Time hora, LocalDate fecha) {

        this.paciente = paciente;
        this.trabajador = trabajador;
        this.hora = hora;
        this.fecha = fecha;
    }



    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


//    public static Cita obtenerCita(int codigo) {
//        String sql = "SELECT * FROM Cita WHERE Codigo = ?";
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, codigo);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return new Cita(
//                        Paciente.obtenerPaciente(rs.getString("Paciente")),
//                        Trabajador.obtenerTrabajador(rs.getString("Trabajador")),
//                        rs.getTime("HoraInicio"),
//                        rs.getTime("HoraFinal"),
//                        rs.getDate("Fecha").toLocalDate()
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
