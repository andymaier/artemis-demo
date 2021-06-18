package de.predic8.transactions;

import org.apache.activemq.artemis.api.core.management.ObjectNameBuilder;

import javax.management.*;
import javax.management.remote.*;
import java.util.HashMap;

import static org.apache.activemq.artemis.api.config.ActiveMQDefaultConfiguration.getDefaultJmxDomain;

public class ListTransactions {

    static String JMXURL = "service:jmx:rmi:///jndi/rmi://localhost:3001/jmxrmi";

    public static void main(String[] args) throws Exception {

        JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(JMXURL), new HashMap<String, String>());

        MBeanServerConnection mbsc = connector.getMBeanServerConnection();

        ObjectName so = ObjectNameBuilder.create(getDefaultJmxDomain(), "0.0.0.0", true).getActiveMQServerObjectName();
        String[] infos = (String[]) mbsc.invoke(so, "listPreparedTransactions", null, null);

        System.out.println("Prepared transactions: ");
        for (String i : infos) {
            System.out.println(i);
        }

    }
}
