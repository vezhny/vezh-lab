package vezh_bank.config;

import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.dao.*;
import vezh_bank.persistence.dao.impl.*;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = DataBaseService.class)
public class JpaConfig {

    @Bean
    @Scope(value = "singleton")
    public EntityManagerFactory emf() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("vezh_bank");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager txMgr(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
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
}
