package cc.doublez.platform.jms;

import cc.doublez.domain.pojo.user.User;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created by yz on 2016/7/13
 */
@Service("messageListener")
public class ReceiveService implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objMsg = (ObjectMessage) message;
                User user;
                user = (User) objMsg.getObject();
                System.out.println(user.getPhone()+"test mq");
            } else {
                System.out.println("unsupported Message type"
                        + message.getJMSType());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
