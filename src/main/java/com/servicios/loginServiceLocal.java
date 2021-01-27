/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servicios;

import com.Excepciones.LoginException;
import javax.ejb.Local;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@Local
public interface loginServiceLocal {
    
    public void login(String mail, String password, HttpSession sesion) throws LoginException;
    
    public void logout(HttpSession sesion);
}
