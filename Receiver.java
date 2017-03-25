/**
 * 
 */
package com.georgeljc.activemq;

/**
 * @author George_Li
 *
 */
import javax.jms.Connection;  
import javax.jms.ConnectionFactory;  
import javax.jms.Destination;  
import javax.jms.MessageConsumer;  
import javax.jms.Session;  
import javax.jms.TextMessage;  
import org.apache.activemq.ActiveMQConnection;  
import org.apache.activemq.ActiveMQConnectionFactory;  
  
public class Receiver {  
    public static void main(String[] args) {  
         
        ConnectionFactory connectionFactory;// ConnectionFactory �����ӹ�����JMS ������������   
        Connection connection = null;// Connection ��JMS �ͻ��˵�JMS Provider ������   
        Session session;// Session�� һ�����ͻ������Ϣ���߳�   
        Destination destination;  
        MessageConsumer consumer; // �����ߣ���Ϣ������ 
        connectionFactory = new ActiveMQConnectionFactory(  
                ActiveMQConnection.DEFAULT_USER,  
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");  
        try {  
            connection = connectionFactory.createConnection();// ����ӹ����õ����Ӷ��� 
            connection.start();  
            session = connection.createSession(Boolean.FALSE,  
                    Session.AUTO_ACKNOWLEDGE);  
            destination = session.createQueue("activemqDemoQueue");  
            consumer = session.createConsumer(destination);  
            while (true) {  
                TextMessage message = (TextMessage) consumer.receive(100000);  
                if (null != message) {  
                    System.out.println("Receiver gets messages:" + message.getText());  
                } else {  
                    break;  
                }  
            }  
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
}  
