package edu.wgu.d387_sample_code.Localization;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Locale;
import java.util.ResourceBundle;

@CrossOrigin(origins = "http://localhost:4200")
public class WelcomeMessage implements Runnable {

    Locale locale;

    public WelcomeMessage(Locale locale) {
        this.locale = locale;
    }

    public String getWelcomeMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("translation",locale);
        return bundle.getString("welcome");
    }

    @Override
    public void run() {
        try {
            System.out.println("ThreadID: " + Thread.currentThread().getId());
            String message = getWelcomeMessage();
            System.out.println("Welcome Message: " + message);
        } catch (Exception e) {
            System.err.println("Error creating threads: " + e.getMessage());
            e.printStackTrace();
        }
    }
}