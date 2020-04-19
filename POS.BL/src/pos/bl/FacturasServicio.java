/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.bl;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author DELL
 */
public class FacturasServicio {
     public void guardar(Factura factura){
        
        //eliminamos parte del if y configuramos el boton guardar
        //si ya existe loactializa si no lo guarda

            Session session=HibernateUtil.getSessionFactory().openSession();
            Transaction tx=session.beginTransaction();
            try {
                session.saveOrUpdate(factura);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                System.out.println(e.getMessage());
            }finally{
                session.close();
            }
    }
}
