package examen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class AcademiaDAOTest {

    AcademiaDAO dao;

    @BeforeEach
    void setUp() {
        String ruta_scrip = "src/main/java/examen/script_datos.sql";
        borrarDatos();
        volcarDatos(ruta_scrip);

        dao = new AcademiaDAO("localhost", "examenacademia", "root", "");
    }
    //EJERCICIO 1
    @Test
    void testEvaluar() {
        dao.evaluar(16, "María García","Desarrollar programa en Python","2023-12-12",7.6);
    }

    //EJERCICIO 2
    @Test
    void testMejor() {
        String valor_esperado = "Alumno con mejor puntuación: María García";

        assertEquals(valor_esperado, dao.mejorExpediente());
    }

    //EJERCICIO 3
    @Test
    void testBorrarTareas() {dao.borrarTareas(11,2023);}

    //EJERCICIO 4
    @Test
    void testVagos() {
        String valor_esperado = "Los alumnos que no han entregado tareas son: Pedro Rodríguez";

        assertEquals(valor_esperado, dao.alumnosVagos());
    }

    //EJERCICIO 5
    @Test
    void borrarSinUtilizar()
    {
        dao.borrarSinUtilizar();
    }

    //EJERCICIO 6
    @Test
    void testPeor() {
        double valor_esperado = 4.7;

        assertEquals(valor_esperado, dao.peorTarea("Preparar presentación oral en inglés"));
    }

    //EJERCICIO 7
    @Test
    void testResumen() {
        String valor_esperado=
                "======================\n" +
                "ASIGNATURA:Matemáticas\n" +
                "TAREAS:\n" +
                "Resolver ecuaciones lineales-->9.3\n" +
                "Estudio de funciones cuadráticas-->1.6\n"+
                "Resolución de problemas trigonométricos-->8.6\n"+
                "======================\n" +
                "ASIGNATURA:Historia\n" +
                "TAREAS:\n" +
                "Sin Tareas\n"+
                "======================\n" +
                "ASIGNATURA:Ciencias Naturales\n" +
                "TAREAS:\n" +
                "Realizar experimento de biología-->5.7\n"+
                "Investigación sobre la célula->4.9\n"+
                "Análisis de ecosistemas locales-->6.8\n"+
                "======================\n" +
                "ASIGNATURA:Inglés\n" +
                "TAREAS:\n" +
                "Preparar presentación oral en inglés-->4.7\n"+
                "======================\n" +
                "ASIGNATURA:Programación\n" +
                "TAREAS:\n" +
                "Desarrollar programa en Python-->3.3\n"+
                "Desarrollo de aplicación web-->2.8\n";

        assertEquals(valor_esperado,dao.resumenAsignaturas());
    }



    void volcarDatos(String ruta_script) {
        Connection conexion = null;
        Statement statement = null;

        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/examenacademia",
                    "root", //usuario de la BD
                    ""); //contraseña

            BufferedReader reader = new BufferedReader(new FileReader(ruta_script));
            statement = conexion.createStatement();
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                statement.executeUpdate(line);
            }
            reader.close();

        } catch (SQLException exception) {
            System.out.println("Error de SQL\n" + exception.getMessage());
            exception.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("No existe el fichero\n" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro del fichero\n" + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void borrarDatos() {
        Connection conexion = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Statement tablas_statement = null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/examenacademia",
                    "root", //usuario de la BD
                    ""); //contraseña

            statement = conexion.createStatement();

            tablas_statement=conexion.createStatement();
            resultSet = tablas_statement.executeQuery("SHOW TABLES");

            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                // Eliminar la tabla
                statement.executeUpdate("DELETE FROM " + tableName);
                System.out.println("Tabla " + tableName + " vaciada.");
            }

        } catch (SQLException exception) {
            System.out.println("Error de SQL\n" + exception.getMessage());
            exception.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
                if (resultSet != null) resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}