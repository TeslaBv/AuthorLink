<%-- 
    Document   : verLibroAutor
    Created on : 15/10/2024, 08:36:35 PM
    Author     : CruzF
--%>

<%@page import="configuration.ConnectionBD"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.LibroAutor" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Autores y Libros</title>
        <!-- Enlace a Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <%
            Connection conn = null;
            PreparedStatement psLibro = null;
            PreparedStatement psAutor = null;
            ResultSet rsLibro = null;
            ResultSet rsAutor = null;

            ConnectionBD conexion = new ConnectionBD();
            conn = conexion.getConnectionBD();
        %>
        <div class="container mt-5">
            <h1 class="text-center">Libros y Autores</h1>
            
            <table class="table table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>Título del Libro</th>
                        <th>Nombre del Autor</th>
                        <th>Rol</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Obtener la lista de librosAutores desde el request
                        List<LibroAutor> librosAutores = (List<LibroAutor>) request.getAttribute("librosAutores");
                        if (librosAutores != null) {
                            for (LibroAutor libroAutor : librosAutores) {
                                
                                // Obtener el título del libro basado en idLibro
                                String tituloLibro = "";
                                String nombreAutor = "";

                                try {
                                    // Consulta para obtener el título del libro
                                    String sqlLibro = "SELECT titulo FROM libro WHERE idLibro = ?";
                                    psLibro = conn.prepareStatement(sqlLibro);
                                    psLibro.setInt(1, libroAutor.getIdLibro());
                                    rsLibro = psLibro.executeQuery();
                                    if (rsLibro.next()) {
                                        tituloLibro = rsLibro.getString("titulo");
                                    }

                                    // Consulta para obtener el nombre del autor
                                    String sqlAutor = "SELECT nombre FROM autor WHERE idAutor = ?";
                                    psAutor = conn.prepareStatement(sqlAutor);
                                    psAutor.setInt(1, libroAutor.getIdAutor());
                                    rsAutor = psAutor.executeQuery();
                                    if (rsAutor.next()) {
                                        nombreAutor = rsAutor.getString("nombre");
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace(); // Manejo de errores en desarrollo
                                } finally {
                                    if (rsLibro != null) rsLibro.close();
                                    if (rsAutor != null) rsAutor.close();
                                    if (psLibro != null) psLibro.close();
                                    if (psAutor != null) psAutor.close();
                                }
                    %>
                    <tr>
                        <td><%= tituloLibro %></td>
                        <td><%= nombreAutor %></td>
                        <td><%= libroAutor.getRol() %></td>
                    </tr>
                    <% 
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="3" class="text-center">No hay asignaciones de autores a libros.</td>
                    </tr>
                    <% 
                        }
                    %>
                </tbody>
            </table>
            
            <a href="index.html" class="btn btn-primary">Volver al Inicio</a>
        </div>

        <!-- Enlace a Bootstrap JS y dependencias (opcional) -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
