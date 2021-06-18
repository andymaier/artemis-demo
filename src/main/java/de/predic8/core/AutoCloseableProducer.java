package de.predic8.core;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;

public class AutoCloseableProducer {

    public static void main(final String[] args) throws Exception {

        Queue queue = ActiveMQJMSClient.createQueue("bar");

        try ( ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
              JMSContext ctx = cf.createContext() ) {

            JMSProducer producer = ctx.createProducer();
            producer.send(queue, "Hi!");

        }
    }
}
