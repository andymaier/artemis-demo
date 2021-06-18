package de.predic8.tanke;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Random;


public class PreisSender {

    public static void main(String[] args) throws Exception {

        InitialContext nctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

        System.out.println("Sending...");

        try(JMSContext ctx = cf.createContext()) {

            Topic topic = ctx.createTopic("tanke.preise");
            JMSProducer producer = ctx.createProducer();

            for (int i = 0; i < 10; i++) {
               producer.send(topic,"" + (1 + (new Random().nextDouble())));
            }
        }
    }
}
