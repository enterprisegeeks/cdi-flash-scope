/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.ulsystems.cdi.flash.extension;

import javax.servlet.http.HttpServletRequest;

/**
 * Request holder
 */
public class RequestHolder {
    
    private static final ThreadLocal<HttpServletRequest> holder = new ThreadLocal<>();
    
    private RequestHolder(){}
    
    public static void set(HttpServletRequest req) {
        holder.set(req);
    }
    
    public static HttpServletRequest get() {
        return holder.get();
    }
    
    public static void remove() {
        holder.remove();
    }
    
    
}
