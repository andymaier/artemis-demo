package de.predic8.core;

import org.apache.activemq.artemis.api.core.*;
import org.apache.activemq.artemis.api.core.client.*;

public class Producer {

    public static void main(String[] args) throws Exception {

        ServerLocator locator = ActiveMQClient.createServerLocator("tcp://localhost:61616");
        ClientSessionFactory fac =  locator.createSessionFactory();
        ClientSession session = fac.createSession();
        session.start();

        session.createAddress(SimpleString.toSimpleString("foo") , RoutingType.ANYCAST, true);

        ClientProducer producer = session.createProducer("foo");

        ClientMessage message = session.createMessage(true);
        message.getBodyBuffer().writeString("Hello Bonn");

        producer.send(message);
        producer.close();
        session.close();

    }
}
