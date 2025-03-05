package org.example.Database;

import org.example.ClasesNativas.*;
import org.example.ClasesNativas.Enums.Especialidad;
import org.example.ClasesNativas.Enums.Rol;

import javax.swing.*;
import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManagement {
    private Connection conn = DatabaseConnection.getConnection();

    /**
     *
     * @param fecha
     * @param trabajador
     * @return
     */
    public List<String> obtenerHorariosOcupados(LocalDate fecha, String trabajador) {
        String dni = obtenerDniPorNombreTrabajador(trabajador);
        List<String> horariosOcupados = new ArrayList<>();
        String sql = "SELECT Hora FROM Cita WHERE Fecha = ? AND Trabajador = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(fecha));
            stmt.setString(2, dni);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                horariosOcupados.add(rs.getTime("Hora").toString().substring(0, 5));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return horariosOcupados;
    }

    /**
     * SEGURO
     */

    public String obtenerDniPorNombreTrabajador(String nombreTrabajador) {
        String dni = null;
        String sql = "SELECT DNI FROM Trabajador WHERE Nombre = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreTrabajador);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                dni = rs.getString("DNI");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dni;
    }

    public String obtenerDniPorNombrePaciente(String nombreTrabajador) {
        String dni = null;
        String sql = "SELECT DNI FROM Paciente WHERE Nombre = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreTrabajador);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                dni = rs.getString("DNI");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dni;
    }

    public void guardarSeguro(Seguro s) {
        String sql = "INSERT INTO Seguro (Paciente,Numero, Compañia, Cobertura) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, s.getTitular());
            pstmt.setString(2, s.getNumeroPoliza());
            pstmt.setString(3, s.getCompania().toString());
            pstmt.setString(4, s.getCobertura().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * USUARIO
     */
    public void guardarUsuario(Usuario u) {
        String sql = "INSERT INTO Usuario (DNI, Rol, Contraseña) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, u.getDni());
            pstmt.setString(2, u.getRol().toString());
            pstmt.setString(3, u.getPasswordHash());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeUsuario(String dni) {
        String sql = "SELECT 1 FROM Usuario WHERE DNI = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Si hay algún resultado, el usuario existe
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void guardarPaciente(Paciente p) {
        String sql = "INSERT INTO Paciente (Nombre, DNI, FNacimiento, Email,Sexo,Telefono,Seguro,Domicilio,PaisNacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNombre());
            pstmt.setString(2, p.getDni());
            pstmt.setDate(3, new java.sql.Date(p.getfNacimiento().getTime()));
            pstmt.setString(4, p.getEmail());
            pstmt.setString(5, p.getSexo());
            pstmt.setString(6, p.getTelefono());
            pstmt.setString(7, p.getSeguro());
            pstmt.setString(8, p.getDomicilio());
            pstmt.setString(9, p.getPaisNacimiento());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HistoriaClinica obtenerHistoriaClinica(int codigoHistoria) {
        HistoriaClinica historiaClinica = null;
        String sql = "SELECT Imagen, Fecha, TipoPrueba, Diagnostico, Descripcion, MotivoConsulta, ExploracionFisica, Tratamiento " +
                "FROM HistoriaClinica WHERE Codigo = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoHistoria);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                historiaClinica = new HistoriaClinica(
                        rs.getString("Imagen"),
                        rs.getString("Fecha"),
                        rs.getString("TipoPrueba"),
                        rs.getString("Diagnostico"),
                        rs.getString("Descripcion"),
                        rs.getString("MotivoConsulta"),
                        rs.getString("ExploracionFisica"),
                        rs.getString("Tratamiento")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historiaClinica;
    }

    public void pedirCita(String dniPaciente, String dniTrabajador, String hora, String dia, String comentario) {
        String sql = "INSERT INTO Cita (Paciente, Trabajador, Hora, Fecha, Comentario) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dniPaciente);
            stmt.setString(2, dniTrabajador);
            stmt.setTime(3, java.sql.Time.valueOf(hora + ":00")); // Convierte la hora a formato TIME
            stmt.setDate(4, java.sql.Date.valueOf(dia));
            if (comentario == null) {
                stmt.setString(5, "Sin comentarios");
            } else {
                stmt.setString(5, comentario);
            }
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Cita agendada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agendar la cita.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo agendar la cita. Verifique los datos.");
        }
    }


    public int obtenerCodigoCitaPaciente(String doctor, Time hora, String fecha) {
        int codigoCita = -1;
        String sql = "SELECT c.Codigo FROM Cita c " +
                "JOIN Trabajador t ON c.Trabajador = t.DNI " +
                "WHERE t.DNI = ? AND c.Hora = ? AND c.Fecha = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctor);
            stmt.setTime(2, hora);
            stmt.setDate(3, java.sql.Date.valueOf(fecha));


            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                codigoCita = rs.getInt("Codigo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigoCita;
    }

    public int obtenerCodigoCitaTrabajador(String paciente, Time hora, String fecha) {
        int codigoCita = -1;
        String sql = "SELECT c.Codigo FROM Cita c " +
                "JOIN Paciente p ON c.Paciente = p.DNI " +
                "WHERE p.DNI = ? AND c.Hora = ? AND c.Fecha = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente);
            stmt.setTime(2, hora);
            stmt.setDate(3, java.sql.Date.valueOf(fecha));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                codigoCita = rs.getInt("Codigo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigoCita;
    }

    public String obtenerComentarioCita(int codigoCita) {
        String comentario = null;
        String sql = "SELECT Comentario FROM Cita WHERE Codigo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoCita);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                comentario = rs.getString("Comentario");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comentario;
    }

    public void actualizarComentarioCita(int codigoCita, String comentario) {
        String sql = "UPDATE Cita SET Comentario = ? WHERE Codigo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, comentario);
            stmt.setInt(2, codigoCita);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {

                System.out.println("Comentario actualizado correctamente.");
            } else {
                System.out.println(codigoCita);
                System.out.println("No se encontró la cita con el código especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String obtenerNombrePorDniPaciente(String dni) {
        String nombre = null;
        String sql = "SELECT Nombre FROM Paciente WHERE DNI = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("Nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombre;
    }

    public String obtenerNombrePorDniTrabajador(String dni) {
        String nombre = null;
        String sql = "SELECT Nombre FROM trabajador WHERE DNI = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("Nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombre;
    }

    public String obtenerTipoUsuario(String dni) {
        String tipo = "Desconocido"; // Valor por defecto si no se encuentra

        String sql = "SELECT Rol FROM Usuario WHERE DNI = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tipo = rs.getString("Rol"); // Retorna "Paciente" o "Trabajador"
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipo;
    }

    public void anularCitaPaciente(String dniTrabajador, Time hora, Date fecha) {
        String sql = "DELETE FROM Cita WHERE Trabajador = ? AND Hora = ? AND Fecha = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dniTrabajador);
            stmt.setTime(2, hora);
            stmt.setDate(3, fecha);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Cita anulada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la cita para anular.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al anular la cita.");
        }
    }

    public void guardarRutaImagenPaciente(String dni, String rutaImagen) {
        String sql = "UPDATE Paciente SET FotoPerfil = ? WHERE DNI = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rutaImagen);
            stmt.setString(2, dni);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String obtenerRutaImagenPaciente(String dni) {
        String rutaImagen = null;
        String sql = "SELECT FotoPerfil FROM Paciente WHERE DNI = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rutaImagen = rs.getString("FotoPerfil");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rutaImagen;
    }

    public String obtenerRutaImagenHistoriaClinica(int codigo) {
        String rutaImagen = null;
        String sql = "SELECT Imagen FROM HistoriaClinica WHERE Codigo = ? ";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rutaImagen = rs.getString("Imagen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rutaImagen;
    }

    public List<Trabajador> obtenerListaTrabajadores() {
        List<Trabajador> trabajadores = null;
        String sql = "SELECT * FROM Trabajador";
        trabajadores = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Trabajador trabajador = new Trabajador(rs.getString("Nombre"), rs.getString("DNI"), rs.getString("Sexo"), Especialidad.valueOf(rs.getString("Especialidad")), rs.getString("FotoPerfil"));

                trabajadores.add(trabajador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trabajadores;
    }

    public boolean existsEmail(String email) {
        String sql = "SELECT * FROM Paciente where Email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    // Otros métodos de la clase...

    public String obtenerDNI(String email) {
        String dni = null;
        String query = "SELECT DNI FROM Paciente WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    dni = rs.getString("DNI");
                }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dni;
    }

    public String obtenerContraseñaPorEmail(String email){
        String dni = null;
        String sql = "SELECT u.Contraseña FROM Usuario u JOIN Paciente p ON u.DNI = p.DNI WHERE p.Email = ?";
        try (PreparedStatement stmt = conn.prepareCall(sql)){
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dni = rs.getString("Contraseña");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dni;
    }
    public void cambiaContraseña(String contraseña, String dni) {
        String sql = "UPDATE Usuario SET Contraseña = ? WHERE DNI = ?";
        try (PreparedStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, contraseña);
            stmt.setString(2, dni);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String obtenerRutaImagenTrabajador(String dniTrabajador) {
        String rutaImagen = null;
        String sql = "SELECT FotoPerfil FROM Trabajador WHERE DNI = ? ";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dniTrabajador);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rutaImagen = rs.getString("FotoPerfil");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rutaImagen;
    }

    public TarjetaIdentificacion obtenerDatosTarjetaIdentificacion(String dni) {
        TarjetaIdentificacion tarjeta = null;
        String sql = "SELECT p.Nombre,p.Email, p.Sexo, p.Telefono, p.Seguro, p.Domicilio, p.PaisNacimiento, " +
                "s.Compañia, s.Cobertura, p.FotoPerfil " +
                "FROM Paciente p JOIN Seguro s ON p.DNI = s.Paciente WHERE p.DNI = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tarjeta = new TarjetaIdentificacion(
                        rs.getString("Nombre"),
                        dni,
                        rs.getString("Email"),
                        rs.getString("Sexo"),
                        rs.getString("Telefono"),
                        rs.getString("Seguro"),
                        rs.getString("Domicilio"),
                        rs.getString("PaisNacimiento"),
                        rs.getString("Compañia"),
                        rs.getString("Cobertura"),
                        rs.getString("FotoPerfil")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarjeta;
    }

    public int obtenerCodigoHistoriaClinica(String dniPaciente, String dniTrabajador, String diagnostico) {
        int codigoHistoria = -1;
        String sql = "SELECT Codigo FROM HistoriaClinica WHERE Paciente = ? AND Trabajador = ? AND Diagnostico = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dniPaciente);
            stmt.setString(2, dniTrabajador);
            stmt.setString(3, diagnostico);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                codigoHistoria = rs.getInt("Codigo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return codigoHistoria; // Retorna -1 si no encuentra el código
    }

    public List<String[]> obtenerHistorialClinicoPorDni(String dni) {
        List<String[]> historial = new ArrayList<>();
        String sql = "SELECT t.Nombre AS Trabajador, h.Diagnostico " +
                "FROM HistoriaClinica h " +
                "JOIN Trabajador t ON h.Trabajador = t.DNI " +
                "WHERE h.Paciente = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                historial.add(new String[]{
                        rs.getString("Trabajador"),
                        rs.getString("Diagnostico")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }

    public void anularCitaTrabajador(String dniTrabajador, String dniPaciente, Time hora, Date fecha) {
        String sql = "DELETE FROM Cita WHERE Trabajador = ? AND Paciente = ? AND Hora = ? AND Fecha = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dniTrabajador);
            stmt.setString(2, dniPaciente);
            stmt.setTime(3, hora);
            stmt.setDate(4, fecha);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Cita anulada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la cita para anular.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al anular la cita.");
        }
    }

    public void guardarRutaImagenHistoriaClinica(int codigoHistoria, String rutaImagen) {
        String sql = "UPDATE HistoriaClinica SET Imagen = ? WHERE Codigo = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rutaImagen);
            stmt.setInt(2, codigoHistoria);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void guardarImagenPaciente(String dni, FileInputStream imagen, int tamaño) {
        String sql = "UPDATE Paciente SET FotoPerfil = ? WHERE DNI = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBinaryStream(1, imagen, tamaño);
            stmt.setString(2, dni);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> obtenerListaPacientes() {
        List<String[]> pacientes = new ArrayList<>();
        String sql = "SELECT Nombre, DNI FROM Paciente";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pacientes.add(new String[]{
                        rs.getString("Nombre"),
                        rs.getString("DNI")
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pacientes;
    }

    public List<String[]> obtenerCitasPorDniSinEspecialidad(String dni) {
        List<String[]> citas = new ArrayList<>();
        String sql = "SELECT p.Nombre AS NombrePaciente, c.Hora, c.Fecha " +
                "FROM Cita c JOIN Paciente p ON c.Paciente = p.DNI " +
                "WHERE c.Trabajador = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citas.add(new String[]{
                        rs.getString("NombrePaciente"),
                        rs.getString("Hora"),
                        rs.getString("Fecha")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // Filtra las citas del paciente según la fecha (formato "yyyy-MM-dd")
    public List<String[]> obtenerCitasPacientePorFecha(String dni, String fecha) {
        List<String[]> citas = new ArrayList<>();
        String sql = "SELECT t.Nombre AS NombreTrabajador, c.Hora, c.Fecha, t.Especialidad " +
                "FROM Cita c JOIN Trabajador t ON c.Trabajador = t.DNI " +
                "WHERE c.Paciente = ? AND c.Fecha = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.setString(2, fecha);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citas.add(new String[]{
                        rs.getString("NombreTrabajador"),
                        rs.getString("Hora"),
                        rs.getString("Fecha"),
                        rs.getString("Especialidad")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // Filtra las citas del paciente según el nombre del doctor
    public List<String[]> obtenerCitasPacientePorDoctor(String dni, String doctor) {
        List<String[]> citas = new ArrayList<>();
        String sql = "SELECT t.Nombre AS NombreTrabajador, c.Hora, c.Fecha, t.Especialidad " +
                "FROM Cita c JOIN Trabajador t ON c.Trabajador = t.DNI " +
                "WHERE c.Paciente = ? AND t.Nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.setString(2, doctor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citas.add(new String[]{
                        rs.getString("NombreTrabajador"),
                        rs.getString("Hora"),
                        rs.getString("Fecha"),
                        rs.getString("Especialidad")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // Filtra las citas del paciente según la especialidad del doctor
    public List<String[]> obtenerCitasPacientePorEspecialidad(String dni, String especialidad) {
        List<String[]> citas = new ArrayList<>();
        String sql = "SELECT t.Nombre AS NombreTrabajador, c.Hora, c.Fecha, t.Especialidad " +
                "FROM Cita c JOIN Trabajador t ON c.Trabajador = t.DNI " +
                "WHERE c.Paciente = ? AND t.Especialidad = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.setString(2, especialidad);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citas.add(new String[]{
                        rs.getString("NombreTrabajador"),
                        rs.getString("Hora"),
                        rs.getString("Fecha"),
                        rs.getString("Especialidad")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    public List<String[]> obtenerCitasFiltradasSinEspecialidad(String dni, String criterio) {
        List<String[]> citas = new ArrayList<>();
        String campoOrden = criterio.equals("Día") ? "c.Fecha" : "p.Nombre";

        String sql = "SELECT p.Nombre AS NombrePaciente, c.Hora, c.Fecha " +
                "FROM Cita c JOIN Paciente p ON c.Paciente = p.DNI " +
                "WHERE c.Trabajador = ? ORDER BY " + campoOrden;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citas.add(new String[]{
                        rs.getString("NombrePaciente"),
                        rs.getString("Hora"),
                        rs.getString("Fecha")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    public List<String[]> obtenerCitasPorDniConEspecialidad(String dni) {
        List<String[]> citas = new ArrayList<>();
        String sql = "SELECT t.Nombre AS NombreTrabajador, c.Hora, c.Fecha, t.Especialidad " +
                "FROM Cita c JOIN Trabajador t ON c.Trabajador = t.DNI " +
                "WHERE c.Paciente = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citas.add(new String[]{
                        rs.getString("NombreTrabajador"),  // Ahora devuelve el nombre en vez del DNI
                        rs.getString("Hora"),
                        rs.getString("Fecha"),
                        rs.getString("Especialidad")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }
}