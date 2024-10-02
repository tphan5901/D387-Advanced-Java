C. Modify the Landon Hotel scheduling application for localization and internationalization by doing the following:
1. Install the Landon Hotel scheduling application in your integrated development environment (IDE). Modify the Java classes of application to display a welcome message by doing the following:
   a. Build resource bundles for both English and French (languages required by Canadian law). Include a welcome message in the language resource bundles.
   CREATE:
   Resource Bundle 'translation'

            translation_en_us.properties
                hello=Hi!
                welcome=Welcome to the Landon Hotel
                presentation.message = there is a presentation at:

            translation_fr_ca.properties
                hello=Bonjour!
                welcome=Bienvenue à l'hôtel Landon
                presentation.message = Il y aura une présentation à l’adresse suivante :

b. Display the welcome message in both English and French by applying the resource bundles using a different thread for each language.


package edu.wgu.d387_sample_code.Localization;
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

app.component.ts

   ngOnInit(){

       this.welcomeMessageFrench$ = this.httpClient.get(this.baseURL + '/welcome?lang=fr-CA', { responseType: 'text' }).pipe(
           map(message => {
             console.log('French Welcome Message:', message);
             return message;
           })
         );
      
         this.welcomeMessageEnglish$ = this.httpClient.get(this.baseURL + '/welcome?lang=en-US', { responseType: 'text' }).pipe(
           map(message => {
             console.log('English Welcome Message:', message);
             return message;
           })
         );
      
         this.announcePresentation$ = this.httpClient.get(this.baseURL + '/presentation', { responseType: 'text' }).pipe(
           map(presentation => {
             console.log('Presentation Announcement:', presentation);
             return presentation;
           })
         );


package edu.wgu.d387_sample_code.Localization;
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

app.component.html
  <h1>{{welcomeMessageFrench$ | async}}</h1>
  <h1>{{welcomeMessageEnglish$ | async}}</h1>


2. Modify the front end to display the price for a reservation in currency rates for U.S. dollars ($), Canadian dollars (C$), and euros (€) on different lines.
   Note: It is not necessary to convert the values of the prices.
   MODIFY:

        app.component.ts, line 70-71

            this.rooms.forEach( room => { room.priceCAD = room.price; room.priceEUR = room.price})
    
        app.component.ts, line 120-122

            priceCAD:string;
            priceEUR:string;

        app.component.html, line 86-90
            <strong> Price: CA${{room.priceCAD}} </strong> <br> 
            <strong> Price: EUR€{{room.priceEUR}} </strong> <br>

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
