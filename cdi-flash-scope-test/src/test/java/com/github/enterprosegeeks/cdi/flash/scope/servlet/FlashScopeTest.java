package com.github.enterprosegeeks.cdi.flash.scope.servlet;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import static org.hamcrest.CoreMatchers.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 * FlashScoped test
 */
@RunWith(Arquillian.class)
public class FlashScopeTest {
    
    private static final String url = "http://localhost:8181/cdi-flash-scope-test/";
    
    @Deployment
    public static WebArchive createDeployment() {
        
        PomEquippedResolveStage mvn = 
                Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies();
        File webinf = new File("src/main/webapp/WEB-INF");
        
        File[] jars = mvn.resolve().withTransitivity().asFile();
        
        WebArchive war =  ShrinkWrap
            .create(WebArchive.class, "cdi-flash-scope-test.war")
            .addAsLibraries(jars)
            .addPackage("com.github.enterprisegeeks.cdi.flash.scope.bean")
            .addPackage("com.github.enterprisegeeks.cdi.flash.scope.servlet")
            .addAsWebInfResource(new File(webinf, "beans.xml"))
            .addAsWebResource(new File("src/main/webapp/index.jsp"));
        
        System.out.println(war.toString(true));
        
        return war;
    }
    
    @Test
    @RunAsClient
    public void on_accessDirectJsp_flashBean_is_empty() throws Exception {
                try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            
            
            HttpGet welocme = new HttpGet(url + "index.jsp");
            try (CloseableHttpResponse response = httpClient.execute(welocme)) {
                HttpEntity entity = response.getEntity();
                String body =EntityUtils.toString(entity, StandardCharsets.UTF_8);
                
                assertThat(response.getStatusLine().getStatusCode(), is(200));
                assertThat(body, containsString("<div></div>"));
            }
        }
    }
    
    
    @Test
    @RunAsClient
    public void on_accessGet_flashBean_is_set_and_after_reload_empty() throws Exception { 
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            
            HttpGet get = new HttpGet(url + "GetServlet?message=flash");

            try (CloseableHttpResponse response = httpClient.execute(get)) {
                HttpEntity entity = response.getEntity();
                String body =EntityUtils.toString(entity, StandardCharsets.UTF_8);
                assertThat(response.getStatusLine().getStatusCode(), is(200));
                assertThat(body, containsString("<div>flash</div>"));
            }
            
            HttpGet reload = new HttpGet(url + "index.jsp");
            try (CloseableHttpResponse response = httpClient.execute(reload)) {
                HttpEntity entity = response.getEntity();
                String body =EntityUtils.toString(entity, StandardCharsets.UTF_8);
                
                assertThat(response.getStatusLine().getStatusCode(), is(200));
                assertThat(body, containsString("<div></div>"));
            }
        }
    }
    
    
    
    @Test
    @RunAsClient
    public void on_accessRedirect_flashBean_is_set_and_after_reload_empty() throws Exception { 
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            
            HttpGet redirect = new HttpGet(url + "RedirectServlet?message=redirect");

            try (CloseableHttpResponse response = httpClient.execute(redirect)) {
                HttpEntity entity = response.getEntity();
                String body =EntityUtils.toString(entity, StandardCharsets.UTF_8);
                
                assertThat(response.getStatusLine().getStatusCode(), is(200));
                assertThat(body, containsString("<div>redirect</div>"));
            }
            
            HttpGet reload = new HttpGet(url + "index.jsp");
            try (CloseableHttpResponse response = httpClient.execute(reload)) {
                HttpEntity entity = response.getEntity();
                String body =EntityUtils.toString(entity, StandardCharsets.UTF_8);
                
                assertThat(response.getStatusLine().getStatusCode(), is(200));
                assertThat(body, containsString("<div></div>"));
            }
        }
    }

}
