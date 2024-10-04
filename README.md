C. Modify the Landon Hotel scheduling application for localization and internationalization by doing the following:
1. Install the Landon Hotel scheduling application in your integrated development environment (IDE). Modify the Java classes of application to display a welcome message by doing the following:
   a. Build resource bundles for both English and French (languages required by Canadian law). Include a welcome message in the language resource bundles.

   Resource Bundle:

            translation_en_us.properties
                hello=Hi!
                welcome=Welcome to the Landon Hotel
                presentation.message = there is a presentation at:

            translation_fr_ca.properties
                hello=Bonjour!
                welcome=Bienvenue à l'hôtel Landon
                presentation.message = Il y aura une présentation à l’adresse suivante:

b. Display the welcome message in both English and French by applying the resource bundles using a different thread for each language.


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

    private final Locale locale;

    public WelcomeMessage(Locale locale) {
        this.locale = locale;
    }

    public String getMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("translation",locale);
        return bundle.getString("welcome");
    }

    @Override
    public void run() {
        try {
            System.out.println("ThreadID: " + Thread.currentThread().getId());
            String message = getMessage();
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

app.component.html
  <h1>{{welcomeMessageFrench$ | async}}</h1>
  <h1>{{welcomeMessageEnglish$ | async}}</h1>


2. Modify the front end to display the price for a reservation in currency rates for U.S. dollars ($), Canadian dollars (C$), and euros (€) on different lines.
   Note: It is not necessary to convert the values of the prices.
   MODIFY:

        app.component.ts
        this.rooms.forEach( room => { room.priceCAD = room.price; room.priceEUR = room.price})
    
        app.component.ts
            priceCAD:string;
            priceEUR:string;

        app.component.html
            <strong> Price: CA${{room.priceCAD}} </strong> <br> 
            <strong> Price: EUR€{{room.priceEUR}} </strong> <br>

3. Display the time for an online live presentation held at the Landon Hotel by doing the following: a. Write a Java method to convert times between eastern time (ET), mountain time (MT), and coordinated universal time (UTC) zones.
   CREATE:

package edu.wgu.d387_sample_code.Localization;
public class ConvertTimezone {

    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private static final Map<String, String> timeZones = Map.<String, String>of(
            "US EST", "America/New_York",
            "US MST", "America/Denver",
            "UTC", "UTC"
    );

    public static String getTime() {
        ZonedDateTime currentTime = ZonedDateTime.now();

        StringBuilder timeStringBuilder = new StringBuilder();
        timeZones.forEach((name, zoneId) -> {
            ZonedDateTime zonedTime = currentTime.withZoneSameInstant(ZoneId.of(zoneId));
            timeStringBuilder.append(name).append(": ").append(zonedTime.format(timeFormat)).append(" ");
        });

        return timeStringBuilder.toString().trim();
    }

    public static void main(String[] args) {
        String message = getTime();
        System.out.println("Message: " + message);
    }

}

D. Create Dockerized file

FROM openjdk:11
EXPOSE 8080
ADD target/D387_sample_code-0.0.2-SNAPSHOT.jar 
WORKDIR /usr/src
ENTRYPOINT ["java", "-jar", "D387_sample_code-0.0.2-SNAPSHOT.jar"]