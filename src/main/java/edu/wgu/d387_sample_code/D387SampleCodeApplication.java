package edu.wgu.d387_sample_code;
import edu.wgu.d387_sample_code.Localization.GetBundle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Locale;
import java.util.concurrent.*;

@SpringBootApplication
public class D387SampleCodeApplication {
	private static ExecutorService executorService = Executors.newFixedThreadPool(2);

	public static void main(String[] args) {
		SpringApplication.run(D387SampleCodeApplication.class, args);
		/*
		ConfigurableApplicationContext context = SpringApplication.run(D387SampleCodeApplication.class, args);

		executorService.submit(new GetBundle(Locale.US));
		executorService.submit(new GetBundle(Locale.CANADA_FRENCH));

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
		*/
		GetBundle english = new GetBundle(Locale.US);
		GetBundle french = new GetBundle(Locale.CANADA_FRENCH);

		Thread enThread = new Thread(english);
		Thread frThread = new Thread(french);

		enThread.start();
		frThread.start();
	}
}
