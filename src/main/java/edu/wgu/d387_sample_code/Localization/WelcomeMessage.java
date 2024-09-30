package edu.wgu.d387_sample_code.Localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class WelcomeMessage implements Runnable {
    private final Locale locale;

    public WelcomeMessage(Locale locale) {
        this.locale = locale;
    }

    public String getMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("translation", locale);
        return bundle.getString("welcome");
    }

    @Override
    public void run() {
        System.out.println("Thread verification: " + getMessage() + ", ThreadID: " + Thread.currentThread().getId());
    }
}
