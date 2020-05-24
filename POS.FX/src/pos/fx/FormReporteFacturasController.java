/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.fx;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import net.sf.jasperreports.engine.JRException;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class FormReporteFacturasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    JFXDatePicker datePickerfechaInicial;
    
    @FXML
    JFXDatePicker datePickerfechaFinal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePickerfechaInicial.setValue(LocalDate.now());
          datePickerfechaFinal.setValue(LocalDate.now());
      
    }
    
    public void generarReporte() throws ParseException{
        ReporteFacturasViewer reporteViewer = new ReporteFacturasViewer();
        try {
            Date fechaInicial = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(getFecha(datePickerfechaInicial)+ "00:00:00");
            Date fechaFinal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(getFecha(datePickerfechaFinal)+ "23:59:59");
            
            reporteViewer.mostrarReporte(fechaInicial,fechaFinal);
        } catch (JRException ex) {
            Logger.getLogger(FormReporteFacturasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
   
    private String getFecha(JFXDatePicker datePicker){
        
        String fecha=
                
                Integer.toString(datePicker.getValue().getYear()) +"-" +
                Integer.toString(datePicker.getValue().getMonthValue()) +"-" +
                Integer.toString(datePicker.getValue().getDayOfMonth());
        
        
        return fecha;
    }
    
}
