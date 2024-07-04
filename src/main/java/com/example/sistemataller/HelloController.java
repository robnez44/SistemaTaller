package com.example.sistemataller;

import com.example.sistemataller.Clases.Categoria;
import com.example.sistemataller.Clases.Empleado;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    @FXML TextField txtPrecio;

    @FXML
    private Button btnAgregarTarea;

    @FXML
    private Button btnLimpiarTarea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

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

}