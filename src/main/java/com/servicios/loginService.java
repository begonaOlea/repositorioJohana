
package com.servicios;

import com.Excepciones.LoginException;
import com.Excepciones.UsuarioException;
import com.modelo.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

@Stateless
public class loginService implements loginServiceLocal {

    @PersistenceContext(unitName = "TareasPU")
    private EntityManager em;

    @Override
    public void login(String mail, String password, HttpSession sesion) throws LoginException{
       
        Query query = em.createNamedQuery("Usuarios.login");
        query.setParameter("email", mail);
        query.setParameter("password", password);
        
        try {
            Usuarios usuarioLogin = (Usuarios) query.getSingleResult();
            sesion.setAttribute("usuario", usuarioLogin);
        } catch (javax.persistence.NoResultException e) {
           throw new LoginException("El usuario no existe");
        }
    }

    @Override
    public void logout(HttpSession sesion) {
        sesion.invalidate();
    }

  
}
