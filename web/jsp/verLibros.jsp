<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Libro" %>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Listado de Libros</title>
        <!-- Enlace a Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script>
            function eliminarLibro(id) {
                console.log(`eliminarLibro?Id=` + id);
                if (confirm("¿Estás seguro de que quieres eliminar este Libro?")) {
                    fetch(`libro_servlet?id=` + id, {
                        method: 'DELETE'
                    }).then(response => {
                        if (response.ok) {
                            alert('Libro eliminado exitosamente');
                            location.reload();
                        } else {
                            alert('Error al eliminar Libro');
                        }
                    }).catch(error => console.error('Error:', error));
                }
            }
        </script>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center">Listado de Libros</h1>
            <table class="table table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Fecha de Publicación</th>
                        <th>Precio</th>
                        <th>Disponible</th>
                        <th>Cantidad</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Libro> libros = (List<Libro>) request.getAttribute("libros");
                        for (Libro libro : libros) {
                    %>
                    <tr>
                        <td><%= libro.getId()%></td>
                        <td><%= libro.getTitulo()%></td>
                        <td><%= libro.getFechaPublicacion()%></td>
                        <td><%= libro.getPrecio()%></td>
                        <td><%= libro.isDisponible() ? "Sí" : "No"%></td>
                        <td><%= libro.getCantidad()%></td>
                        <td> <button style="background: transparent; border-color: green; color: green; display: block; border-radius: 20px; font-family: cursive" onclick="eliminarLibro(<%= libro.getId()%>)">Eliminar</button> </td>
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
