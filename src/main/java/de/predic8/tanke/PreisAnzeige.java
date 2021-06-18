package de.predic8.tanke;

import javax.jms.*;
import javax.naming.InitialContext;


public class PreisAnzeige {

    public static void main(final String[] args) throws Exception {

        InitialContext nctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

        try(JMSContext ctx = cf.createContext()) {
            ctx.setClientID("Tanke-" + args[0] );

            Topic topic = ctx.createTopic("tanke.preise");

            JMSConsumer consumer = ctx.createDurableConsumer(topic, "sub-" + args[0] );

            while (true) {
                System.out.println("1 Liter Urzeitsaft " + consumer.receiveBody(String.class) + " €");
            }
        }
    }
}
