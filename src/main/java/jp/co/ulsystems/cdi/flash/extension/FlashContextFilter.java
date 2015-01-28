package jp.co.ulsystems.cdi.flash.extension;

import java.io.IOException;
import java.util.logging.Logger;
import javax.enterprise.inject.spi.CDI;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import jp.co.ulsystems.cdi.flash.FlashScoped;

/**
 * Filter for FlashScope context.
 * 
 * this manage to Desoroty FlashScoped beans. 
 */
@WebFilter(urlPatterns = "/*", description = "FlashScope manage filter")
public class FlashContextFilter implements Filter{

    private static final Logger LOG 
            = Logger.getLogger(FlashContextFilter.class.getPackage().getName());
    
   
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.fine("FlashContextFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        chain.doFilter(request, response);
        
        HttpServletResponse res = (HttpServletResponse) response;
        
        // mark flash scope
        FlashContextHolder holder = FlashContextHolder.getInstance();
        for(FlashContextHolder.FlashInstance i : holder.getBeans().values()) {
            i.mark(res.getStatus() == HttpServletResponse.SC_FOUND);
            
            if (i.isDestory()) {
                holder.destroyBean(i);
            }
        }
    
    }

    @Override
    public void destroy() {
    }
}
