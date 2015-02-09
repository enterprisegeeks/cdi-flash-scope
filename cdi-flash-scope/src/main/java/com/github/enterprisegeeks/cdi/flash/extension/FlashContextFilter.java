package com.github.enterprisegeeks.cdi.flash.extension;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filter for FlashScope context.
 * 
 * this manage to Desoroty FlashScoped beans. 
 */
@WebFilter(urlPatterns = "/*", description = "FlashScope manage filter", asyncSupported = true)
public class FlashContextFilter implements Filter{

    private static final Logger LOG 
            = Logger.getLogger(FlashContextFilter.class.getPackage().getName());
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.fine("FlashContextFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        LOG.fine("FlashScopedFilter start");
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String key = FlashContextBeanStore.class.getCanonicalName();
        FlashContextBeanStore beanStore = (FlashContextBeanStore)session.getAttribute(key);
        if (beanStore == null) {
            beanStore = new FlashContextBeanStore();
            session.setAttribute(key, beanStore);
        }
        // pass store to context 
        BeanStoreHolder.set(beanStore);
        try {
            chain.doFilter(request, response);
        } finally {
            BeanStoreHolder.remove();
        }
        
        // update flash scope beans;
        HttpServletResponse res = (HttpServletResponse) response;
        
        for(FlashContextBeanStore.FlashInstance i : beanStore.getBeans().values()) {
            i.mark(res.getStatus() == HttpServletResponse.SC_FOUND);

            if (i.isDestory()) {
                LOG.fine("FlashScopedFilter destory bean");
                beanStore.destroyBean(i);
            }
        }
        

        LOG.fine("FlashScopedFilter end");
        
    }

    @Override
    public void destroy() {
    }
}
