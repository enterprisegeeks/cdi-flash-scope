/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.enterprisegeeks.cdi.flash.extension;

/**
 * BeanStore holder
 */
public class BeanStoreHolder {
    
    private static final ThreadLocal<FlashContextBeanStore> holder = new ThreadLocal<>();
    
    private BeanStoreHolder(){}
    
    public static void set(FlashContextBeanStore req) {
        holder.set(req);
    }
    
    public static FlashContextBeanStore get() {
        return holder.get();
    }
    
    public static void remove() {
        holder.remove();
    }
    
    
}
