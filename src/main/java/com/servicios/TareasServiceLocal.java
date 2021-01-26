
package com.servicios;

import com.Excepciones.TareasException;
import com.modelo.Tareas;
import java.util.Collection;
import javax.ejb.Local;


@Local
public interface TareasServiceLocal {
    
    public Collection<Tareas> getTareas(Integer idUsuario, String estado);

    public Tareas getTarea(int i);
    
    public void CambiarEstadoUp(Integer idTarea) throws TareasException;
    public void CambiarEstadoDown(Integer idTarea) throws TareasException;
}
