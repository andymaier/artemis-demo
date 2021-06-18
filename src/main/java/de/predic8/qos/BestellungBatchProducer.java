package de.predic8.qos;

import javax.jms.*;
import javax.naming.InitialContext;

import static java.lang.System.currentTimeMillis;


public class BestellungBatchProducer {

    public static void main(String[] args) throws Exception {

        InitialContext nctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

        try(JMSContext ctx = cf.createContext(JMSContext.SESSION_TRANSACTED)) {

            Queue queue = ctx.createQueue("bestellungen.neu");            
            JMSProducer producer = ctx.createProducer();

            long t1 = currentTimeMillis();

            int i =0;
            /*for (i = 1; i <= 1_000_000; i++) {
                producer.send(queue, i + " Schnitzel bitte!");
                if (i % 1000 == 0) {
                    ctx.commit();
                    System.out.println(i);
                }
            }
            ctx.commit();*/
            
            
            while(true){
                i++;
                producer.send(queue, i + " Schnitzel bitte!");
                if (i % 1000 == 0) {
                    ctx.commit();
                    System.out.println(i);
                }
            } 
            
            
            /*double dauer = (currentTimeMillis() - t1) / 1000;

            System.out.println("Done after: " + dauer + " s.");
            System.out.println(i / dauer + " Nachrichten pro Sek.");
            */
        }

        //System.out.println("Fertig.");
    }
}
