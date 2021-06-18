package de.predic8.simple;

import javax.jms.*;
import javax.naming.InitialContext;


public class Producer {

    public static void main(String[] args) throws Exception {

        InitialContext nctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

        try(JMSContext ctx = cf.createContext()) {

            Queue queue = ctx.createQueue("bestellungen");
            JMSProducer producer = ctx.createProducer().setDeliveryMode(DeliveryMode.NON_PERSISTENT);


            for (int i = 0; i < 10; i++) {
                producer.send(queue, i + " Schnitzel bitte! - TTL").setTimeToLive(1000L); 
            }            

        }

        System.out.println("Done.");
    }
}
