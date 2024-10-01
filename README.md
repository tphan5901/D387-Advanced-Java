C. Modify the Landon Hotel scheduling application for localization and internationalization by doing the following:
1. Install the Landon Hotel scheduling application in your integrated development environment (IDE). Modify the Java classes of application to display a welcome message by doing the following:
   a. Build resource bundles for both English and French (languages required by Canadian law). Include a welcome message in the language resource bundles.
   CREATE:
   Resource Bundle 'translation'

            translation_en_us.properties
                hello=Hi!
                welcome=Welcome to the Landon Hotel

            translation_fr_ca.properties
                hello=Bonjour!
                welcome=Bienvenue à l'hôtel Landon
b. Display the welcome message in both English and French by applying the resource bundles using a different thread for each language.
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

@SpringBootApplication
public class D387SampleCodeApplication {

	private static ExecutorService executorService = Executors.newFixedThreadPool(2);

	public static void main(String[] args) {
		SpringApplication.run(D387SampleCodeApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(D387SampleCodeApplication.class, args);

		executorService.submit(new WelcomeMessage(Locale.US));
		executorService.submit(new WelcomeMessage(Locale.CANADA_FRENCH));

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				executorService.shutdown();
				if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
					executorService.shutdownNow();
				}
			} catch (InterruptedException e) {
				executorService.shutdownNow();
			}
		}));
	}

}


2. Modify the front end to display the price for a reservation in currency rates for U.S. dollars ($), Canadian dollars (C$), and euros (€) on different lines.
   Note: It is not necessary to convert the values of the prices.
   MODIFY:

        app.component.ts, lines 70-71

            // C2 - Code to add the CAD/EUR "prices"
            this.rooms.forEach( room => { room.priceCAD = room.price; room.priceEUR = room.price})
    
        app.component.ts, lines 120-122

            // C2 - Code to add the CAD/EUR "prices"
            priceCAD:string;
            priceEUR:string;

        app.component.html, lines 86-90
            < strong > Price: CA${{room.priceCAD}} < /strong > < br > 
            < strong > Price: EUR€{{room.priceEUR}} < /strong > < br >

3. Display the time for an online live presentation held at the Landon Hotel by doing the following: a. Write a Java method to convert times between eastern time (ET), mountain time (MT), and coordinated universal time (UTC) zones.
   CREATE:

        TZConvert.java

            package edu.wgu.d387_sample_code.internationalization;
            
            import org.springframework.web.bind.annotation.CrossOrigin;
            import java.time.*;
            import java.time.format.DateTimeFormatter;
            
            @CrossOrigin(origins = "http://localhost:4200")
            public class TZConvert {
            
                public static String getTime() {
                    ZonedDateTime time = ZonedDateTime.now();
                    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            
                    ZonedDateTime est = time.withZoneSameInstant(ZoneId.of("America/New_York"));
                    ZonedDateTime mst = time.withZoneSameInstant(ZoneId.of("America/Denver"));
                    ZonedDateTime utc = time.withZoneSameInstant(ZoneId.of("UTC"));
            
                    String times = est.format(timeFormat) + "EST, " + mst.format(timeFormat) + "MST, " + utc.format(timeFormat) + "UTC";
            
                    return times;
                }
            }
