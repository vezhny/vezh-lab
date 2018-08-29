package core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import vezh_bank.config.JpaConfig;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = JpaConfig.class)
public class VezhBankConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry.addResourceHandler("/resources/**").addResourceLocations("WEB-INF/resources/");
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
//        internalResourceViewResolver.setRedirectHttp10Compatible(false);
        return internalResourceViewResolver;
    }

}
