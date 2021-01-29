/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web;

import com.Excepciones.UsuarioException;
import com.Excepciones.UsuarioUpdateException;
import com.modelo.Usuarios;
import com.servicios.UsuarioServiceLocal;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author user
 */
@Named(value = "gestionUsuariosMB")
@ViewScoped
public class GestionUsuarios implements Serializable{

    private Collection<Usuarios> coleccionUsuarios; 
     @EJB private UsuarioServiceLocal usuarioService;
     private Usuarios usuarioEncontrado;
     private String emailABuscar;
     private Usuarios nuevoUsuario = new Usuarios();
     
    public GestionUsuarios() {
        System.out.println("-... INSTANCIANDO GESTIÓN DE USUARIOS ");
    }
    
    @PostConstruct
     public void inicializar(){
         
         this.coleccionUsuarios = usuarioService.getAllUsuarios();
     }

    public String getEmailABuscar() {
        return emailABuscar;
    }

    public void setEmailABuscar(String emailABuscar) {
        this.emailABuscar = emailABuscar;
    }

    public Usuarios getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(Usuarios nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }
     
     
     public Collection<Usuarios> getColleccionUsuaios(){
         return coleccionUsuarios;
     }
     
     public Usuarios getUsuarioEncontrado(){
         return usuarioEncontrado;
     } 
     
     public String buscarUsuario(String email){
        return buscarPorMail(email);
     }
     
     public String buscarUsuarioPorEmailEntrada(){
        return buscarPorMail((this.emailABuscar));
    }
    
   private String buscarPorMail (String email){
        
        try {
            this.usuarioEncontrado = usuarioService.getUsuarioByEmail(email);
            return null;
        } catch (UsuarioException ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(ex.getMessage()));
            return null;
        }
   }
   
   public String borrarUsuario(int id){
        try {
            usuarioService.borrar(id);
            inicializar();
        } catch (UsuarioException ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(ex.getMessage()));
        }
       return null;
   }
   
   public String modificarUsuario () {
            
       try{
          usuarioService.modificar(usuarioEncontrado);
          inicializar();
          return "null";
      } catch (UsuarioException | UsuarioUpdateException ex) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("No se pudo modificar "+ ex.getMessage()));
            return null;
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Error no controlado "+ e.getMessage()));
            e.printStackTrace();
            return null;
        }
      
   }
   
   public String crearUsuario(){
       try {
           usuarioService.alta(nuevoUsuario);
           FacesContext ctx = FacesContext.getCurrentInstance();
           FacesMessage mns = new FacesMessage("Usuario dado de alta");
           ctx.addMessage(null, mns);
           return "login";
           
       } catch (Exception e) {
           FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(e.getMessage()));
            return null;
       }
       
   }
}