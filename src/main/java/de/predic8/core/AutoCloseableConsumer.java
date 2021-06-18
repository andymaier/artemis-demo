package de.predic8.core;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;

public class AutoCloseableConsumer {

    public static void main(final String[] args) throws Exception {

        Queue queue = ActiveMQJMSClient.createQueue("bar");

        try ( ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
              JMSContext ctx = cf.createContext() ) {

            System.out.println("Got: " + ctx.createConsumer(queue).receiveBody(String.class));
        }
    }
}
