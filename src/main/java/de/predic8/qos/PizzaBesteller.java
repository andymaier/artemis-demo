package de.predic8.qos;

import javax.jms.*;
import javax.naming.InitialContext;


public class PizzaBesteller {

    public static void main(String[] args) throws Exception {


        InitialContext nctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

        try(JMSContext ctx = cf.createContext()) {

            Queue queue = ctx.createQueue("bestellungen.bonn.pizza");
            JMSProducer producer = ctx.createProducer();

            //producer.send(queue, "Pizza Napoli");
            int i = 0;
            while (true) {
                producer.send(queue, "Pizza Napoli " + i++);
                Thread.sleep(1000);
            }

        }

        //System.out.println("Bestellt.");
    }
}
