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
         
        ConnectionFactory connectionFactory;// ConnectionFactory ：连接工厂，JMS 用它创建连接   
        Connection connection = null;// Connection ：JMS 客户端到JMS Provider 的连接   
        Session session;// Session： 一个发送或接收消息的线程   
        Destination destination;  
        MessageConsumer consumer; // 消费者，消息接收者 
        connectionFactory = new ActiveMQConnectionFactory(  
                ActiveMQConnection.DEFAULT_USER,  
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");  
        try {  
            connection = connectionFactory.createConnection();// 构造从工厂得到连接对象 
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
