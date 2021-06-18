package de.predic8.jms1;

import javax.jms.*;
import javax.naming.InitialContext;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;


public class JMS1Producer {

    public static void main(String[] args) throws Exception {

        InitialContext ctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        Connection con = cf.createConnection();

        Queue queue = (Queue) ctx.lookup("bestellungen");

        Session session = con.createSession(false, AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(queue);

        long t1 = System.currentTimeMillis();

        for (int i = 0; i < 10_000; i++) {
            TextMessage msg = session.createTextMessage(i + " Schnitzel bitte!");
            producer.send(msg);
        }

        double dauer = (System.currentTimeMillis() - t1) / 1000.0;

        System.out.println("Done after: " + dauer + " s.");
        System.out.println(10_000 / dauer + " Nachrichten pro Sek.");

        con.close();
        ctx.close();

    }
}
