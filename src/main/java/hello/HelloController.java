package hello;

import java.net.InetAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @Value("${hostname}")
    private String hostname;
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/h")
    public String host() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        return "Greetings from Spring Boot!"+address.getHostAddress();
    }
    @RequestMapping("/a")
    public String echo() throws Exception {
        return "Greetings from Spring Boot!"+hostname;
    }

    
}
