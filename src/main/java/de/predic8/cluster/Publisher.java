package de.predic8.cluster;

import org.apache.activemq.artemis.jms.client.ActiveMQConnection;

import javax.jms.*;
import javax.naming.InitialContext;

import static javax.jms.Session.*;

public class Publisher {

    public static void main(String[] args) throws Exception {

        InitialContext ic = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory)ic.lookup("ConnectionFactory");

        //Queue orderQueue = (Queue)ic.lookup("bestellungen");

        Connection con = cf.createConnection();
        ((ActiveMQConnection) con).setFailoverListener(new FailoverListener());

        Session session = con.createSession(true, CLIENT_ACKNOWLEDGE);
        Queue orderQueue = session.createQueue("bestellungen");
        MessageProducer producer = session.createProducer(orderQueue);

        con.start();
        boolean success = false;

        for (int i = 0; i < 1000; i++) {
            while(!success) {
                try {
                    System.out.println("Sende " + i);
                    TextMessage message = session.createTextMessage("Bestellung " + i);
                    producer.send(message);
                    session.commit();
                    Thread.sleep(1000);
                    success = true;
                } catch (Exception e) {
                    System.out.println("e = " + e);
                }
            }
            success = false;
        }

    }
}
