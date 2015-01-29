/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.enterprisegeeks.cdi.flash.scope.servlet;

import com.github.enterprisegeeks.cdi.flash.scope.bean.FlashBean;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * get query String and set flashScope bean
 */
@WebServlet(name = "GetServlet", urlPatterns = {"/GetServlet"})
public class GetServlet extends HttpServlet {

    @Inject
    private FlashBean bean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("call GetServlet");
        String message = request.getParameter("message");
        
        bean.setMessage(message);
        
        request.getRequestDispatcher("welcome").forward(request, response);
        
    }
}
