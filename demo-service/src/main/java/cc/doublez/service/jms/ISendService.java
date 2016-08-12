package cc.doublez.service.jms;

import cc.doublez.domain.pojo.user.User;

/**
 * Created by yz on 2016/7/11
 */
public interface ISendService {
    public void send(final User user);
}
