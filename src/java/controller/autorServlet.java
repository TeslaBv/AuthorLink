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
import model.Autor;

/**
 *
 * @author CruzF
 */
@WebServlet(name = "autorServlet", urlPatterns = {"/autor_servlet"})
public class autorServlet extends HttpServlet {

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
            out.println("<title>Servlet autorServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet autorServlet at " + request.getContextPath() + "</h1>");
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

            // Realizar la consulta para obtener todos los autores
            String sql = "SELECT * FROM autor";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);

            // Crear una lista para almacenar los autores
            List<Autor> autores = new ArrayList<>();

            // Recorrer los resultados y agregar los autores a la lista
            while (rs.next()) {
                int idAutor = rs.getInt("idAutor");
                String nombre = rs.getString("nombre");
                String nacionalidad = rs.getString("nacionalidad");
                java.sql.Date fechaNacimiento = rs.getDate("fecha_nacimiento");

                Autor autor = new Autor(idAutor, nombre, nacionalidad, fechaNacimiento);
                autores.add(autor);
            }
            // Cerrar el ResultSet y Statement
            rs.close();
            statement.close();

            // Almacenar la lista de autores en el request
            request.setAttribute("autores", autores);

            // Redirigir a la página JSP para mostrar los autores
            request.getRequestDispatcher("jsp/verAutor.jsp").forward(request, response);

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

        ConnectionBD conexion = new ConnectionBD();

        // Obtener los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String nacionalidad = request.getParameter("nacionalidad");
        String fechaNac = request.getParameter("fechaNacimiento"); // Tipo Date

        java.sql.Date fechaNacimiento = java.sql.Date.valueOf(fechaNac);

        try {
            // SQL para insertar el autor en la base de datos
            String sql = "INSERT INTO autor (nombre, nacionalidad, fecha_nacimiento) VALUES (?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, nacionalidad);
            ps.setDate(3, fechaNacimiento);

            // Ejecutar la inserción
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Autor insertado correctamente");
                response.sendRedirect(request.getContextPath() + "/index.html");
            } else {
                response.getWriter().println("Error al insertar el autor.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
