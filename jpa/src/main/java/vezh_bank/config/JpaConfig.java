package vezh_bank.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vezh_bank.constants.MavenProfiles;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@Profile(MavenProfiles.PRODUCTION)
public class JpaConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/vezh_bank?" +
                        "useUnicode=true&" +
                        "useJDBCCompliantTimezoneShift=true&" +
                        "useLegacyDatetimeCode=false&" +
                        "serverTimezone=UTC")
                .username("root")
                .password("root")
                .build();
    }

    @Bean
    @Scope(value = "singleton")
    public EntityManagerFactory emf(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPersistenceUnitName("vezh_bank");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager txMgr(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
