
package com.web;

import com.modelo.Usuarios;
import com.servicios.UsuarioServiceLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "pruebasMB")
@RequestScoped
public class PruebasMB {

    @EJB
  private UsuarioServiceLocal usuarioSL;
  private Usuarios usuarioEncontrado;
    
    public PruebasMB() {
    }
    
     public Usuarios getUsuarioEncontrado() {
        return usuarioEncontrado;
    }
     
    public void setUsuarioEncontrado(Usuarios usuarioEncontrado) {
        this.usuarioEncontrado = usuarioEncontrado;
    }
    
    //action
    
    public void mostrarUsuario (int i){
        try {
            this.usuarioEncontrado = usuarioSL.getUsuario(i);
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(e.getMessage()));
        }
     
    }
}
