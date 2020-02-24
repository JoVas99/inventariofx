/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.fx;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pos.bl.Producto;
import pos.bl.ProductosServicio;
import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class FormProductoController implements Initializable {
    
    @FXML
    private TableView tableView;
    
    @FXML
    private TableColumn<Producto, Integer> colId;
    
    @FXML
    private TableColumn<Producto, String> colDescripcion;
    
    @FXML
    private TableColumn colEditar;
    
    @FXML
    private TableColumn colEliminar;
    
    ObservableList<Producto> data;
    
    ProductosServicio servicio;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
       servicio=new ProductosServicio();
       colId.setCellValueFactory(new PropertyValueFactory<>("id"));
       colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
       
       definirColumnaEditar();
       definirColumnaEliminar();
       
       cargarDatos();
    }    
    public void nuevoProducto() throws IOException{
        Producto nuevoProducto=new Producto();
        abrirVentanaModal(nuevoProducto,"Nuevo Producto");
    }
    public void guardar(Producto producto){
        servicio.guardar(producto);
        cargarDatos();
    }

    private void abrirVentanaModal(Producto producto, String titulo) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("NuevoEditarProducto.fxml"));
        Parent root=(Parent) loader.load();
        
        NuevoEditarProductoController controller=loader.getController();
        controller.setController(this);
        controller.setProducto(producto);
        
        Stage stage=new Stage();
        
        Scene scene=new Scene(root);
        stage.setScene(scene);
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(titulo);
        stage.show();
    }

    private void cargarDatos() {
       data=FXCollections.observableArrayList(servicio.obtenerProductos());
       tableView.setItems(data);
       tableView.refresh();
    }

    private void definirColumnaEditar() {
        colEditar.setCellFactory(param -> new TableCell<String, String>() {
            final Button btn=new Button("Editar");
            
            @Override
            public void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                }
                else{
                    btn.setOnAction(event -> {
                        Producto producto = (Producto) getTableRow().getItem();
                        try {
                            abrirVentanaModal(producto, "Editar Producto");
                        } catch (IOException ex) {
                            Logger.getLogger(FormProductoController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    setGraphic(btn);
                    setText(null);
                }
            }
        });
    }

    private void definirColumnaEliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
