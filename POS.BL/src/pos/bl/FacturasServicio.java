/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.bl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
// *
 * @author DELL
 */
public class FacturasServicio {
    public ArrayList<Factura> obtenerFacturas(Date fechaInicial,Date fechaFinal){
        Session session=HibernateUtil.getSessionFactory().openSession();
        
        Transaction tx=session.beginTransaction();
        
        Criteria query=session.createCriteria(Factura.class);
        query.add(Restrictions.ge("fecha",fechaInicial));
        query.add(Restrictions.ge("fecha",fechaFinal));
        query.add(Restrictions.eq("activo",true));
        List<Factura> resultado=query.list();
        
        tx.commit();
        session.close();
        
        return new ArrayList<>(resultado);
    }
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
