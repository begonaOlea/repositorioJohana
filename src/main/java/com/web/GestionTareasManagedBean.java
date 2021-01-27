
package com.web;

import com.Excepciones.TareasException;
import com.modelo.Tareas;
import com.servicios.TareasServiceLocal;
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "gestionTareasMB")
@ViewScoped
public class GestionTareasManagedBean implements Serializable{

    private Tareas selectTarea;
    private int idTarea;
    private Collection<Tareas> coleccionTareasTodo;
    private Collection<Tareas> coleccionTareasInProgress;
    private Collection<Tareas> coleccionTareasDone;
    
    @EJB
    private TareasServiceLocal tareasService;
    
    public GestionTareasManagedBean() {
    }

    @PostConstruct
    public void iniciar(){
        this.coleccionTareasTodo = this.tareasService.getTareas(4, "TO DO");
        this.coleccionTareasInProgress = this.tareasService.getTareas(4, "IN PROGRESS");
        this.coleccionTareasDone = this.tareasService.getTareas(4, "DONE");
        
    }
    
    public Collection<Tareas> getColeccionTareasTodo() {
        System.out.println(".....OBTENIENDO TAREAS HACER ");
        return coleccionTareasTodo;
    }
    
    public Collection<Tareas> getColeccionTareasInProgress() {
        System.out.println(".....OBTENIENDO TAREAS PROGRESO ");
        return coleccionTareasInProgress;
    }
    
    public Collection<Tareas> getColeccionTareasDone() {
        System.out.println(".....OBTENIENDO TAREAS DONE ");
        return coleccionTareasDone;
    }
    
    public Tareas getSelectTarea() {
        return selectTarea;
    }
    
    public void setSelectTarea(Tareas selectTarea) {
        this.selectTarea = selectTarea;
    }
    
    public String  cambiarEstadoUp(){
         
        FacesContext ctx = FacesContext.getCurrentInstance();
    
          try {
            this.idTarea = this.selectTarea.getIdTarea();
              this.tareasService.CambiarEstadoUp(idTarea);
            FacesMessage msg = new FacesMessage("Cambiaste el estado de la tarea ");
            ctx.addMessage(null, msg);
            iniciar();
            return "tareas";
        } catch (TareasException ex) {
            FacesMessage msg = new FacesMessage("No se ha cambiado el estado" + ex.getMessage());
            ctx.addMessage(null, msg);
        }
        return null;
     }
     
    public String  cambiarEstadoDown(){
        
         FacesContext ctx = FacesContext.getCurrentInstance();
         
          try {
              this.idTarea = this.selectTarea.getIdTarea();
             this.tareasService.CambiarEstadoDown(idTarea);
            FacesMessage msg = new FacesMessage("Cambiaste el estado de la tarea ");
            ctx.addMessage(null, msg);
            iniciar();
            return "tareas";
        } catch (TareasException ex) {
            FacesMessage msg = new FacesMessage("No se ha cambiado el estado" + ex.getMessage());
            ctx.addMessage(null, msg);
        }
        return null;
     }
}
