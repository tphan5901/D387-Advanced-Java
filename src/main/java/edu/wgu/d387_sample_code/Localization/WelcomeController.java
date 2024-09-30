package edu.wgu.d387_sample_code.Localization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class WelcomeController {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    @GetMapping("/welcome")
    public ResponseEntity<String> displayWelcome(@RequestParam("lang") String lang) {
        Locale locale = Locale.forLanguageTag(lang);
        WelcomeMessage welcomeMessage = new WelcomeMessage(locale);
        executor.submit(welcomeMessage);
        return new ResponseEntity<>(welcomeMessage.getMessage(), HttpStatus.OK);
    }

    @PreDestroy
    public void shutdownExecutor() {
        executor.shutdown();
    }
}