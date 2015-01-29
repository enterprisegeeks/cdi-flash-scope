/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.enterprisegeeks.cdi.flash.scope.servlet;

import com.github.enterprisegeeks.cdi.flash.scope.bean.FlashBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * welcome page
 */
@WebServlet(name = "welcome", urlPatterns = {"/welcome"})
public class WelcomServlet extends HttpServlet {

    @Inject
    private FlashBean bean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("call WelcomServlet");
        try(PrintWriter out = response.getWriter()){
            out.append(bean.getMessage());
        }
        
    }
}
