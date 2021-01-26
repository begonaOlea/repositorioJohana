
package com.servicios;

import com.Excepciones.UsuarioException;
import com.Excepciones.UsuarioUpdateException;
import com.modelo.Usuarios;
import java.util.Collection;
import javax.ejb.Local;

@Local
public interface UsuarioServiceLocal {

    public void alta(Usuarios usrs);
    public Usuarios getUsuario(int id) throws UsuarioException;
    public Collection<Usuarios> getAllUsuarios();
    public Usuarios getUsuarioByEmail (String email) throws UsuarioException ;
    public void borrar(int id) throws UsuarioException;
    public void modificar(Usuarios usr) throws UsuarioException, UsuarioUpdateException;
   
}
