package de.predic8.cluster;

import org.apache.activemq.artemis.jms.client.ActiveMQConnection;

import javax.jms.*;
import javax.naming.InitialContext;

import static javax.jms.Session.CLIENT_ACKNOWLEDGE;

public class Subscriber {

    public static void main(String[] args) throws Exception {

        InitialContext ic = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) ic.lookup("ConnectionFactory");
        

        Connection con = cf.createConnection();
        ((ActiveMQConnection) con).setFailoverListener(new FailoverListener());

        Session sess = con.createSession(true, CLIENT_ACKNOWLEDGE);
        Queue queue = sess.createQueue("bestellungen");
        
        //Queue queue = (Queue) ic.lookup("bestellungen");

        MessageConsumer consumer = sess.createConsumer(queue);

        con.start();

        System.out.println("Started");

        while (true) {
            try {
                TextMessage msg = (TextMessage) consumer.receive();
                System.out.println("Got: " + msg.getText());
                sess.commit();
            } catch (Exception e) {
                System.out.println("e = " + e);
            }
        }
    }
}
