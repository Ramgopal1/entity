import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "controller")
public class EntityGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(EntityGeneratorApplication.class, args);
    }
}
