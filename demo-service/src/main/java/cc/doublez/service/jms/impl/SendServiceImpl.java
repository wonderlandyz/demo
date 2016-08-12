package cc.doublez.service.jms.impl;

import cc.doublez.domain.pojo.user.User;
import cc.doublez.service.jms.ISendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by yz on 2016/7/11
 */
@Service("sendService")
public class SendServiceImpl implements ISendService {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Override
    public void send(final User user) {
        jmsTemplate.convertAndSend("test.queue",user);
        /*jmsTemplate.send("test.queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(user);
            }
        });*/
    }
}
