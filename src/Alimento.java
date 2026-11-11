import java.io.InputStream;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;

public class Alimento {

    static String todos = "SELECT * FROM alimentos ORDER BY idalimentos";
    static String ids = "SELECT idalimentos FROM alimentos ORDER BY idalimentos";

    int idalimentos;
    String nombre, descripcion, categoria, estado;
    float precio;
    byte[] foto;

    public Alimento() {
    }

    // Constructor que acepta los tipos de datos que vienen de la interfaz (String e ImageIcon)
    public Alimento(String idalimentos, String nombre, String descripcion, String precio, String categoria, String estado, ImageIcon foto, Conexion cnx) {
        try {
            this.idalimentos = Integer.parseInt(idalimentos.trim());
        } catch (Exception e) {
            this.idalimentos = 0;
        }

        this.nombre = nombre;
        this.descripcion = descripcion;

        try {
            this.precio = Float.parseFloat(precio.trim());
        } catch (Exception e) {
            this.precio = 0.0f;
        }

        this.categoria = categoria;
        this.estado = estado;
        
        // Convertir la ImageIcon a byte[] usando la función de tu clase Conexion
        this.foto = convertirImageIconABytes(foto, cnx);
    }

    // Método auxiliar para obtener byte[] desde la clase Conexion
    private byte[] convertirImageIconABytes(ImageIcon imagen, Conexion cnx) {
        if (imagen == null || cnx == null) return null;
        try {
            InputStream is = cnx.ImagenBytes(imagen);
            if (is != null) {
                return is.readAllBytes();
            }
        } catch (Exception e) {
            System.out.println("Error convirtiendo foto a bytes: " + e.getMessage());
        }
        return null;
    }

    // Acepta directamente tu clase Conexion
    public int insertar(Conexion cnx) {
        int resultado = 0;
        try {
            String sql = "INSERT INTO alimentos VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.con.prepareStatement(sql);
            ps.setInt(1, idalimentos);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setFloat(4, precio);
            ps.setBytes(5, foto);
            ps.setString(6, categoria);
            ps.setString(7, estado);
            return ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return resultado;
        }
    }

    public int borrar(Conexion cnx) {
        int resultado = 0;
        try {
            String sql = "DELETE FROM alimentos WHERE idalimentos = ?";
            PreparedStatement ps = cnx.con.prepareStatement(sql);
            ps.setInt(1, idalimentos);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return resultado;
        }
    }

    public int actualizar(Conexion cnx) {
        int resultado = 0;
        try {
            String sql = "UPDATE alimentos SET nombre = ?, descripcion = ?, precio = ?, foto = ?, categoria = ?, estado = ? WHERE idalimentos = ?";
            PreparedStatement ps = cnx.con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setFloat(3, precio);
            ps.setBytes(4, foto);
            ps.setString(5, categoria);
            ps.setString(6, estado);
            ps.setInt(7, idalimentos);
            return ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return resultado;
        }
    }
}