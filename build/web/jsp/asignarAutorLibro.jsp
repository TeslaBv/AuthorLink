<%-- 
    Document   : asignarAutorLibro
    Created on : 14/10/2024, 08:57:35 PM
    Author     : CruzF
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="configuration.ConnectionBD"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Autor" %>
<%@ page import="model.Libro" %>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Asignar Autor a Libro</title>
        <!-- Enlace a Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <%
            Connection conn;
            PreparedStatement ps;
            Statement statement;
            ResultSet rs;
        %>
        <div class="container mt-5">
            <h1 class="text-center">Asignar Autor a Libro</h1>
            <form action="${pageContext.request.contextPath}/autor_libro_Servlet" method="post">

                <!-- Combobox para seleccionar Libro -->
                <div class="form-group">
                    <label for="idLibro">Seleccionar Libro:</label>
                    <select class="form-control" id="idLibro" name="idLibro" required>
                        <option value="">Seleccione un libro</option>
                        <%
                            ConnectionBD conexion = new ConnectionBD();
                            conn = conexion.getConnectionBD();
                            String sql = "SELECT idLibro,titulo FROM libro";
                            statement = conn.createStatement();
                            rs = statement.executeQuery(sql);
                            
                            List<Libro> libros = new ArrayList<>();
                            while (rs.next()) {
                                int id = rs.getInt("idLibro");
                                String titulo = rs.getString("titulo");
                                
                                Libro libro = new Libro(id, titulo);
                                libro.toString();
                                libros.add(libro);
                            }
                            // Obtener la lista de libros (esta debe estar asignada previamente en el request)
                            
                            for (Libro libro : libros) {
                        %>
                        <option value="<%= libro.getId()%>"><%= libro.getTitulo()%> (ID: <%= libro.getId()%>)</option>
                        <%
                            }
                        %>
                    </select>
                </div>

                <!-- Combobox para seleccionar Autor -->
                <div class="form-group mt-3">
                    <label for="idAutor">Seleccionar Autor:</label>
                    <select class="form-control" id="idAutor" name="idAutor" required>
                        <option value="">Seleccione un autor</option>
                        <%
                            
                            conn = conexion.getConnectionBD();
                            String sql2 = "SELECT idAutor,nombre FROM autor";
                            statement = conn.createStatement();
                            rs = statement.executeQuery(sql2);
                            
                            List<Autor> autores = new ArrayList<>();
                            while (rs.next()) {
                                int id = rs.getInt("idAutor");
                                String nombre = rs.getString("nombre");
                                
                                Autor autor = new Autor(id, nombre);
                                autor.toString();
                                autores.add(autor);
                            }
                            for (Autor autor : autores) {
                        %>
                        <option value="<%= autor.getIdAutor()%>"><%= autor.getNombre()%> (ID: <%= autor.getIdAutor()%>)</option>
                        <%
                            }
                        %>
                    </select>
                </div>

                <!-- Campo para el rol del autor -->
                <div class="form-group mt-3">
                    <label for="rol">Rol del Autor:</label>
                    <input type="text" class="form-control" id="rol" name="rol" required>
                </div>

                <!-- Botón para enviar el formulario -->
                <button type="submit" class="btn btn-primary mt-4">Asignar Autor a Libro</button>
            </form>
        </div>

        <!-- Enlace a Bootstrap JS y dependencias (opcional) -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
