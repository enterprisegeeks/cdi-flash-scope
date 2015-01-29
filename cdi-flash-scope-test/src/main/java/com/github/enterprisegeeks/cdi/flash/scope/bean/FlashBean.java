package com.github.enterprisegeeks.cdi.flash.scope.bean;

import com.github.enterprisegeeks.cdi.flash.FlashScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;

/**
 * FlashScope Bean
 */
@FlashScoped
@Named
public class FlashBean implements Serializable{
    
    private String message;

    public FlashBean(){}
    
    
    public FlashBean(String message){
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @PostConstruct
    public void postConstruct(){
        System.out.println("bean created.");
    }
    
    @PreDestroy
    public void preDestroy(){
        
        System.out.println("bean destory.");
    }
}
