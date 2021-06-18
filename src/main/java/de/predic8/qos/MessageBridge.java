package de.predic8.qos;

import javax.jms.*;
import javax.naming.InitialContext;


public class MessageBridge {

   public static void main(final String[] args) throws Exception {

      InitialContext nctx = new InitialContext();
      ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

      try(JMSContext ctx = cf.createContext(JMSContext.SESSION_TRANSACTED)) {

         Queue neu = ctx.createQueue("bestellungen.neu");
         Queue verarbeitet = ctx.createQueue("bestellungen.verarbeitet");

         JMSConsumer consumer = ctx.createConsumer(neu);
         JMSProducer producer = ctx.createProducer();

         while (true) {

            String bestellung = consumer.receiveBody(String.class);
            System.out.println("Got: " + bestellung);
            bestellung = bestellung.toUpperCase();
            System.out.println("Sende: " + bestellung);
            producer.send(verarbeitet, bestellung);
            System.out.println("gesendet");

            ctx.commit();
         }
      }
   }
}
