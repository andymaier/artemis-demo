package de.predic8.simple;

import javax.jms.*;
import javax.naming.InitialContext;


public class Consumer {

   public static void main(final String[] args) throws Exception {

      InitialContext nctx = new InitialContext();
      ConnectionFactory cf = (ConnectionFactory) nctx.lookup("ConnectionFactory");

      try(JMSContext ctx = cf.createContext()) {

         Queue queue = ctx.createQueue("bestellungen");

         JMSConsumer consumer = ctx.createConsumer(queue);

         while (true) {
            System.out.println("Got: " + consumer.receiveBody(String.class));
         }
      }
   }
}
