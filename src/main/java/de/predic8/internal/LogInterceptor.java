package de.predic8.internal;

import org.apache.activemq.artemis.api.core.*;
import org.apache.activemq.artemis.core.protocol.core.Packet;
import org.apache.activemq.artemis.core.protocol.core.impl.wireformat.SessionSendMessage;
import org.apache.activemq.artemis.spi.core.protocol.RemotingConnection;

/**
 * broker.xml:
 *
 *  <remoting-incoming-interceptors>
 *          <class-name>de.predic8.internal.LogInterceptor</class-name>
 *       </remoting-incoming-interceptors>
 *
 */
public class LogInterceptor implements Interceptor {

    @Override
    public boolean intercept(final Packet packet, final RemotingConnection connection) throws ActiveMQException {
        System.out.println("Intercepted!");
        System.out.println("Packet: " + packet.getClass().getName());
        System.out.println("RemotingConnection: " + connection.getRemoteAddress());

        if (packet instanceof SessionSendMessage) {
            SessionSendMessage paket = (SessionSendMessage) packet;
            Message msg = paket.getMessage();

        }

        return true; // Continue
    }

}
