package org.example.ClasesNativas;

public class HistoriaClinica {
    private String imagen;
    private String fecha;
    private String tipoPrueba;
    private String diagnostico;
    private String descripcion;
    private String motivoConsulta;
    private String exploracionFisica;
    private String tratamiento;

    public HistoriaClinica(String imagen, String fecha, String tipoPrueba, String diagnostico,
                           String descripcion, String motivoConsulta, String exploracionFisica, String tratamiento) {
        this.imagen = imagen;
        this.fecha = fecha;
        this.tipoPrueba = tipoPrueba;
        this.diagnostico = diagnostico;
        this.descripcion = descripcion;
        this.motivoConsulta = motivoConsulta;
        this.exploracionFisica = exploracionFisica;
        this.tratamiento = tratamiento;
    }

    public String getImagen() { return imagen; }
    public String getFecha() { return fecha; }
    public String getTipoPrueba() { return tipoPrueba; }
    public String getDiagnostico() { return diagnostico; }
    public String getDescripcion() { return descripcion; }
    public String getMotivoConsulta() { return motivoConsulta; }
    public String getExploracionFisica() { return exploracionFisica; }
    public String getTratamiento() { return tratamiento; }
}
