package jp.co.ulsystems.cdi.flash.extension;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.servlet.http.HttpSession;

import jp.co.ulsystems.cdi.flash.FlashScoped;

/**
 * Context Defination of FlashScope
 */
public class FlashScopeContext implements Context,Serializable {
    
    private static final Logger LOG = Logger.getLogger(
            FlashScopeContext.class.getPackage().getName());
    
    public FlashScopeContext() {
        LOG.fine("FlashScopeContext Init");
    }
 
    @Override
    public Class<? extends Annotation> getScope() {
        return  FlashScoped.class;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    /**
     * call on new bean request.
     * 
     * @param <T> bean type
     * @param cntxtl bean
     * @param cc bean creation 
     * @return bean instance
     */
    @Override
    public <T> T get(Contextual<T> cntxtl, CreationalContext<T> cc) {
        Bean<T> bean = (Bean<T>) cntxtl;
        FlashContextHolder beanHolder = getHolder();
        if (beanHolder.containsBean(bean)) {
            return beanHolder.getBean(bean).getInstance();
        }
        T t = bean.create(cc);
        beanHolder.putBean(bean, cc, t);
        
        return t;
    }

    /**
     * call on exisiting bean request;
     * 
     * @param <T> bean type
     * @param cntxtl bean
     * @return bean instance
     */
    @Override
    public <T> T get(Contextual<T> cntxtl) {
        Bean<T> bean = (Bean<T>) cntxtl;
        FlashContextHolder beanHolder = getHolder();
        if (beanHolder.containsBean(bean)) {
            return beanHolder.getBean(bean).getInstance();
        }
        return null;
    }
    
    private FlashContextHolder getHolder() {
        HttpSession session = RequestHolder.get().getSession();
        
        String key = FlashContextHolder.class.getCanonicalName();
        
        Object obj = session.getAttribute(key);
        if (obj != null) {
            return (FlashContextHolder)obj;
        }
        
        FlashContextHolder holder = new FlashContextHolder();
        session.setAttribute(key, holder);
        
        return holder;
    }
    
}
