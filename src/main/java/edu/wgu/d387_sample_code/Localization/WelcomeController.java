package edu.wgu.d387_sample_code.Localization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PreDestroy;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public ResponseEntity<String> displayWelcome (@RequestParam("lang") String welcome) {
        Locale locale = Locale.forLanguageTag(welcome);
        WelcomeMessage welcomeMessage = new WelcomeMessage(locale);
        return new ResponseEntity<String> (welcomeMessage.getWelcomeMessage(), HttpStatus.OK); // respond
    }

    @PreDestroy
    public void shutdownExecutor() {
        ExecutorService executor = null;
        executor.shutdown();
    }
}