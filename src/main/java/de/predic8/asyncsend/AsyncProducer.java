package de.predic8.asyncsend;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.concurrent.atomic.AtomicInteger;


public class AsyncProducer {

    /**
     * Set on Connection URI!
     * connectionFactory.ConnectionFactory=tcp://127.0.0.1:61616?confirmationWindowSize=1000000
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        InitialContext ctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) ctx.lookup("ConnectionFactory");

        AtomicInteger success = new AtomicInteger();
        AtomicInteger failure = new AtomicInteger();

        try(JMSContext jmsCtx = cf.createContext()) {

            Queue queue = jmsCtx.createQueue("bestellungen");
            JMSProducer producer = jmsCtx.createProducer()
                    .setAsync(new CompletionListener() {  // Async oder nicht?
                        @Override
                        public void onCompletion(Message msg) {
                            success.incrementAndGet();
                        }

                        @Override
                        public void onException(Message msg, Exception exception) {
                            failure.incrementAndGet();
                        }
                    })
                    .setDeliveryMode(DeliveryMode.PERSISTENT); // PERSISTENT oder NON_PERSISTENT?

            long t1 = System.currentTimeMillis();

            for (int i = 0; i < 1_000_000; i++) {
                producer.send(queue,i + " Schnitzel bitte!");
            }

            long dauer = System.currentTimeMillis() - t1;

            System.out.println("Done after: " + (dauer/1000.0) + " s.");
            System.out.println(1_000_000 / (dauer/1000.0) + " Nachrichten pro Sek.");

            while(success.get() < 1_000_000) {
                System.out.println("Success: " + success);
                System.out.println("Failure: " + failure);
                Thread.sleep(1000);
            }
            System.out.println("Success: " + success);
            System.out.println("Failure: " + failure);
        }
    }
}
