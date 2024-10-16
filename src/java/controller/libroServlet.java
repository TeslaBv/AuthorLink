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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Libro;

/**
 *
 * @author CruzF
 */
@WebServlet(name = "libroServlet", urlPatterns = {"/libro_servlet"})
public class libroServlet extends HttpServlet {

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
            out.println("<title>Servlet libroServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet libroServlet at " + request.getContextPath() + "</h1>");
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
            
            conn = conexion.getConnectionBD();

            String sql = "SELECT * FROM libro";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);

            List<Libro> libros = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("idLibro");
                String titulo = rs.getString("titulo");
                java.sql.Date fechaPublicacion = rs.getDate("fecha_publicacion");
                double precio = rs.getDouble("precio");
                boolean disponible = rs.getBoolean("disponible");
                int cantidad = rs.getInt("cantidad");

                Libro libro = new Libro(id, titulo, fechaPublicacion, precio, disponible, cantidad);
                libro.toString();
                libros.add(libro);
            }

            rs.close();
            statement.close();

            request.setAttribute("libros", libros);

            
            request.getRequestDispatcher("jsp/verLibros.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); 
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        System.out.println("Entro al doPost");
        ConnectionBD conexion = new ConnectionBD();

        // Obtener los parámetros del formulario 
        String titulo = request.getParameter("titulo");
        String fechaPub = request.getParameter("fechaPub");//Tipo Date
        String price = request.getParameter("precio");//Tipo double
        String disp = request.getParameter("disponible");//Tipo boolean
        String cant = request.getParameter("cantidad");//Tipo boolean

        java.sql.Date fechaPublicacion = java.sql.Date.valueOf(fechaPub);
        Double precio = Double.parseDouble(price);
        Boolean disponible = Boolean.parseBoolean(disp);
        int cantidad = Integer.parseInt(cant);

        try {
            String sql = "INSERT INTO libro (titulo, fecha_publicacion, precio, disponible,cantidad) VALUES (?, ?, ?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setDate(2, fechaPublicacion);
            ps.setDouble(3, precio);
            ps.setBoolean(4, disponible);
            ps.setInt(5, cantidad);

            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Libro insertado correctamente");
                response.sendRedirect(request.getContextPath() + "/index.html");
            } else {

            }
        } catch (Exception e) {
        }

    }

    @Override
    public String getServletInfo() {
        return "Servlet para la creacion de libros";
    }// </editor-fold>

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionBD conexion = new ConnectionBD();
        String id = request.getParameter("id");
        PreparedStatement ps = null;
        Connection conn = null;

        // Validate input
        if (id == null || id.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Invalid request
            return;
        }

        String sql = "DELETE FROM libro WHERE idLibro = ?";
        String sql2 = "DELETE FROM libroautor WHERE idLibro = ?";

        try {
            // Conectar y eliminar de la tabla 'libroautor' primero
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql2);
            ps.setString(1, id);
            int rowsAffected2 = ps.executeUpdate();

            // Cerrar el PreparedStatement antes de ejecutar la segunda sentencia
            ps.close();

            // Luego eliminar de la tabla 'libro'
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK); // Eliminar exitoso 
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // No se encontró el libro
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Error del servidor 
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
