package com.web;

import com.Excepciones.TareasException;
import com.modelo.Tareas;
import com.modelo.Usuarios;
import com.servicios.TareasServiceLocal;
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "gestionTareasMB")
@ViewScoped
public class GestionTareasManagedBean implements Serializable {

    private Tareas selectTarea;
    private Tareas tareaNueva = new Tareas();
    private int idTarea;
    private Collection<Tareas> coleccionTareasTodo;
    private Collection<Tareas> coleccionTareasInProgress;
    private Collection<Tareas> coleccionTareasDone;
    private Usuarios usuarioComp;
    private Integer idUsuario;

    @Inject
    private loginManagedBean loginMB;

    @EJB
    private TareasServiceLocal tareasService;

    public GestionTareasManagedBean() {
    }

    @PostConstruct
    public void iniciar() {
        if (loginMB.getUsuarioCompleto() != null) {
            usuarioComp = loginMB.getUsuarioCompleto();
            idUsuario = usuarioComp.getIdUsuario();
        } else {
            System.out.println("Aun no tienes tareas para mostrar");
        }

        this.coleccionTareasTodo = this.tareasService.getTareas(idUsuario, "TO DO");
        this.coleccionTareasInProgress = this.tareasService.getTareas(idUsuario, "IN PROGRESS");
        this.coleccionTareasDone = this.tareasService.getTareas(idUsuario, "DONE");

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

    public Tareas getTareaNueva() {
        return tareaNueva;
    }

    public void setTareaNueva(Tareas tareaNueva) {
        this.tareaNueva = tareaNueva;
    }

    public String cambiarEstadoUp() {
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

    public String cambiarEstadoDown() {
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

    public String addTarea() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            tareaNueva.setArchivado(false);
            tareaNueva.setUsuario(usuarioComp);
            tareasService.altaTarea(tareaNueva);
            FacesMessage msg = new FacesMessage("Tarea agregada ");
            ctx.addMessage(null, msg);
            iniciar();
            return "lista-tareas";
        } catch (TareasException ex) {
            FacesMessage msg = new FacesMessage("No se ha creado la tarea" + ex.getMessage());
            System.out.println(ex.getMessage());
            ctx.addMessage(null, msg);
            return null;
        }
    }
}
