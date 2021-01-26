
package com.servicios;

import com.Excepciones.TareasException;
import com.modelo.Tareas;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TareasService implements TareasServiceLocal {

    @PersistenceContext(unitName = "TareasPU")
   private EntityManager em;

    @Override
    public Collection<Tareas> getTareas(Integer idUsuario, String estado) {
        Query query = em.createNamedQuery("Tareas.findByEstado");
        query.setParameter("estado", estado);
        query.setParameter("idUsuario", idUsuario);
       
        return query.getResultList(); 
    }

    @Override
    public Tareas getTarea(int id) {
        Tareas t = em.find(Tareas.class, id);
        return t;
    }

    
    
    @Override
    public void CambiarEstadoUp(Integer idTarea) throws TareasException{
        Tareas t = getTarea(idTarea);
        if (t.getDescripcion().equals("TO DO"))
        t.setEstado("IN PROGRESS");
        if (t.getDescripcion().equals("IN PROGRESS"))
        t.setEstado("DONE"); 
    }

    @Override
    public void CambiarEstadoDown(Integer idTarea) throws TareasException{
        Tareas t = getTarea(idTarea);
        if (t.getDescripcion().equals("DONE"))
        t.setEstado("IN PROGRESS");
        if (t.getDescripcion().equals("IN PROGRESS"))
        t.setEstado("TO DO"); 
    }

    
}
