package Lab9_10;

import org.h2.tools.Server;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
@EntityScan(basePackages = {"Lab9_10"})
@EnableJpaRepositories(basePackages = "Lab9_10")
public class Main {
    final static String defaultLogin = "sa";
    final static String defaultPassword = "password";
    public static void main(String[] args)
    {
        try
        {
            Server server = Server.createTcpServer("-tcpAllowOthers").start();
            System.out.println("Server URL: " + server.getURL());
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", defaultLogin, defaultPassword);
            System.out.println("Connected");

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
