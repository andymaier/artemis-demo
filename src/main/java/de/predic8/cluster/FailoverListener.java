package de.predic8.cluster;

import org.apache.activemq.artemis.api.core.client.*;

public class FailoverListener implements FailoverEventListener {
    @Override
    public void failoverEvent(FailoverEventType e) {
        System.out.println("Failover happend! " + e);
    }
}
