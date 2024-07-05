package com.example.sistemataller;

import com.example.sistemataller.Clases.Categoria;
import com.example.sistemataller.Clases.Empleado;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public Label lblEmpAgregado;
    @FXML
    private TextField tfNombreEmp;

    @FXML
    private TextField tfApellidoEmp;

    @FXML
    private DatePicker dpFechaContEmp;

    @FXML
    private Button btnAgregarEmp;

    @FXML
    private Button btnLimpiarEmp;

    @FXML
    private ComboBox<Categoria> cbCategoriaAddTarea;

    @FXML
    private ComboBox<Empleado> cbAsignarEmpTarea;

    @FXML
    private DatePicker dtFechaAsignTarea;

    @FXML
    private TextArea txtDescp;

    @FXML
    private TextField txtPrecio;

    @FXML
    private Label lblTareaAgregada;

    @FXML
    private Button btnAgregarTarea;

    @FXML
    private Button btnLimpiarTarea;

    @FXML
    private TextField txtIdEmp;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private DatePicker dpFechaFin;

    private ObservableList<Categoria> categorias = FXCollections.observableArrayList();
    private ObservableList<Empleado> empleados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SistemaTaller", "rober", "12345");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Categorias");
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getString("NombreCategoria"), rs.getInt("ID"));
                categorias.add(categoria);
            }
            cbCategoriaAddTarea.setItems(categorias);

            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM Empleados");
            while (rs2.next()) {
                Empleado empleado = new Empleado(rs2.getString("Nombre"), rs2.getString("Apellido"), rs2.getInt("ID"), rs2.getDate("FechaContratacion"));
                empleados.add(empleado);
            }
            cbAsignarEmpTarea.setItems(empleados);

            conn.close();

        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la bd");
            e.printStackTrace();
        }
    }

    @FXML
    public void setBtnAgregarEmpleado() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SistemaTaller", "rober", "12345");

            String insertEmp = "insert into Empleados (Nombre, Apellido, FechaContratacion) values (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertEmp);

            String nombre = tfNombreEmp.getText().trim();
            String apellido = tfApellidoEmp.getText().trim();
            String fechaContratacion = dpFechaContEmp.getValue().toString();

            if (!nombre.isEmpty() && !apellido.isEmpty() && fechaContratacion != null) {
                ps.setString(1, nombre);
                ps.setString(2, apellido);
                ps.setString(3, fechaContratacion);
                ps.executeUpdate();

                lblEmpAgregado.setText("Empleado agregado correctamente!");
                limpiarEmp();
                tiempoLabel(lblEmpAgregado);
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la bd");
            e.printStackTrace();
        }
    }

    @FXML
    public void setBtnLimpiarEmp(){
        limpiarEmp();
    }

    // limpiar campos de vista agregar empleado
    private void limpiarEmp(){
        tfNombreEmp.setText("");
        tfApellidoEmp.setText("");
        dpFechaContEmp.setValue(null);
    }

    // label desparezca despues de 2 seg
    private void tiempoLabel(Label label){
        label.setVisible(true);
        Duration duration = Duration.seconds(2);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            label.setVisible(false);
        }));
        timeline.play();
    }

    @FXML
    public void setBtnAgregarTarea() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SistemaTaller", "rober", "12345");

            String insertTarea = "insert into Tareas (Descripcion, Precio, CategoriaID, EmpleadoID, FechaAsignacion) values (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertTarea);

            String descripcion = txtDescp.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int IDCategoria = obtenerTareaIDPorNombre(cbCategoriaAddTarea.getValue().toString());
            int IDEmp = obtenerEmpleadoIDPorNombre(cbAsignarEmpTarea.getValue().toString());
            String fechaAsignacion = dtFechaAsignTarea.getValue().toString();
            System.out.println(descripcion + " " + precio + " " +  IDCategoria + " " +  IDEmp + " " +  fechaAsignacion);

            if (!descripcion.isEmpty() && !fechaAsignacion.isEmpty() && IDCategoria != -1 && IDEmp != -1) {
                ps.setString(1, descripcion);
                ps.setDouble(2, precio);
                ps.setInt(3, IDCategoria);
                ps.setInt(4, IDEmp);
                ps.setString(5, fechaAsignacion);
                ps.executeUpdate();

                lblTareaAgregada.setText("Tarea agregada correctamente!");
                limpiarTarea();
                tiempoLabel(lblTareaAgregada);
            }
                conn.close();
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la bd");
            e.printStackTrace();
        }
    }

    private int obtenerTareaIDPorNombre(String nombre) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SistemaTaller", "rober", "12345");

            String consulta = "SELECT ID FROM Categorias WHERE NombreCategoria = ?";
            PreparedStatement ps = conn.prepareStatement(consulta);

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la bd");
            e.printStackTrace();
        }
        return -1;
    }

    private int obtenerEmpleadoIDPorNombre(String nombreCompleto) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SistemaTaller", "rober", "12345");

            String[] partesNombre = nombreCompleto.split(" ");

            String consulta = "SELECT ID FROM Empleados WHERE Nombre = ? and Apellido = ?";
            PreparedStatement ps = conn.prepareStatement(consulta);

            ps.setString(1, partesNombre[0]);
            ps.setString(2, partesNombre[1]);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la bd");
            e.printStackTrace();
        }
        return -1;
    }

    private void limpiarTarea(){
        cbCategoriaAddTarea.setValue(null);
        cbAsignarEmpTarea.setValue(null);
        txtDescp.setText("");
        txtPrecio.setText("");
        dtFechaAsignTarea.setValue(null);
    }

    @FXML
    public void setBtnLimpiarTarea(){
        limpiarTarea();
    }

    @FXML
    public void consultarTareasPorIDEmpleado(String fechaInicio, String fechaFin, int ID){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SistemaTaller", "rober", "12345");

            fechaInicio = dpFechaInicio.getValue().toString();
            fechaFin = dpFechaFin.getValue().toString();
            ID = Integer.parseInt(txtIdEmp.getText());

            if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()) {
                String query = "SELECT t.TareaID, c.NombreCategoria, t.Descripcion, t.Precio, CONCAT(e.Nombre, ' ', e.Apellido) as Empleado, t.FechaAsignacion " +
                        "FROM Tareas AS t " +
                        "INNER JOIN Empleados AS e ON e.ID = t.EmpleadoID " +
                        "INNER JOIN Categorias AS c ON c.ID = t.CategoriaID " +
                        "WHERE t.FechaAsignacion BETWEEN " + fechaInicio + " AND " + fechaFin +
                        " AND t.EmpleadoID = " + ID;

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
            }



            conn.close();
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la bd");
            e.printStackTrace();
        }
    }

}