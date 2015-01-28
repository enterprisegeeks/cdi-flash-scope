package jp.co.ulsystems.cdi.flash.extension;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

/**
 * Bean Holder in Flash Scope 
 */
public class FlashContextHolder {
    
    private final Map<Class, FlashInstance> holder = new ConcurrentHashMap<>();
    
    public <T> FlashInstance<T> getBean(Bean<T> bean) {
        return holder.get(bean.getBeanClass());
    }
 
    public boolean containsBean(Bean bean) {
        return holder.containsKey(bean.getBeanClass());
    }
    
    public <T> void  putBean(Bean<T> bean, CreationalContext<T> cc, T instance) {
        FlashInstance<T> fi = new FlashInstance<>();
        fi.bean = bean;
        fi.ctx = cc;
        fi.instance = instance;
        holder.put(bean.getBeanClass(), fi);
    }
 
    Map<Class, FlashInstance> getBeans(){
        return this.holder;
    }
    
    void destroyBean(FlashInstance flashInstance) {
        holder.remove(flashInstance.bean.getBeanClass());
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
            if (!sendRedirect || callSendRedirect) {
                sendRedirect = true;
                return;
            }
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
