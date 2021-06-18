package de.predic8.internal;

import org.apache.activemq.artemis.jms.client.ActiveMQMessage;

import javax.jms.*;
import javax.naming.InitialContext;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;


public class NotificationConsumer {

   public static void main(final String[] args) throws Exception {

      InitialContext ctx = new InitialContext();

      ConnectionFactory cf = (ConnectionFactory) ctx.lookup("ConnectionFactory");
      Connection con = cf.createConnection();

      Queue queue = (Queue) ctx.lookup("activemq.notifications");

      Session session = con.createSession(false, AUTO_ACKNOWLEDGE);

      MessageConsumer consumer = session.createConsumer(queue);

      con.start();

      while (true) {
         ActiveMQMessage msg = (ActiveMQMessage) consumer.receive();
         System.out.println("Got: " + msg);
      }
   }
}
