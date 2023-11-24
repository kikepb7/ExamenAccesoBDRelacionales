package examen;

import java.sql.*;

public class AcademiaDAO {
    private String host;
    private String base_datos;
    private String usuario;
    private String password;


    public AcademiaDAO(String host, String base_datos, String usuario, String password) {
        this.host = host;
        this.base_datos = base_datos;
        this.usuario = usuario;
        this.password = password;
    }

    public static void cerrarConexion(Connection conexion, PreparedStatement sentencia, ResultSet resultado) {
        try {
            if (resultado != null) resultado.close();

            if (sentencia != null) sentencia.close();

            if (conexion != null) conexion.close();

        } catch (SQLException exception) {
            System.out.println("Error al cerrar la conexión\n" + exception.getMessage());
            exception.printStackTrace();
        }
    }

    /*
    ------------------
    MÉTODOS AUXILIARES
    ------------------
     */
    private Integer returnId(Connection connection, String parameterValue, String tableName, String column) {
        PreparedStatement sentencia = null;
        ResultSet result = null;
        Integer id = null;

        try {
            String sql_id = "SELECT id FROM " + tableName +
                    " WHERE " + column + " = ?";

            sentencia = connection.prepareStatement(sql_id);
            sentencia.setString(1, parameterValue);
            result = sentencia.executeQuery();

            if(result.next()) {
                id = result.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            // Do not close the connection here
            cerrarConexion(null, sentencia, result);
        }
        return id;
    }

    //EJERCICIO 1
    public void evaluar(int idEvaluacion, String alumno,String tarea,String fecha_entrega,Double puntuacion) {

        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = establecerConexion(host, base_datos, usuario, password);

            // Obtenemos los ids tanto de alumno como de la tarea para comprobar si existen en la BD
            Integer idAlumno = returnId(conexion, alumno, "alumnos", "nombre");
            Integer idTarea = returnId(conexion, tarea, "tareas", "descripcion");

            if (idAlumno == null) {
                throw new RuntimeException("No existe el alumno con nombre: " + alumno);
            }

            if (idTarea == null) {
                throw new RuntimeException("No existe la tarea con descripción: " + tarea);
            } else {
                // Insertamos nueva evaluación
                String sql_evaluar = "INSERT INTO evaluacion " +
                        "VALUES (?, ?, ?, ?, ?)";

                sentencia = conexion.prepareStatement(sql_evaluar);
                sentencia.setInt(1, idEvaluacion);
                sentencia.setInt(2, idAlumno);
                sentencia.setInt(3, idTarea);
                sentencia.setString(4, fecha_entrega);
                sentencia.setDouble(5, puntuacion);

                int filasInsertadas = sentencia.executeUpdate();

                if (filasInsertadas > 0) {
                    System.out.println("Se ha añadido correctamente una nueva evaluación");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            // Do not close the connection here
            cerrarConexion(null, sentencia, resultado);
        }
    }

    //EJERCICIO 2
    public String mejorExpediente() {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        String res = "";

        try {
            conexion = establecerConexion(host, base_datos, usuario, password);

            String sql_mejorExpediente = "SELECT a.nombre AS nombre_alumno, AVG(e.puntuacion) AS media_evaluacion " +
                    "FROM alumnos a " +
                    "JOIN evaluacion e ON e.alumno_id = a.id " +
                    "GROUP BY a.id " +
                    "ORDER BY media_evaluacion DESC " +
                    "LIMIT 1";

            sentencia = conexion.prepareStatement(sql_mejorExpediente);

            resultado = sentencia.executeQuery();

            while(resultado.next()) {
                String nombreAlumno = resultado.getString("nombre_alumno");
                res = "Alumno con mejor puntuación: " + nombreAlumno;
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            // Do not close the connection here
            cerrarConexion(null, sentencia, resultado);
        }

        return res;
    }

    //EJERCICIO 3
    public void borrarTareas(Integer mes, Integer año) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = establecerConexion(host, base_datos, usuario, password);

            // Borramos primero las evaluaciones
            String sql_borrarEvaluaciones = "DELETE evaluacion FROM evaluacion " +
                    "JOIN tareas ON evaluacion.tarea_id = tareas.id " +
                    "WHERE MONTH(tareas.fecha_entrega) = ? AND YEAR(tareas.fecha_entrega) = ?";

            sentencia = conexion.prepareStatement(sql_borrarEvaluaciones);
            sentencia.setInt(1, mes);
            sentencia.setInt(2, año);

            sentencia.executeUpdate();

            // Una vez que hemos borrado las evaluaciones, pasamos a eliminar las tareas
            String sql_borrarTareas = "DELETE FROM tareas " +
                    "WHERE MONTH(tareas.fecha_entrega) = ? AND YEAR(tareas.fecha_entrega) = ?";

            sentencia = conexion.prepareStatement(sql_borrarTareas);

            sentencia.setInt(1, mes);
            sentencia.setInt(2, año);

            int filasEliminadas = sentencia.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Se han eliminado correctamente " + filasEliminadas + " filas.");
            } else {
                throw new RuntimeException("No se ha eliminado ninguna fila.");
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            cerrarConexion(conexion, sentencia, resultado);
        }
    }

    //EJERCICIO 4
    public String alumnosVagos() {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        StringBuilder res = new StringBuilder();

        try {
            conexion = establecerConexion(host, base_datos, usuario, password);

            // Mostramos los alumnos que no hayan realizado ninguna tarea aún
            String sql_alumnosVagos = "SELECT alumnos.nombre AS nombre_alumno " +
                    "FROM alumnos " +
                    "LEFT JOIN evaluacion ON alumnos.id = evaluacion.alumno_id " +
                    "WHERE evaluacion.alumno_id IS NULL";

            sentencia = conexion.prepareStatement(sql_alumnosVagos);

            resultado = sentencia.executeQuery();

            while(resultado.next()) {
                String nombreAlumno = resultado.getString("nombre_alumno");

                res.append("Los alumnos que no han entregado tareas son: ").append(nombreAlumno);
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            // Do not close the connection here
            cerrarConexion(null, sentencia, resultado);
        }

        return res.toString();
    }

    //EJERCICIO 5
    public void borrarSinUtilizar() {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = establecerConexion(host, base_datos, usuario, password);

            String sql_borrarSinUtilizar = "DELETE asignaturas " +
                    "FROM asignaturas " +
                    "LEFT JOIN tareas ON asignaturas.id = tareas.asignatura_id " +
                    "WHERE tareas.asignatura_id IS NULL";

            sentencia = conexion.prepareStatement(sql_borrarSinUtilizar);

            int filasEliminadas = sentencia.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Se han eliminado correctamente " + filasEliminadas + " filas.");
            } else {
                throw new RuntimeException("No se ha eliminado ningún registro.");
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            // Do not close the connection here
            cerrarConexion(null, sentencia, resultado);
        }
    }

    //EJERCICIO 6
    public Double peorTarea(String tarea) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        double res = 0.0;

        try {
            conexion = establecerConexion(host, base_datos, usuario, password);

            // Obtener la id de la tarea
            Integer idTarea = returnId(conexion, tarea, "tareas", "descripcion");

            if(idTarea != null) {
                // Consulta para obtener la peor nota de una tarea entregada por un alumno
                String sql_peorTarea = "SELECT MIN(e.puntuacion) AS peor_puntuacion " +
                        "FROM evaluacion e " +
                        "JOIN tareas t ON e.tarea_id = t.id " +
                        "WHERE t.id = ?";

                sentencia = conexion.prepareStatement(sql_peorTarea);
                sentencia.setInt(1, idTarea);

                resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    res = resultado.getDouble("peor_puntuacion");
                }
            } else {
                throw new RuntimeException("ID de la tarea es nulo para " + tarea);
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            // Do not close the connection here
            cerrarConexion(conexion, sentencia, resultado);
        }

        return res;
    }


    private String tareasAsignatura(String asignatura) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        StringBuilder res = new StringBuilder();

        try {
            conexion = establecerConexion(host, base_datos, usuario, password);

            // Obtener la id de la asignatura
            Integer idAsignatura = returnId(conexion, asignatura, "asignaturas", "nombre");

            if (idAsignatura != null) {
                // Consulta para obtener las tareas y sus puntuaciones para la asignatura dada
                String sql_tareasAsignatura = "SELECT t.descripcion AS descripcion, e.puntuacion AS puntuacion " +
                        "FROM tareas t " +
                        "LEFT JOIN evaluacion e ON t.id = e.tarea_id " +
                        "JOIN asignaturas a ON t.asignatura_id = a.id " +
                        "WHERE a.id = ?";

                sentencia = conexion.prepareStatement(sql_tareasAsignatura);
                sentencia.setInt(1, idAsignatura);

                resultado = sentencia.executeQuery();

                while (resultado.next()) {
                    String descripcionTarea = resultado.getString("descripcion");
                    double puntuacionTarea = resultado.getDouble("puntuacion");

                    res.append(descripcionTarea).append("-->").append(puntuacionTarea).append("\n");
                }
            } else {
                throw new RuntimeException("ID de la asignatura es nulo para " + asignatura);
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            // Do not close the connection here
            cerrarConexion(conexion, sentencia, resultado);
        }

        return res.toString();
    }


    //EJERCICIO 7
    public String resumenAsignaturas() {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        StringBuilder res = new StringBuilder();

        try {
            conexion = establecerConexion(host, base_datos, usuario, password);

            String sql_resumen = "SELECT nombre FROM asignaturas";

            sentencia = conexion.prepareStatement(sql_resumen);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                String nombreAsignatura = resultado.getString("nombre");

                res.append("======================\n")
                        .append("ASIGNATURA:").append(nombreAsignatura).append("\n")
                        .append("TAREAS:\n");

                // Cambia el nombre del método a tareasAsignatura
                String tareasAsignaturas = tareasAsignatura(nombreAsignatura);
                if(tareasAsignaturas.isEmpty()) {
                    res.append("Sin Tareas\n");
                } else {
                    res.append(tareasAsignaturas);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            // Do not close the connection here
            cerrarConexion(conexion, sentencia, resultado);
        }
        return res.toString();
    }


    //plantillas crear y cerrar conexion consultas preparadas
    public static Connection establecerConexion(String host, String base_datos, String usuario, String password) throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + host + "/" + base_datos, usuario, password);
    }

}
