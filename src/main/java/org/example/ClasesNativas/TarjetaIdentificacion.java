package org.example.ClasesNativas;

public class TarjetaIdentificacion {
    private String nombre;
    private String dni;
    private String email;
    private String sexo;
    private String telefono;
    private String seguro;
    private String domicilio;
    private String paisNacimiento;
    private String compania;
    private String cobertura;
    private String fotoPerfil;

    public TarjetaIdentificacion(String nombre,String dni, String email, String sexo, String telefono, String seguro,
                                 String domicilio, String paisNacimiento, String compania, String cobertura,
                                 String fotoPerfil) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.sexo = sexo;
        this.telefono = telefono;
        this.seguro = seguro;
        this.domicilio = domicilio;
        this.paisNacimiento = paisNacimiento;
        this.compania = compania;
        this.cobertura = cobertura;
        this.fotoPerfil = fotoPerfil;
    }

    public String getNombre() {
        return nombre;
    }
    public String getDni() { return dni; }
    public String getEmail() { return email; }
    public String getSexo() { return sexo; }
    public String getTelefono() { return telefono; }
    public String getSeguro() { return seguro; }
    public String getDomicilio() { return domicilio; }
    public String getPaisNacimiento() { return paisNacimiento; }
    public String getCompania() { return compania; }
    public String getCobertura() { return cobertura; }
    public String getFotoPerfil() { return fotoPerfil; }
}
