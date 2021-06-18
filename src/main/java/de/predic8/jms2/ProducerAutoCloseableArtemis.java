package de.predic8.jms2;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;

import static javax.jms.DeliveryMode.PERSISTENT;

public class ProducerAutoCloseableArtemis {

    public static void main(final String[] args) throws Exception {

        Queue queue = ActiveMQJMSClient.createQueue("baz");

        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext ctx = cf.createContext()) {

            JMSProducer producer = ctx.createProducer()
                    .setDeliveryMode(PERSISTENT)
                    .setProperty("hossa","7");

            producer.send(queue, "Hi!");

        }

    }
}
