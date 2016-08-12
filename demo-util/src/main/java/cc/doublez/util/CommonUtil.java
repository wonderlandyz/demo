package cc.doublez.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

import java.util.regex.Pattern;

public class CommonUtil {

    private static final Pattern json_pattern_1 = Pattern.compile("^\\{(.*?)\\}$");
    private static final Pattern jaon_pattern_2 = Pattern.compile("^\\[(.*?)\\]$");

    private static final Log logger = LogFactory.getLog(CommonUtil.class);


    /**
     * 检查字符串是否是json格式的，是返回true，否返回false
     * 
     * @param content
     * @return
     */
    public static boolean checkIfJSON(String content) {
        if (StringUtils.isBlank(content)) {
            return false;
        }
        boolean b1 = json_pattern_1.matcher(content).find();
        boolean b2 = jaon_pattern_2.matcher(content).find();
        return (b1 || b2);
    }
    public static boolean isUrlSuccess(String url) {
        if (StringUtils.isBlank(url)) {
            return false;
        }
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
        try {
            int status = client.executeMethod(new GetMethod(url));
            logger.info("url:" + url + ", status:" + status);
            return status == HttpStatus.SC_OK;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

}
