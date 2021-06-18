package de.predic8.misc;

import javax.jms.*;
import javax.naming.InitialContext;
import java.io.InputStream;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;


public class ByteMessageSender {

    public static void main(String[] args) throws Exception {

        InitialContext ctx = new InitialContext();

        ConnectionFactory cf = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        Connection con = cf.createConnection();

        Queue queue = (Queue) ctx.lookup("dateien");

        Session session = con.createSession(false, AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(queue);

        InputStream is = ClassLoader.class.getResourceAsStream("/jndi.properties");

        byte[] array = new byte[is.available()];
        is.read(array);

        BytesMessage msg = session.createBytesMessage();
        msg.writeBytes(array);

        producer.send(msg);

        ctx.close();
        con.close();


    }
}
