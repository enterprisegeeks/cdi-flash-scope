package com.github.enterprisegeeks.cdi.flash.extension;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

/**
 * Bean Store in Flash Scope 
 */
public class FlashContextBeanStore {
    
    private final Map<Class, FlashInstance> store = new ConcurrentHashMap<>();
    
    public <T> FlashInstance<T> getBean(Bean<T> bean) {
        return store.get(bean.getBeanClass());
    }
 
    public boolean containsBean(Bean bean) {
        return store.containsKey(bean.getBeanClass());
    }
    
    public <T> void  putBean(Bean<T> bean, CreationalContext<T> cc, T instance) {
        FlashInstance<T> fi = new FlashInstance<>();
        fi.bean = bean;
        fi.ctx = cc;
        fi.instance = instance;
        store.put(bean.getBeanClass(), fi);
    }
 
    Map<Class, FlashInstance> getBeans(){
        return this.store;
    }
    
    void destroyBean(FlashInstance flashInstance) {
        store.remove(flashInstance.bean.getBeanClass());
        flashInstance.bean.destroy(flashInstance.instance, flashInstance.ctx);
    }
    
    public static class FlashInstance<T> {
        
        // state
        /** end first request */
        private boolean sendRedirect;
        /** end second request */
        private boolean endRedirect;
        
        private Bean<T> bean;
        private CreationalContext<T> ctx;
        private T instance;
        
        /**
         * change state.
         * 
         * @param callSendRedirect  
         */
        public void mark(boolean callSendRedirect) {
            // sendRedirect on , if first request is redirect
            if (!sendRedirect && callSendRedirect) {
                sendRedirect = true;
                return;
            }
            // is second request end?
            if (sendRedirect) { 
                endRedirect = true;
            }
        }
        
        /**
         * Is bean destory ? 
         * @return ok - true
         */
        public boolean isDestory() {
            return (sendRedirect && endRedirect)
                    || (!sendRedirect && !endRedirect);
        }
        
        public T getInstance(){
            return instance;
        }
        
    }
}
