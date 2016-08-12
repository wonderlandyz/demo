package cc.doublez.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtil {

	private static final Logger LOG = LoggerFactory.getLogger(SerializeUtil.class);
	
	final static ObjectMapper mapper;
	static{
		mapper = new ObjectMapper();
	}

	/**
	 * Object 2 JSON
	 * @param object
	 * @return
	 */
	public static String writeAsString(Object object){
		try {
			return mapper.writeValueAsString(object);
		}catch (Exception e) {
			LOG.warn("serialize object has error");
		}
		return "";
	}

	/**
	 * JSON转JAVA对象
	 * @param content
	 * @param type
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T readValue(String content, Class<T> type) throws Exception{
		if(!CommonUtil.checkIfJSON(content)){
			throw new Exception("格式错误");
		}
		try {
			return mapper.readValue(content, type);
		} catch (Exception e) {
			LOG.warn("serialize parse value["+content+"] to object has error");
			throw new Exception("serialize object has error");
		} 
	}

	/**
	 * JSON格式字符串转换为泛型对象
	 * @param content
	 * @param valueTypeRef
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T readValue(String content, TypeReference valueTypeRef) throws Exception{
		if(!CommonUtil.checkIfJSON(content)){
			return null;
		}
		try {
			return mapper.readValue(content, valueTypeRef);
		} catch (Exception e) {
			LOG.warn("serialize parse value["+content+"] to object has error");
			throw new Exception("serialize object has error");
		} 
	}
}
