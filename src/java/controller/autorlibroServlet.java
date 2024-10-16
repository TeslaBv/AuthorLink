/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import configuration.ConnectionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Libro;
import model.LibroAutor;

/**
 *
 * @author CruzF
 */
@WebServlet(name = "autorlibroServlet", urlPatterns = {"/autor_libro_Servlet"})
public class autorlibroServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet autorlibroServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet autorlibroServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        ConnectionBD conexion = new ConnectionBD();
        try {
            // Conectar a la base de datos
            conn = conexion.getConnectionBD();

            String sql = "SELECT * FROM libroautor";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);

            List<LibroAutor> librosAutores = new ArrayList<>();

            // Recorrer los resultados y agregar los libros a la lista
            while (rs.next()) {
                int idLibro = rs.getInt("idLibro");
                int idAutor = rs.getInt("idAutor");
                String rol = rs.getString("rol");

                LibroAutor libroAutor = new LibroAutor(idLibro, idAutor,rol);
                libroAutor.toString();
                librosAutores.add(libroAutor);
            }

            // Cerrar el ResultSet y Statement
            rs.close();
            statement.close();

            // Almacenar la lista de libros en el request
            request.setAttribute("librosAutores", librosAutores);

            // Redirigir a la página JSP para mostrar los libros
            request.getRequestDispatcher("jsp/verLibroAutor.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); // Manejar la excepción de manera adecuada en producción
        } finally {
            // Asegurarse de cerrar la conexión
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        System.out.println("Entro al doPost");
        ConnectionBD conexion = new ConnectionBD();
        
        // Obtiene los parámetros del formulario
        int idLibro = Integer.parseInt(request.getParameter("idLibro"));
        int idAutor = Integer.parseInt(request.getParameter("idAutor"));
        String rol = request.getParameter("rol");


        try {
            
            // Query de inserción en la tabla libroautor
            String sql = "INSERT INTO libroautor (idLibro, idAutor, rol) VALUES (?, ?, ?)";
            conn = conexion.getConnectionBD();
            // Prepara la consulta
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idLibro);     // Asigna el valor del idLibro
            ps.setInt(2, idAutor);     // Asigna el valor del idAutor
            ps.setString(3, rol);      // Asigna el valor del rol

            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Libro insertado correctamente");
                response.sendRedirect(request.getContextPath() + "/index.html");
            } else {

            }

        } catch (SQLException e) {
            e.printStackTrace();
            // En caso de error, redirecciona a una página de error o muestra un mensaje
            response.sendRedirect("error.jsp?message=" + e.getMessage());

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
