package core.config;

import core.services.ServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import vezh_bank.persistence.dao.*;
import vezh_bank.persistence.dao.impl.*;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"core"})
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

    @Bean
    @Lazy
    public CardDao cardDao() {
        return new CardDaoImpl(){};
    }

    @Bean
    @Lazy
    public CurrencyDao currencyDao() {
        return new CurrencyDaoImpl(){};
    }

    @Bean
    @Lazy
    public EventDao eventDao() {
        return new EventDaoImpl(){};
    }

    @Bean
    @Lazy
    public RoleDao roleDao() {
        return new RoleDaoImpl(){};
    }

    @Bean
    @Lazy
    public TransactionDao transactionDao() {
        return new TransactionDaoImpl(){};
    }

    @Bean
    @Lazy
    public UserDao userDao() {
        return new UserDaoImpl(){};
    }

    @Bean
    @Lazy
    public UserRequestDao userRequestDao() {
        return new UserRequestDaoImpl(){};
    }

    @Bean
    @Lazy
    public PaymentDao paymentDao() {
        return new PaymentDaoImpl(){};
    }

    @Bean
    @Lazy
    public ServiceProvider serviceProvider() {
        return new ServiceProvider();
    }

}
