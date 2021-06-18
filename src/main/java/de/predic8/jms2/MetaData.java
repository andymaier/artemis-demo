package de.predic8.jms2;

import javax.jms.*;
import javax.naming.*;
import java.util.Iterator;

public class MetaData {

    public static void main(String[] args) throws Exception {

        InitialContext ctx = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        Connection con = cf.createConnection();

        ConnectionMetaData meta = con.getMetaData();

        System.out.println("JMSMajorVersion: "+ meta.getJMSMajorVersion());
        System.out.println("JMSMinorVersion: "+ meta.getJMSMinorVersion());
        System.out.println("JMSVersion: "+ meta.getJMSVersion());

        System.out.println("JMSProvider: "+ meta.getJMSProviderName() + " Version: " + meta.getProviderVersion());

        System.out.println();

        System.out.println("JMSXPropertyNames: ");

        for (Iterator it = meta.getJMSXPropertyNames().asIterator(); it.hasNext(); ) {
            String name = (String) it.next();
            System.out.println(name);
        }

    }
}
