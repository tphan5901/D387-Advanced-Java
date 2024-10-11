C. Modify the Landon Hotel scheduling application for localization and internationalization by doing the following:
1. Install the Landon Hotel scheduling application in your integrated development environment (IDE). Modify the Java classes of application to display a welcome message by doing the following:
   a. Build resource bundles for both English and French (languages required by Canadian law). Include a welcome message in the language resource bundles.

Resource Bundle:
            en_us.properties
                hello=Hi!
                welcome=Welcome to the Landon Hotel
                presentation.message = there is a presentation at:

            fr_ca.properties
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

        app.component.ts
            this.rooms.forEach(room => {
                if (room.price) {
                  room.priceCAD = room.price;
                  room.priceEUR = room.price;
                }

        app.component.ts
            priceCAD:string;
            priceEUR:string;

        app.component.html
                    <p>Room price:  ${{ room.price }}</p>
                    <p>Price (CAD): ${{ room.priceCAD }}</p>
                    <p>Price (EUR): ${{ room.priceEUR }}</p>

3. Display the time for an online live presentation held at the Landon Hotel by doing the following:
     a) Write a Java method to convert times between eastern time (ET), mountain time (MT), and coordinated universal time (UTC) zones.

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

b. Use the time zone conversion method from part C3a to display a message stating the time in all three times zones in hours and minutes for an online,
live presentation held at the Landon Hotel. The times should be displayed as ET, MT, and UTC.

app.component.ts, Lines: 30-60

this.announcePresentation$ = this.httpClient.get(this.baseURL + '/presentation', {responseType: 'text'} )
this.announcePresentation$ = this.httpClient.get(this.baseURL + '/presentation', { responseType: 'text' }).pipe(

map(presentation => {
    console.log('Presentation Announcement:', presentation);
    return presentation;
    })
);

app.component.html Line: 33

  <div class="scene" id="presentation">
        <h1>{{announcePresentation$ | async}}</h1>
  </div>


D.
1. Create Dockerfile 

FROM openjdk:11
EXPOSE 8080
COPY target/dockerized-application.jar /usr/src/dockerized-application.jar
WORKDIR /usr/src
ENTRYPOINT ["java", "-jar", "dockerized-application.jar"]

2.

3. Describe how you would deploy the current multithreaded Spring application to the cloud. 
Include the name of the cloud service provider you would use.

I would use AWS to deploy the container since it is the most popular cloud environment known in the software industry. 
First create the Docker Container. Second, package the project using Maven and copy the .jar file to a base image. 
Make the app accessible by configuring the route to a port number. Lastly, upload the container to a registry. 
Once that is done, deploy the container application to an AWS instance.