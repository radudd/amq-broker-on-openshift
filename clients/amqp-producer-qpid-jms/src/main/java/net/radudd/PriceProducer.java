package net.radudd;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.print.attribute.standard.Destination;

/**
 * A bean producing random prices every 5 seconds and sending them to the prices JMS queue.
 */
@ApplicationScoped
public class PriceProducer implements Runnable {

    @Inject
    ConnectionFactory connectionFactory;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    void onStart(@Observes StartupEvent ev) {
        scheduler.scheduleWithFixedDelay(this, 0L, 5L, TimeUnit.SECONDS);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }

    @Override
    public void run() {      
      try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
        jakarta.jms.Destination queue = context.createQueue("foo");
        JMSProducer producer = context.createProducer();
        while (true) {
            try {
                // Create TS message
                // Get the current date and time
                LocalDateTime now = LocalDateTime.now();
                // Define the format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // Format the current date and time
                String formattedNow = now.format(formatter);
                producer.send(queue, formattedNow);
                System.out.println("Sent message: " + formattedNow);

                // Sleep for one second before sending the next message
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Exit loop if the thread is interrupted
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
  }
}
}