/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.bl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author DELL
 */
public class ProductosServicio {
    //eliminamos y copiamos lode catergoria solo que para producto
    public ArrayList<Producto> obtenerProductos(){
       Session session=HibernateUtil.getSessionFactory().openSession();
        
        Transaction tx=session.beginTransaction();
        
        Criteria query=session.createCriteria(Producto.class);
        List<Producto> resultado=query.list();
        
        tx.commit();
        session.close();
        
        return new ArrayList<>(resultado);
    }
     //eliminamos y copiamos lode catergoria solo que para producto
    //con la excepcion de like(conteins) y en where
    public ArrayList<Producto> obtenerProductos(String buscar){
        Session session=HibernateUtil.getSessionFactory().openSession();
        
        Transaction tx=session.beginTransaction();
        
        Criteria query=session.createCriteria(Producto.class);
        query.add(Restrictions.like("descripcion", buscar,MatchMode.ANYWHERE));
        
        List<Producto> resultado=query.list();
        
        tx.commit();
        session.close();
        
        return new ArrayList<>(resultado);
    }
    
    public String guardar(Producto producto){
        String resultado=validarProducto(producto);
        //eliminamos parte del if y configuramos el boton guardar
        //si ya existe loactializa si no lo guarda
        if(resultado.equals("")){
            Session session=HibernateUtil.getSessionFactory().openSession();
            Transaction tx=session.beginTransaction();
            try {
                session.saveOrUpdate(producto);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                return e.getMessage();
            }finally{
                session.close();
            }
            
            return "";
        }
        return resultado;
    }
    
    public void eliminar(Producto producto){
        //copiamos guardar solo que delete y el menaje del try en consola
        Session session=HibernateUtil.getSessionFactory().openSession();
            Transaction tx=session.beginTransaction();
            try {
                session.delete(producto);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                System.out.println(e.getMessage());
            }finally{
                session.close();
            }    
    }
           
    public Producto clonar(Producto producto){
        Producto productoClonado = new Producto();
        
        productoClonado.setId(producto.getId());
        productoClonado.setDescripcion(producto.getDescripcion());
        productoClonado.setCategoria(producto.getCategoria());
        productoClonado.setPrecio(producto.getPrecio());
        productoClonado.setExistencia(producto.getExistencia());
        productoClonado.setActivo(producto.getActivo());
        productoClonado.setImagen(producto.getImagen());
        return productoClonado;
        
    }

    private String validarProducto(Producto producto) {
        if(producto.getDescripcion()==null || producto.getDescripcion().equals("")){
            return "Ingrese la Descripcion";
        }
        if(producto.getCategoria()==null){
            return "Seleccione una categoria";
        }
        if(producto.getPrecio() < 0){
            return "Ingrese un preciomayor o igual a cero";
        }
        if(producto.getExistencia() < 0){
            return "La existencia debe ser mayor que cero";
        }
        return "";
    }
    
    
    
}
