package cc.doublez.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理类
 */
public class StringUtil {

    public static final String EMPTY = "";
    /**
     * 取指定小数位的浮点数,不够小数位数时补零
     * 
     * @param floStr
     * @return
     */
    public static String paseFloat(String floStr, int location) {
        if (floStr == null) return "";
        int index = floStr.indexOf(".");
        // 如果没有小数点.则加一个小数点.
        if (index == -1) {
            floStr = floStr + ".";
        }
        index = floStr.indexOf(".");
        int leave = floStr.length() - index;
        // 如果小于指定位数则在后面补零
        for (; leave <= location; leave++) {
            floStr = floStr + "0";
        }
        return floStr.substring(0, index + location + 1);
    }

    /**
     * 把字符型数字转换成整型.
     * 
     * @param str 字符型数字
     * @return int 返回整型值。如果不能转换则返回默认值defaultValue.
     */
    public static int getInt(String str, int defaultValue) {
        if (str == null) return defaultValue;
        if (isInt(str)) {
            return Integer.parseInt(str);
        } else {
            return defaultValue;
        }
    }

    /** 取整数，默认值-1 */
    public static int getInt(String str) {
        return getInt(str, -1);
    }

    /**
     * 判断一个字符串是否为数字
     */
    public static boolean isInt(String str) {
        return str.matches("-?\\d+");
    }

    
    /**
	 * 判断字符串是否为null或空.
	 * 
	 * <pre>
	 * isEmpty(null)      = true
	 * isEmpty(&quot;&quot;)        = true
	 * isEmpty(&quot; &quot;)       = true
	 * isEmpty(&quot;bob&quot;)     = false
	 * isEmpty(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @return true if <code>(str == null || str.trim().length() == 0)</code>,
	 *         otherwise false.
	 * @since ls@07.0624
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	/**
	 * 和 {@link #isEmpty(String)} 相反。
	 * 
	 */
	public static boolean isNotEmpty(String str) {
		return false == isEmpty(str);
	}

    /** 判断指定的字符串是否是空串 */
    public static boolean isBlank(String str) {
        if (isEmpty(str)) return true;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /** 针对字符串为NULL的处理 */
    public static String avoidNull(String str) {
    	return (str == null) ? "" : str;
    }

    /**
     * 判断2个字符串是否相等
     */
    public static boolean isequals(String str1, String str2) {
        return str1.equals(str2);
    }

    /**
     * 在长数字前补零
     * 
     * @param num 数字
     * @param length 输出位数
     */
    public static String addzero(long num, int length) {
        String str = "";
        if (num < Math.pow(10, length - 1)) {
            for (int i = 0; i < (length - (num + "").length()); i++) {
                str += "0";
            }
        }
        str = str + num;
        return str;
    }

    /**
     * 在数字前补零
     * 
     * @param num 数字
     * @param length 输出位数
     */
    public static String addzero(int num, int length) {
        String str = "";
        if (num < Math.pow(10, length - 1)) {
            for (int i = 0; i < (length - (num + "").length()); i++) {
                str += "0";
            }
        }
        str = str + num;
        return str;
    }

    /**
     * 使HTML的标签失去作用*
     * 
     * @param input 被操作的字符串
     * @return String
     */
    public static final String escapeHTMLTag(String input) {
        if (input == null) return "";
        input = input.trim().replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll("\n", "<br>");
        input = input.replaceAll("'", "&#39;");
        input = input.replaceAll("\"", "&quot;");
        input = input.replaceAll("\\\\", "&#92;");
        return input;
    }

    /**
     * 还原html标签
     * 
     * @param input
     * @return
     */
    public static final String unEscapeHTMLTag(String input) {
        if (input == null) return "";
        input = input.trim().replaceAll("&amp;", "&");
        input = input.replaceAll("&lt;", "<");
        input = input.replaceAll("&gt;", ">");
        input = input.replaceAll("<br>", "\n");
        input = input.replaceAll("&#39;", "'");
        input = input.replaceAll("&quot;", "\"");
        input = input.replaceAll("&#92;", "\\\\");
        return input;
    }

    /**
     * 把数组合成字符串
     * 
     * @param str
     * @param seperator
     * @return
     */
    public static String toString(String[] str, String seperator) {
        if (str == null || str.length == 0) return "";
        StringBuffer buf = new StringBuffer();
        for (int i = 0, n = str.length; i < n; i++) {
            if (i != 0) buf.append(seperator);
            buf.append(str[i]);
        }
        return buf.toString();
    }

    /**
     * 把字符串分隔成数组
     * 
     * @param str 字符 如： 1/2/3/4/5
     * @param seperator 分隔符号 如: /
     * @return String[] 字符串树组 如: {1,2,3,4,5}
     */
    public static String[] split(String str, String seperator) {
        StringTokenizer token = new StringTokenizer(str, seperator);
        int count = token.countTokens();
        String[] ret = new String[count];
        for (int i = 0; i < count; i++) {
            ret[i] = token.nextToken();
        }
        return ret;
    }

    /**
     * 按指定的分隔符分隔数据，有N个分隔符则返回一个N+1的数组
     * 
     * @param str
     * @param seperator
     * @return
     */
    public static String[] splitHaveEmpty(String str, String seperator) {
        // 分隔符前后增加一个空白字符
        str = str.replaceAll(seperator, " " + seperator + " ");
        return str.split(seperator);
    }

    /**
     * @param len 需要显示的长度(<font color="red">注意：长度是以byte为单位的，一个汉字是2个byte</font>)
     * @param symbol 用于表示省略的信息的字符，如“...”,“>>>”等。
     * @return 返回处理后的字符串
     */
    public static String getSub(String str, int len, String symbol) {
        if (str == null) return "";
        try {
            int counterOfDoubleByte = 0;
            byte[] b = str.getBytes("gbk");
            if (b.length <= len) return str;
            for (int i = 0; i < len; i++) {
                if (b[i] < 0) counterOfDoubleByte++; // 通过判断字符的类型来进行截取
            }
            if (counterOfDoubleByte % 2 == 0)
                str = new String(b, 0, len, "gbk") + symbol;
            else
                str = new String(b, 0, len - 1, "gbk") + symbol;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    /**
     * 按字节获取字符串的长度,一个汉字占二个字节
     * 
     * @param str
     * @return
     */
    public static int getLen(String str) {
        try {
            byte[] b = str.getBytes("gbk");
            return b.length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getSub(String str, int len) {
        return getSub(str, len, "");
    }

    /** 只取某一字符串的前几个字符，后面以...表示 */
    public static String getAbc(String str, int len) {
        return getAbc(str, len, "...");
    }

    /** 截取多少长度前的一断字符串 */
    public static String getAbc(String str, int len, String symbol) {
        if (str == null) return null;
        if (len < 0) return "";
        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len).concat(symbol);
        }
    }
    
    public static String hideBankCard(String str) {
    	if (str == null) return null;
    	if(str.length() <= 4) return "尾号".concat(str);
    	
    	return "尾号".concat(str.substring(str.length()-4, str.length()));
    }
    
    public static String hideAliAccount(String str) {
    	if(isPhone(str)){
    		return str;
    	}
    	if(isEmail(str)){
    		String[] es = str.split("@");
    		if(es[0].length() > 3){
    			return es[0].substring(0, 3).concat("@").concat(es[1]);
    		}else{
    			return es[0].concat("@").concat(es[1]);
    		}
    	}
    	return str;
    }

    /**
     * 截取某字符串中两个字符串之间的一段字符串 eg:StringUtil.subBetween("yabczyabcz", "y", "z") = "abc"
     */
    public static String subBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    /**
     * 截取某字符串中最后出现指定字符串之后的一段字符串 StringUtil.subAfterLast("abcba", "b") = "a"
     */
    public static String subAfterLast(String str, String separator) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (separator == null || separator.length() == 0) {
            return "";
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == (str.length() - separator.length())) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取某字符串中最后出现指定字符串之前的一段字符串 StringUtil.subBeforeLast("abcba", "b") = "abc"
     */
    public static String subBeforeLast(String str, String separator) {
        if (str == null || separator == null || str.length() == 0 || separator.length() == 0) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 截取某字符串中指定字符串之后的一段字符串 StringUtil.subAfter("abcba", "b") = "cba"
     */
    public static String subAfter(String str, String separator) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (separator == null) {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取某字符串中指定字符串之前的一段字符串 StringUtil.subBefore("abcbd", "b") = "a"
     */
    public static String subBefore(String str, String separator) {
        if (str == null || separator == null || str.length() == 0) {
            return str;
        }
        if (separator.length() == 0) {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /** 判断两个字符串中是否含有相同的元素 */
    public static boolean containsNone(String str, String invalidChars) {
        if (str == null || invalidChars == null) {
            return true;
        }
        return containsNone(str, invalidChars.toCharArray());
    }

    /** 判断字符串中是否含有字符数组中的元素 */
    public static boolean containsNone(String str, char[] invalidChars) {
        if (str == null || invalidChars == null) {
            return true;
        }
        int strSize = str.length();
        int validSize = invalidChars.length;
        for (int i = 0; i < strSize; i++) {
            char ch = str.charAt(i);
            for (int j = 0; j < validSize; j++) {
                if (invalidChars[j] == ch) {
                    return false;
                }
            }
        }
        return true;
    }

    /** 判断字符串中是否包含指定字符串 */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return (str.indexOf(searchStr) >= 0);
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     * 
     * @param email
     * @return boolean
     */
    static Pattern emailer;

    public static boolean isEmail(String email) {
        if (emailer == null) {
            String check = "^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            emailer = Pattern.compile(check);
        }
        Matcher matcher = emailer.matcher(email);
        return matcher.matches();
    }

    /**
     * 转换html特殊字符为html码
     * 
     * @param str
     * @return
     */
    public static String htmlSpecialChars(String str) {
        try {
            if (str.trim() == null) {
                return "";
            }
            StringBuffer sb = new StringBuffer();
            char ch = ' ';
            for (int i = 0; i < str.length(); i++) {
                ch = str.charAt(i);
                if (ch == '&') {
                    sb.append("&amp;");
                } else if (ch == '<') {
                    sb.append("&lt;");
                } else if (ch == '>') {
                    sb.append("&gt;");
                } else if (ch == '"') {
                    sb.append("&quot;");
                } else if (ch == '\'') {
                    sb.append("&#039;");
                } else if (ch == '(') {
                    sb.append("&#040;");
                } else if (ch == ')') {
                    sb.append("&#041;");
                } else if (ch == '@') {
                    sb.append("&#064;");
                } else {
                    sb.append(ch);
                }
            }
            if (sb.toString().replaceAll("&nbsp;", "").replaceAll("　", "").trim().length() == 0) {
                return "";
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 
     * @param word : 输入的字符串
     * @return 是否输入的是字符
     */
    public boolean CharIsLetter(String word) {
        boolean sign = true; // 初始化标志为为'true'
        for (int i = 0; i < word.length(); i++) { // 遍历输入字符串的每一个字符
            if (!Character.isLetter(word.charAt(i))) { // 判断该字符是否为英文字符
                sign = false; // 若有一位不是英文字符，则将标志位修改为'false'
            }
        }
        return sign;// 返回标志位结果
    }

    /**
     * 生成小标题的正则表达式替换,mark是大标题的镭点标识 小标题的锚点标识按mark加出现的序号的方式生成，如第一个出现的小标题为： mark1,第个出现的为mark2，如此类推。
     * 小标题示例:
     * <ol>
     * <li><span class="menuId">3.1</span><a href="#">历史著名运动员</a></li>
     * <li><span class="menuId">3.2</span><a href="#">2004年奥运会著名运动员</a></li>
     * <li><span class="menuId">3.3</span><a href="#">其他运动员</a></li>
     * <li><span class="menuId">3.4</span><a href="#">其他著名人物</a></li>
     * </ol>
     * 返回一个字符串数组，序号1为解析后的数据，序号2为小题标数据
     * 
     * @param input 需要解析的原始数据
     * @param mark 大标题的锚点标识
     * @param bigProIndex 大标题的索引序号
     * @return
     */
    public static String[] findSpecData(String input, String mark, String bigProIndex) {
        // 用来存放处理解析后的文本数据
        StringBuffer sb = new StringBuffer();
        // 用来生成小项的锚点
        StringBuffer smallPro = new StringBuffer("<ol>").append("\n");
        // 用来存放小项的标号
        int index = 1;

        String regex = "(<div class=s_title>)(.*?)(</div>)";
        Matcher testM = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(input);
        while (testM.find()) {
            testM.appendReplacement(sb, "<div class=\"s_title\"><a name=\"" + mark + index + "\"></a>$2$3");
            // 小标题名称
            String smallName = testM.group(2);
            smallPro.append("<li><span class=\"menuId\" >").append(bigProIndex).append(".").append(index).append("</span><a href=\"#").append(mark).append(index).append("\">").append(smallName).append("</a></li>").append("\n");
            // 索引号自加
            index++;
        }
        // 如果存在小标题，
        if (index != 1) {
            // 组装小标题
            smallPro.append("</ol>");
            // 生成带小标题锚点的数据
            testM.appendTail(sb);
            return new String[] {sb.toString(), smallPro.toString()};
        }
        return null;
    }

    /**
     * 字符串截取
     * 
     * @param str 要处理的字符串
     * @param beginIndex 开始位置
     * @param endIndex 结束位置
     * @return
     */
    public String substr(String str, int beginIndex, int endIndex) {
        if (isBlank(str)) {
            return "";
        }
        if (endIndex == -1) {
            return str.substring(beginIndex);
        }

        if (endIndex > str.length()) {
            endIndex = str.length();
        }
        return str.substring(beginIndex, endIndex);
    }

    /**
     * 随机生成几个不同的数
     * 
     * @param lenth
     * @param num
     * @return
     */
	public static int[] random5(int lenth, int num) {

        Random rd = new Random();
        HashSet<Integer> set = new HashSet<Integer>();
        while (true) {
            int i = rd.nextInt(lenth);
            set.add(new Integer(i));
            if (set.size() == num) {
                break;
            }
        }
        Iterator<Integer> iter = set.iterator();
        int jj[] = new int[num];
        int i = 0;
        while (iter.hasNext()) {

            jj[i] = iter.next().intValue();
            ++i;
        }
        return jj;
    }

    /**
     * 字符串截取
     * 
     * @param str 要处理的字符串
     * @param beginIndex 开始位置
     * @param endIndex 结束位置
     * @param endMark 在结束处加...符
     * @return
     */
    public String substr(String str, int beginIndex, int endIndex, String endMark) {
        if (isBlank(str)) {
            return "";
        }
        if (endIndex == -1) {
            return str.substring(beginIndex);
        }

        if (endIndex > str.length()) {
            endIndex = str.length();
        }
        String restr = str.substring(beginIndex, endIndex);
        if (endIndex < str.length()) {
            restr = restr + "...";
        }
        return restr;
    }

    /**
     * 去掉超链接和
     * <P>
     * </P>
     * ,文章摘要使用
     * 
     * @param str
     * @return
     */
    public static String filtHref(String str) {
        if (str == null) return "";
        String regex = "<[a|A] href=\".*?>(.*?)</[a|A]>";
        Pattern pattern = null;
        pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String ss = matcher.group(1);
            str = str.replaceAll("<[a|A] href=\".*?>" + ss + "</[a|A]>", ss);
        }

        regex = "<[p|P] [^>]*?>(.*?)</[p|P]>";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(str);
        while (matcher.find()) {
            String ss = matcher.group(1);
            str = str.replaceAll("<[p|P] [^>]*?>" + ss + "</[p|P]>", ss);
        }
        return str;
    }

    /**
     * 给超链接加上:target="_blank"
     * 
     * @param str
     * @return
     */
    public static String addHrefBlank(String str) {
        if (str == null) return "";
        String regex = "<[a|A] href=\"([^>]*?)>.*?</[a|A]>";
        Pattern pattern = null;
        pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String ss = matcher.group(1);
            if (ss.indexOf("_blank") == -1) {
                str = str.replaceAll(ss, ss + "  target=\"_blank\"");
            }
        }
        return str;
    }

    // 获取26个字母
    public static char[] getEnChar() {
        char[] cs = new char[26];
        char c = 'A' - 1;
        for (int i = 0; c++ < 'Z'; i++) {
            cs[i] = c;
        }
        return cs;
    }

    // 判断是否在26个字母里面
    public static boolean isInChar(String c) {
        boolean in = false;
        char[] ch = getEnChar();
        for (int i = 0; i < ch.length; i++) {
            if (c.equals(ch[i] + "")) {
                in = true;
                break;
            }
        }
        return in;
    }

    // 转化成大写
    public String toUpperCase(String str) {
        if (isBlank(str)) {
            return "";
        }
        return str.toUpperCase();
    }

    // 转化成大写
    public String toLowerCase(String str) {
        if (isBlank(str)) {
            return "";
        }
        return str.toLowerCase();
    }

    /**
     * 根据大图获得小图地址
     * 
     * @param imgurl
     * @return
     */
    public static String getSmallImg(String imgurl) {
        int len = imgurl.lastIndexOf("/");
        if (len > 1)
            return imgurl.substring(0, len + 1) + "t_" + imgurl.substring(len + 1, imgurl.length());
        else
            return imgurl;
    }

    /**
     * 把字符串切成每个字符
     * 
     * @param str
     * @return
     */
    public static char[] toArray(String str) {
        return str.toCharArray();
    }

    /**
     * b代替a
     * 
     * @param str
     * @param a
     * @param b
     * @return
     */
    public static String replaceStr(String str, String a, String b) {
        if (isBlank(str)) {
            return "";
        }
        return str.replaceAll(a, b);
    }


    /**
     * Description: 基本功能：过滤URL
     * 
     * @param str
     * @return
     */
    public static boolean filterURL(String str, String regexpURL) {
        // logger.info( str + regexpURL );
        Pattern pattern = Pattern.compile(regexpURL);
        Matcher matcher = pattern.matcher(str);
        boolean result = matcher.find();
        return result;
    }

    public static String trim(String str) {
        if (str == null) {
            return str;
        }
        str = str.replaceAll("[\r\n]", "");
        return str.trim();
    }

    /**
     * 字符串截取,区分中文、数字和标点符号
     * 
     * @param str 要处理的字符串
     * @param length 最多需要保留的字符数
     * @param endMark 在结束处加..符
     * @return
     */
    public static String substrByChar(String str, int length, String endMark) {
        if (isBlank(str)) {
            return "";
        }

        if (length >= str.length()) {
            return str;
        }
        int count = 0;
        String[] strArray = str.split("");
        StringBuffer sb = new StringBuffer();
        int maxByteLength = 2 * length - endMark.getBytes().length;
        for (String s : strArray) {
            count += s.getBytes().length;
            if (count < maxByteLength) {
                sb.append(s);
            } else {
                sb.append(endMark);
                break;
            }
        }

        return sb.toString();
    }

    public static boolean startWith(String str, String start) {
        int index = StringUtils.indexOf(str, start, 0);
        return (index == 0);
    }

    public static String getSubByIndex(String str, int start, int len) {
        return StringUtils.substring(str, start, start + len);
    }

    public static int getLength(String str) throws Exception {
        byte[] b = str.getBytes("gbk");
        return b.length;
    }

    /**
     * 将全角字符转为半角字符
     * */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    /**
     * 用于URL编码，使用UTF8编码两次
     * */
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(URLEncoder.encode(str, "utf8"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用于前端展示使用，如果字符串不是数字（整型或浮点型）则显示为“待查”
     * */
    public static String toPageShowNum(String num) {
        if (StringUtils.isEmpty(num)) { // isNumeric会把空白当作numeric
            return "待查";
        } else if (NumberUtils.isNumber(num)) {
            return num;
        } else {
            return "待查";
        }
    }


    public String encodeStr(String str, String charset) {
        try {
            return URLEncoder.encode(str, charset);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 截取一个字符串函数，如果输入的为汉字，则要保证汉字不被截半个
     */
    public static String splitIt(String str, int num, String endstr) {
        if (str == null || "".equals(str)) {
            return "";
        }
        int index = 0;
        StringBuffer sb = new StringBuffer();
        char[] chs = str.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            String c = String.valueOf(chs[i]);
            byte[] bs = c.getBytes();
            int lth = bs.length;

            index += lth;
            if (index > num) {
                break;
            } else {
                sb.append(chs[i]);
            }
        }
        String result = sb.toString();
        if (result.equals(str)) {
            return result;
        }
        return result + endstr;
    }

    /**
     * 对价格进行四舍五入保留指定位数
     * 
     * @param price
     * @param location
     * @return
     */
    public static String formatFloat(String price, int location) {
        String str = "#0";
        // 根据汽车业务需要，百万级车型，只保留整数
        String formatprice = "";
        try {
            double dprice = Double.valueOf(price);
            if (dprice < 100) {
                for (int i = 0; i < location; i++) {
                    if (i == 0) {
                        str += ".0";
                    } else {
                        str += "0";
                    }
                }
            }
            DecimalFormat format = new DecimalFormat(str);
            formatprice = format.format(dprice);
        } catch (NumberFormatException e) {
            return "";
        }
        return formatprice;
    }

    public static long parseLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return defaultValue;
    }

    /**
     * 整数判断
     * 
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (str == null) return false;
        return NumberUtils.isNumber(str.replaceAll(".", ""));
    }

    /**
     * 小数判断
     * 
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        return NumberUtils.isNumber(str);
    }

    /**
     * 手机号验证
     * 
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     * 
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    
    /**
     * Check that the given CharSequence is neither null nor of length 0. StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false StringUtils.hasLength(" ") = true StringUtils.hasLength("Hello") = true
     * 
     * @param str the CharSequence to check (may be <code>null</code>)
     * @return <code>true</code> if the CharSequence is not null and has length
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check that the given CharSequence is neither null nor of length 0. StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false StringUtils.hasLength(" ") = true StringUtils.hasLength("Hello") = true
     * 
     * @param str the CharSequence to check (may be <code>null</code>)
     * @return <code>true</code> if the CharSequence is not null and has length
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }
    
    public static String getRandomVerifyCode(){
	    //取得一个1000-9999的随机数   
    	try {
    		String s="";   
        	int intCount=0;   
    	    intCount=(new Random()).nextInt(9999);//   
    	    if(intCount<1000)intCount+=1000;   
    	    	s=intCount+"";  
    	    return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	    return "2568";
    }
    
	/**
	 * 以","为分隔符连接数组为一个串
	 * 
	 * @param array
	 * @return
	 * @since liuyou @ 2010-4-19
	 */
	public static String join(int[] array, String separator) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}

		StringBuilder buf = new StringBuilder(200);

		if (array.length > 0) {
			buf.append(array[0]);
		}
		for (int i = 1; i < array.length; i++) {
			buf.append(separator);
			buf.append(array[i]);
		}
		return buf.toString();
	}

	/**
	 * 以","为分隔符连接数组为一个串
	 * 
	 * @param array
	 * @return
	 * @since liuyou @ 2010-4-19
	 */
	public static String join(Object[] array) {
		return join(array, ",");
	}

	/**
	 * 指定分隔符用来连接数组为一个串
	 * 
	 * @param array
	 * @param separator
	 * @return
	 * @since liuyou @ 2010-4-19
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * 指定分隔符用来连接数组为一个串
	 * 
	 * @param array
	 * @param separator
	 * @return
	 * @since liuyou @ 2010-4-19
	 */
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * join(null, *)               = null
	 * join([], *)                 = ""
	 * join([null], *)             = ""
	 * join(["a", "b", "c"], ';')  = "a;b;c"
	 * join(["a", "b", "c"], null) = "abc"
	 * join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 *
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use
	 * @param startIndex
	 *            the first index to start joining from. It is an error to pass
	 *            in an end index past the end of the array
	 * @param endIndex
	 *            the index to stop joining from (exclusive). It is an error to
	 *            pass in an end index past the end of the array
	 * @return the joined String, <code>null</code> if null array input
	 */
	static String join(Object[] array, char separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return EMPTY;
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex]
				.toString().length()) + 1);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	static String join(Object[] array, String separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}

		// endIndex - startIndex > 0: Len = NofStrings *(len(firstString) +
		// len(separator))
		// (Assuming that all Strings are roughly equally long)
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return EMPTY;
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex]
				.toString().length()) + separator.length());

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}
	
	/**
	 * 获得安全的URL，避免跨站式攻击 处理方法同
	 * ynj@2008-11-03
	 * 
	 * @return
	 */
	public static String getURLSafe(String url) {
		if (url == null || "".equals(url))
			return "";

		StringBuffer strBuff = new StringBuffer();
		char[] charArray = url.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '<' || charArray[i] == '>')
				continue;

			strBuff.append(charArray[i]);
		}
		return strBuff.toString();
	}
}
