package de.predic8.qos;

import java.util.Random;

import javax.jms.*;
import javax.naming.InitialContext;

public class PizzaService {

    public static void main(String[] args) throws Exception {

        InitialContext nctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

        try (JMSContext ctx = cf.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {

            //Queue queue = ctx.createQueue("bestellungen.bonn.pizza");
            Queue queue = ctx.createQueue("bestellungen.neu");

            JMSConsumer consumer = ctx.createConsumer(queue);

            while (true) {
                try {
                    Message msg = consumer.receive();
                    System.out.println();
                    System.out.println("JMSRedelivered: " + msg.getJMSRedelivered());
                    System.out.println("JMSXDeliveryCount: " + msg.getStringProperty("JMSXDeliveryCount"));
                    //Logik
                    System.out.println("Body: " + msg.getBody(String.class));
                    /*if ((new Random().nextInt(3)) == 2)
                        ctx.rollback();
                    else
                        ctx.commit();*/
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        }
    }
}
