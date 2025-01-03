package edu.wgu.d387_sample_code;

import edu.wgu.d387_sample_code.Localization.GetBundle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Locale;
import java.util.concurrent.*;

@SpringBootApplication
public class D387SampleCodeApplication {
	private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(D387SampleCodeApplication.class, args);
		executorService.submit(new GetBundle(Locale.US));
		executorService.submit(new GetBundle(Locale.CANADA_FRENCH));

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			shutdownExecutorService();
		}));
	}

	private static void shutdownExecutorService() {
		try {
			executorService.shutdown();
			if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
		}
	}
}
