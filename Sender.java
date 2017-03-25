/**
 * 
 */
package com.georgeljc.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;  
import org.apache.activemq.ActiveMQConnectionFactory;  
/**
 * @author George_Li
 * 
 */
public class Sender {  
    private static final int SEND_NUMBER = 5;  
  
    public static void main(String[] args) {  
        ConnectionFactory connectionFactory; // Connection ：JMS 客户端到JMS  
        Connection connection = null; // Session： 一个发送或接收消息的线程  
        Session session; // Destination ：消息的目的地;消息发送给谁.  
        Destination destination;  
        MessageProducer producer;// MessageProducer：消息发送者
        connectionFactory = new ActiveMQConnectionFactory(  
                ActiveMQConnection.DEFAULT_USER,  
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");  
        try {  
            connection = connectionFactory.createConnection();  
            connection.start();  
            session = connection.createSession(Boolean.TRUE,  
                    Session.AUTO_ACKNOWLEDGE);  
            destination = session.createQueue("activemqDemoQueue");  
            producer = session.createProducer(destination);  
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//非持久性消息
            sendMessage(session, producer);  
            session.commit();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (null != connection)  
                    connection.close();  
            } catch (Throwable ignore) {  
            }  
        }  
    }  
  
    public static void sendMessage(Session session, MessageProducer producer)  
            throws Exception {  
        for (int i = 1; i <= SEND_NUMBER; i++) {  
            TextMessage message = session.createTextMessage("-SenderMesg-"  
                    + i);  
            System.out.println("Sender sends message：" + i);  
            producer.send(message);  
        }  
    }  
}  