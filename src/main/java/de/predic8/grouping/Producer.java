package de.predic8.grouping;

import javax.jms.*;
import javax.naming.InitialContext;


public class Producer {

    public static void main(String[] args) throws Exception {

        InitialContext nctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

        try(JMSContext ctx = cf.createContext()) {

            Queue queue = ctx.createQueue("bestellungen");
            JMSProducer producer = ctx.createProducer();
            
            for (int i = 1; i <= 10000; i++) {
                Message msg = ctx.createTextMessage(i + " Schnitzel bitte!");
                msg.setStringProperty("JMSXGroupID", "" + i % 10);
                producer.send(queue, msg);
                Thread.sleep(1000);
            }
        }
    }
}
