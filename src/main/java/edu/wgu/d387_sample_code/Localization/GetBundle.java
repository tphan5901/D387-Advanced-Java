package edu.wgu.d387_sample_code.Localization;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Locale;
import java.util.ResourceBundle;

@CrossOrigin(origins = "http://localhost:4200")
public class GetBundle implements Runnable {
    private final Locale locale;

    public GetBundle(Locale locale) {
        this.locale = locale;
    }

    public String getMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("translation",locale);
        return bundle.getString("greeting");
    }

    @Override
    public void run() {
        try {
            System.out.println("ThreadID: " + Thread.currentThread().getId());
            String message = getMessage();
            System.out.println("Message: " + message);
        } catch (Exception e) {
            System.err.println("Error creating threads: " + e.getMessage());
            e.printStackTrace();
        }
    }

}