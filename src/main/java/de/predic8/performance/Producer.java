package de.predic8.performance;

import javax.jms.*;
import javax.naming.InitialContext;


public class Producer {

    public static void main(String[] args) throws Exception {


        InitialContext nctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

        try(JMSContext ctx = cf.createContext()) {

            Queue queue = ctx.createQueue("bestellungen");
            JMSProducer producer = ctx.createProducer().setDeliveryMode(DeliveryMode.PERSISTENT);

            long t1 = System.currentTimeMillis();

            int i;
            for (i = 0; i < 1_000_000; i++) {
                producer.send(queue,i + " Schnitzel bitte!");
            }

            long dauer = System.currentTimeMillis() - t1;

            System.out.println("Done after: " + (dauer/1000.0) + " s.");
            System.out.println(i / (dauer/1000.0) + " Nachrichten pro Sek.");
        }
    }
}
