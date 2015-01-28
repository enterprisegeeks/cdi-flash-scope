package jp.co.ulsystems.cdi.flash.extension;

import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import jp.co.ulsystems.cdi.flash.FlashScoped;

/**
 * CDI Extention Point for Flash Scopd
 */
public class FlashScopeExtension implements Extension{
    
    private static final Logger LOG = Logger.getLogger(FlashScopeExtension.class.getPackage().getName());
    
    public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event, BeanManager beanManager) {
        // add FlashScoped Annotation for CDI Bean
        
        LOG.fine("FlashScopeExtension beforeBeanDiscovery.");
        event.addScope(FlashScoped.class, true, true);
    }

    /**
     * After bean discovery.
     *
     * @param event the event.
     * @param beanManager  beanManger.
     */
    public void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager beanManager) {  
        
        LOG.fine("FlashScopeExtension afterBeanDiscovery addContext.");
        event.addContext(new FlashScopeContext());
    }
}
