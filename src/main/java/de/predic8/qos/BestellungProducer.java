package de.predic8.qos;

import javax.jms.*;
import javax.naming.InitialContext;


public class BestellungProducer {

    public static void main(String[] args) throws Exception {

        InitialContext nctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

        try(JMSContext ctx = cf.createContext()) {

            Queue queue = ctx.createQueue("bestellungen.neu");
            JMSProducer producer = ctx.createProducer()
                    .setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            producer.send(queue, "Schnitzel bitte!");
        }

        System.out.println("Fertig.");
    }
}
