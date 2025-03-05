package org.example.ClasesNativas;

import org.example.ClasesNativas.Enums.Cobertura;
import org.example.ClasesNativas.Enums.Compania;
import org.example.Database.DatabaseConnection;

import java.sql.Connection;

public class Seguro {
    private String titular;
    private String numeroPoliza;
    private Compania compania;
    private Cobertura cobertura;

    public Seguro(String dniPaciente, String numeroPoliza, String compania, String cobertura) {
        this.titular = dniPaciente;
        this.numeroPoliza = numeroPoliza;
        this.compania = Compania.valueOf(compania);
        this.cobertura = Cobertura.valueOf(cobertura);
    }

    public String getNumeroPoliza() {
        return numeroPoliza;
    }

    public void setNumeroPoliza(String numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }



    // Método para guardar un seguro en la base de datos


    public Compania getCompania() {
        return compania;
    }

    public void setCompania(Compania compania) {
        this.compania = compania;
    }

    public Cobertura getCobertura() {
        return cobertura;
    }

    public void setCobertura(Cobertura cobertura) {
        this.cobertura = cobertura;
    }

    public String getTitular() {
        return titular;
    }
}
