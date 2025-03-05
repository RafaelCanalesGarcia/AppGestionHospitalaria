package org.example.Registro;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
// clase para cargar la configuracion que hay en config.properties donde hay datos sensibles del correo
public class CargaConfiguracion {
    private static Properties properties;

    static {
        try (InputStream input = CargaConfiguracion.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties = new Properties();
            if (input == null) {
                throw new IOException("Archivo 'config.properties' no encontrado.");
            }

            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el archivo de configuración.");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
