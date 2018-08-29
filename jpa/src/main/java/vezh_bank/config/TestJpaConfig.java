package vezh_bank.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vezh_bank.constants.MavenProfiles;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableTransactionManagement
@Profile(MavenProfiles.TEST)
public class TestJpaConfig {
    @Bean
    @Scope(value = "singleton")
    @Qualifier("H2EmFactory")
    public EntityManagerFactory emf(@Qualifier("H2DS") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    @Qualifier("H2TxManager")
    public PlatformTransactionManager txMgr(@Qualifier("H2EmFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    @Qualifier("H2DS")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("test;MODE=MySQL;DATABASE_TO_UPPER=false")
                .setScriptEncoding(StandardCharsets.UTF_8.name())
                .continueOnError(true)
                .ignoreFailedDrops(true)

                // ------------- SCHEMA -------------

                .addScript("vezh_bank_model.sql")

                // -------------- DATA --------------

//                .addScript("sql/data/roles.sql")
                .build();
    }
}
