package org.example.ClasesNativas;

import org.example.ClasesNativas.Enums.Especialidad;

public class Trabajador {
    private String nombre;
    private String DNI;
    private String sexo;
    private Especialidad especialidad;
    private String fotoPerfil;

    public Trabajador() {}
    public Trabajador(String nombre, String DNI, String sexo, Especialidad especialidad) {
        this.nombre = nombre;

        this.DNI = DNI;
        this.sexo = sexo;
        this.especialidad = especialidad;
        this.fotoPerfil = null;
    }
    public Trabajador(String nombre, String DNI, String sexo, Especialidad especialidad, String fotoPerfil) {
        this.nombre = nombre;

        this.DNI = DNI;
        this.sexo = sexo;
        this.especialidad = especialidad;
        this.fotoPerfil = fotoPerfil;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }



}
