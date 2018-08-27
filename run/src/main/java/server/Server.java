package server;


import core.config.VezhBankConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AutoConfigureAfter({VezhBankConfiguration.class})
@SpringBootApplication
public class Server {

    public static void main(String args[]) {
        SpringApplication.run(Server.class, args);
    }
}
