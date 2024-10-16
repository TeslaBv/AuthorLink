<%--      Document   : registrarLibro     Created on : 14/10/2024, 08:56:29 PM     Author     : CruzF --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Libro</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h1>Registrar Libro</h1>
           <form action="${pageContext.request.contextPath}/libro_servlet" method="post">
                <div class="form-group">
                    <label for="titulo">Título:</label>
                    <input type="text" class="form-control" id="titulo" name="titulo" required>
                </div>
                
                <div class="form-group">
                    <label for="fechaPub">Fecha de Publicación:</label>
                    <input type="date" class="form-control" id="fechaPub" name="fechaPub" required>
                </div>
                
                <div class="form-group">
                    <label for="precio">Precio:</label>
                    <input type="number" step="0.01" class="form-control" id="precio" name="precio" required>
                </div>
                
                <div class="form-group">
                    <label for="disponible">Disponible:</label>
                    <select class="form-control" id="disponible" name="disponible">
                        <option value="true">Sí</option>
                        <option value="false">No</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="cantidad">Cantidad</label>
                    <input type="number" step="1" class="form-control" id="cantidad" name="cantidad" required>
                </div>
                
                <button type="submit" class="btn btn-primary">Registrar</button>
            </form>
        </div>
    </body>
</html>
