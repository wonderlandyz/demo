package cc.doublez.platform.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Message;
/**
 * Created by yz on 2016/7/13
 */
@Component("topicSender")
public class Publisher {
    @Autowired
    @Qualifier("topicJmsTemplate")
    private JmsTemplate jmsTemplate;

    public void send(String topicName,final String message){
        jmsTemplate.send(topicName, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}
