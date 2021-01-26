
package com.servicios;

import com.Excepciones.UsuarioException;
import com.Excepciones.UsuarioUpdateException;
import com.modelo.Usuarios;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UsuariosService implements UsuarioServiceLocal {
    @PersistenceContext(unitName = "TareasPU")
   private EntityManager em;

   
    @Override
    public Usuarios getUsuario(int id) throws UsuarioException{
        Usuarios u = em.find(Usuarios.class, id);
        if (u == null){
            throw new UsuarioException("No existe el usuario con id solicitado");
        }
        return u;
    }

 
    @Override
    public void alta(Usuarios usrs) {
       em.persist(usrs);
    }

    @Override
    public Collection<Usuarios> getAllUsuarios() {
        //consulta SQL = SELECT *FROM USUARIOS;  //no distingue mayusculas
        //JPA query = SELECT u FROM Usuarios  //distingue mayusculas
                        //SELECT u.idUsuario, u.email FROM usuarios u
        //String consulta = "SELECT u FROM Usuarios u";
        
        //CREATE NATIVE QUIERY 
        //String consulta = "select * from usuarios";
  //       Query query = em.createNativeQuery(consulta);
        
       Query query = em.createNamedQuery("Usuarios.findAll");
        
        return query.getResultList();
    }

    @Override
    public Usuarios getUsuarioByEmail(String email) throws UsuarioException {
        
        Query query = em.createNamedQuery("Usuarios.findByEmail");
        query.setParameter("email", email);
        try {
            Usuarios uEncontrado = (Usuarios) query.getSingleResult();
            return uEncontrado;
        } catch (javax.persistence.NoResultException e) {
            throw new UsuarioException("No existe un usuario con el email " + email);
        }
    }

    @Override
    public void borrar(int id) throws UsuarioException {
        Usuarios u = this.getUsuario(id);
        em.remove(u);
    }
    
    
    @Override
    public void modificar(Usuarios usr) throws UsuarioException, UsuarioUpdateException {
       Usuarios u = this.getUsuario(usr.getIdUsuario()); //este hace el em.find
       
       //u.setEmail(usr.getEmail());
       //u.setNombre(usr.getNombre());
       //u.setPassword(usr.getPassword());
       //ESTO ES LO MISMO QUE LAS TRES LÍNEAS ANTERIORES 
       em.merge(usr); 
       //los metodos del EJB siempre hacen commit al final si no hay excepciones 
       //COMMIT- Sincroniza los objetos del em con las tablas de la BD
    }
}
