package cn.gleme.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by janyw on 2017/1/20.
 */
public class StringUtils {
    static final String REG = "[\u4e00-\u9fa5]";

    public static String deleteChar(String str) {
        Pattern pat = Pattern.compile(REG);
        Matcher mat = pat.matcher(str);
        return mat.replaceAll("");
    }

    /**
     * 判斷字符串是否為數字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str.matches("\\d*")) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isEmailFormat(String email) {
        if (email == null) return false;
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(email);
        boolean isMatch = m.matches();
        return isMatch;
    }

    /**
     * 去除字符串中的换行、回车、制表符
     *
     * @param inputValue 输入字符串
     * @return String
     */
    public static String removeEnter(String inputValue) {
        if (StringUtils.isEmpty(inputValue))
            return null;
        //	\n  匹配一个换行符
        //	\r  匹配一个回车符
        //	\t  匹配一个制表符
        Pattern p = Pattern.compile("\t|\r|\n");
        Matcher m = p.matcher(inputValue);
        return m.replaceAll("").trim();
    }

    /**
     * 字符換行符替換 "\\r\\n" replace to "\r\n"
     *
     * @param str
     * @return
     * @author showlike
     * @date 2014-3-26 下午04:20:53
     */
    public static String strNewlineReplace(String str) {
        return str.replace("\\r\\n", "\r\n");
    }

    /**
     * 是否為數字或字母
     *
     * @param str
     * @return 為數字或字母返回true，反則返回false
     * @author showlike
     * @date 2014-6-10 上午10:20:31
     */
    public static boolean isNumbersOrLetters(String str) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher m = p.matcher(str);

        return m.matches();
    }

    /**
     * 判斷用戶是否為郵箱或數字字母
     *
     * @param username
     * @author showlike
     * @return 是：true，否：false
     * @date 2014-6-10 上午10:49:13
     */
    public static boolean isEmailOrNumbersLetters(String username) {
        if (username.contains("@") && username.contains(".")) {
            return isEmailFormat(username);
        } else {
            return isNumbersOrLetters(username);
        }
    }

    /**
     * 日誌記錄
     *
     * @param logStrings 字符數組
     * @return
     * @author showlike
     * @date 2014-6-20 下午05:49:27
     */
    public static String logRecord(int length, String... logStrings) {
        if (logStrings == null || logStrings.length == 0) return "";

        StringBuilder sb = new StringBuilder(length);
        for (String string : logStrings) {
            sb.append(string).append(",");
        }

        return sb.toString();
    }

    /**
     * 判斷字符串是否為空
     *
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object... obj) {
        return !isEmpty(obj);
    }

    public static boolean isEmpty(Object... o) {
        // 判断是否为空
        if (o == null)
            return true;
        for (Object obj : o) {
            if (obj == null)
                return true;
            // ----------------根据各种对象类型判断是否值为空--------------
            if (obj instanceof String) {
                if (((String) obj).trim().equals("") || "null".equalsIgnoreCase(obj.toString()) || "[]".equalsIgnoreCase(obj.toString()))
                    return true;
            } else if (obj instanceof Collection) {
                Collection coll = (Collection) obj;
                if (coll.size() == 0)
                    return true;
            } else if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.size() == 0)
                    return true;
            } else if (obj.getClass().isArray()) {
                if (Array.getLength(obj) == 0)
                    return true;
            } else if (obj instanceof Integer) {
                Integer i = (Integer) obj;
                if (i == 0)
                    return true;
            } else if (obj instanceof String[]) {
                String[] i = (String[]) obj;
                if (i.length == 0) return true;
            }
        }
        return false;
    }
    /**
     * Json串某個值
     *
     * @param obj
     * @return
     */
//	public static boolean isEmpty(JSONObject json, String... obj) {
//		if (null == json || isEmpty(obj)) {
//			return true;
//		}
//		for(String o : obj){
//			if(!json.has(o) || json.isNull(o)) return true;
//		}
//		return false;
//	}
//	public static boolean isNotEmpty(JSONObject json, String... obj) {
//		return !isEmpty(json, obj);
//	}

    /**
     * 判斷字符串是否相等，只要有一个相等，就返回true
     *
     * @param base
     * @return
     */
    public static boolean equalsIgnoreCase(String base, String... obj) {
        if (null == obj) return false;

        if (obj.length > 0) {
            for (String o : obj) {
                if (null == o || "".equals(o)) continue;
                if (o.equalsIgnoreCase(base)) return true;
            }
            return false;
        } else {
            return false;
        }

    }

    /**
     * 判斷字符串是否相等，只要有一个相等，就返回true
     *
     * @param base
     * @return
     */
    public static boolean equals(String base, String... obj) {
        if(null == obj) return false;

        if (obj.length > 0) {
            for(String o : obj){
                if(null == o || "".equals(o)) continue;
                if(o.equals(base)) return true;
            }
            return false;
        } else {
            return false;
        }

    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (isEmpty(s)) {
            return s;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        str = sb.toString();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        return str;
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 驼峰转下划线,效率比上面高
     *
     * @param str
     * @return
     */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * object转String
     *
     * @param object
     * @return
     */
    public static String getString(Object object) {
        return getString(object, "");
    }

    public static String getString(Object object, String defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return object.toString();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Integer
     *
     * @param object
     * @return
     */
    public static int getInt(Object object) {
        return getInt(object, -1);
    }

    public static int getInt(Object object, Integer defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Boolean
     *
     * @param object
     * @return
     */
    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }

    public static boolean getBoolean(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isLocalPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isLocalPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //随机产生的6位短信验证码
    public static String getRandomSMSCode() {
        //发出随机产生的验证码短信
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < 6) {
            int t = random.nextInt(9);
            stringBuffer.append(t);
            i++;
        }
        return stringBuffer.toString();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static int[] StringToInt(String[] arrs) {
        int[] ints = new int[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            ints[i] = Integer.parseInt(arrs[i]);
        }
        return ints;
    }

    public static String composeMessage (String template, Map < String, Object > data){
        String regex = "\\@\\{(.+?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(template);
        /*
         * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序
         * 存储起来。
         */
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String name = matcher.group(1);//键名
            String value = String.valueOf(data.get(name));//键值
            if (value == null) {
                value = "";
            } else {
                /*
                 * 由于$出现在replacement中时，表示对捕获组的反向引用，所以要对上面替换内容
                 * 中的 $ 进行替换，让它们变成 "\$1000.00" 或 "\$1000000000.00" ，这样
                 * 在下面使用 matcher.appendReplacement(sb, value) 进行替换时就不会把
                 * $1 看成是对组的反向引用了，否则会使用子匹配项值amount 或 balance替换 $1
                 * ，最后会得到错误结果：
                 *
                 * 尊敬的客户刘明你好！本次消费金额amount000.00，您帐户888888888上的余额
                 * 为balance000000.00，欢迎下次光临！
                 *
                 * 要把 $ 替换成 \$ ，则要使用 \\\\\\& 来替换，因为一个 \ 要使用 \\\ 来进
                 * 行替换，而一个 $ 要使用 \\$ 来进行替换，因 \ 与  $ 在作为替换内容时都属于
                 * 特殊字符：$ 字符表示反向引用组，而 \ 字符又是用来转义 $ 字符的。
                 */
                value = value.replaceAll("\\@", "\\\\\\@");
                //System.out.println("value=" + value);
            }
            /*
             * 经过上面的替换操作，现在的 value 中含有 $ 特殊字符的内容被换成了"\$1000.00"
             * 或 "\$1000000000.00" 了，最后得到下正确的结果：
             *
             * 尊敬的客户刘明你好！本次消费金额$1000.00，您帐户888888888上的
             * 余额为$1000000.00，欢迎下次光临！
             *
             * 另外，我们在这里使用Matcher对象的appendReplacement()方法来进行替换操作，而
             * 不是使用String对象的replaceAll()或replaceFirst()方法来进行替换操作，因为
             * 它们都能只能进行一次性简单的替换操作，而且只能替换成一样的内容，而这里则是要求每
             * 一个匹配式的替换值都不同，所以就只能在循环里使用appendReplacement方式来进行逐
             * 个替换了。
             */
            matcher.appendReplacement(sb, value);
            System.out.println("sb = " + sb.toString());
        }
        //最后还得要把尾串接到已替换的内容后面去，这里尾串为“，欢迎下次光临！”
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * Javascript: unterminated string literal 解决方法
     * @Description: TODO
     * @author LiXiang
     * @date 2013-11-20 上午10:02:19
     * @param s
     * @return
     * @throws
     */
    public static final String htmlToCode(String s) {
        if (s == null) {
            return "";
        } else {
            s = s.replace("\n\r", "\\n");
            s = s.replace("\n", "\\n");
            s = s.replace("\r\n", "\\n");
            s = s.replace("\t", "\\n");
            s = s.replace(" ", "&nbsp;");

            s = s.replace("\"", "\\" + "\"");// 如果原文含有双引号，这一句最关键！！！！！！
            return s;
        }
    }
    /**
     * 对象转数组
     * @param obj
     * @return
     */
    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
    /**
     * 数组转对象
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
    public static final InputStream byte2Input(byte[] buf) {
      return new ByteArrayInputStream(buf);
   }

    public static final byte[] input2byte(InputStream inStream) throws IOException {
       ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
       byte[] buff = new byte[100];
       int rc = 0;
       while ((rc = inStream.read(buff, 0, 100)) > 0) {
               swapStream.write(buff, 0, rc);
           }
       byte[] in2b = swapStream.toByteArray();
       return in2b;
   }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            } else {
            }
        }
        if (buf == null) {
            return "极速名车用户";//如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }

    private static boolean containsEmoji(String source) {
        if (org.apache.commons.lang.StringUtils.isBlank(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public static String filterUtf8mb4(String str) {
        final int LAST_BMP = 0xFFFF;
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            int codePoint = str.codePointAt(i);
            if (codePoint < LAST_BMP) {
                sb.appendCodePoint(codePoint);
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    public static String joinKeyValue(Map<String, Object> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
        List<String> list = new ArrayList<String>();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = ConvertUtils.convert(entry.getValue());
                if (org.apache.commons.lang.StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue || org.apache.commons.lang.StringUtils.isNotEmpty(value))) {
                    list.add(key + "=" + (value != null ? value : ""));
                }
            }
        }
        return (prefix != null ? prefix : "") + org.apache.commons.lang.StringUtils.join(list, separator) + (suffix != null ? suffix : "");
    }

    public static BigDecimal eval(String formulary) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            Object result1 = engine.eval(formulary);
            System.out.println("结果类型:" + result1.getClass().getName() + ",计算结果:" + result1);
            return new BigDecimal(result1.toString());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取汉字串拼音首字母，英文字符不变
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        if(org.apache.commons.lang3.StringUtils.isBlank(chinese)){
            return null;
        }
        //取首汉字
        chinese = chinese.substring(0,1);
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim().toUpperCase();
    }

    public static int compareVersion(String str1,String str2){
    	String[] split1 = str1.split("\\."); 
        String[] split2 = str2.split("\\.");
    	int length=Math.min(split1.length, split2.length);
        int result = 0;
        for(int i=0;i<length;i++){
            if(split1[i].compareTo(split2[i])>0){
                result = 1;
                break;
            }else if(split1[i].compareTo(split2[i])<0){
                result = -1;
                break;
            }else if(i==length-1){
                result = 0;
                break;
            }
        }
		return result;
    }

    public static void main(String[] args) throws ScriptException {
//        ScriptEngineManager manager = new ScriptEngineManager();
////        ScriptEngine engine = manager.getEngineByName("js");
////        /*
////         * 字符串转算术表达式
////         */
////        String str1 = "43*(2 + 1.4)+2*32/(3-2.1)";
////        Object result1 = engine.eval(str1);
////        System.out.println("结果类型:" + result1.getClass().getName() + ",计算结果:" + result1);
////        /*
////         * 字符串转条件表达式
////         */
////        int value = 7;
////        String state = "正常";
////        boolean flag = true;
////        String st = "test";
////        String str2 = "value > 5 && st == \"test\" && state == \"正常\" && flag == true";
////        engine.put("value", value);
////        engine.put("state", state);
////        engine.put("flag", flag);
////        engine.put("st", st);
////        Object result2 = engine.eval(str2);
////        System.out.println("结果类型:" + result2.getClass().getName() + ",结果:" + result2);

        System.out.println(compareVersion("0.10.10","1.1.1"));
//        System.out.println(compareVersion("1.1.2","1.1.1"));
    }
}
