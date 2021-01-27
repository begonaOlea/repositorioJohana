/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web;

import com.Excepciones.LoginException;
import com.modelo.Usuarios;
import com.servicios.loginServiceLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@Named(value = "loginMB")
@SessionScoped
public class loginManagedBean implements Serializable {

    private String email;
    private String clave;
    private Usuarios usuarioCompleto;
    private Date fecha = new Date();
    
    @EJB
     private loginServiceLocal servLogin;       
    
    public loginManagedBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Usuarios getUsuarioCompleto() {
        return usuarioCompleto;
    }

    public void setUsuarioCompleto(Usuarios usuarioCompleto) {
        this.usuarioCompleto = usuarioCompleto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String login() {
        
        //obtener la sesion de JSF 
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
        try {
            servLogin.login(email, clave, session);
            usuarioCompleto = (Usuarios) session.getAttribute("usuario");
            return "lista-tareas.xhtml";

        } catch (LoginException e) {
            this.email = "";
            this.clave = "";

            //mns de error para mostrar en el formulario 
            FacesMessage mns = new FacesMessage(e.getMessage());
            //ctx.addMessage("formLogin:psw", mns);
            ctx.addMessage(null, mns);

            return "login.xhtml";
        }
    }

    public String logout() {
        
        //obtener la sesion de JSF 
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession) ctx.getExternalContext().getSession(true);
        servLogin.logout(sesion);
        
        return "login?faces-redirect=true";
    }
    
}
