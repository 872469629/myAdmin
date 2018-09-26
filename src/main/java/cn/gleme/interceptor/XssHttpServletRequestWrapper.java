package cn.gleme.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
        }
        return value;
    }
    @Override
    public String[] getParameterValues(String name) {
        String[] value = super.getParameterValues(name);
        if(value != null){
            for (int i = 0; i < value.length; i++) {
                value[i] = xssEncode(value[i]);
            }
        }
        return value;
    }
    @Override
    public Map getParameterMap() {
        // TODO Auto-generated method stub
        return super.getParameterMap();
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
     * getHeaderNames 也可能需要覆盖
     * 这一段代码在一开始没有注释掉导致出现406错误，原因是406错误是HTTP协议状态码的一种，
     * 表示无法使用请求的内容特性来响应请求的网页。一般是指客户端浏览器不接受所请求页面的 MIME 类型。
     *
     @Override
     public String getHeader(String name) {

     String value = super.getHeader(xssEncode(name));
     if (value != null) {
     value = xssEncode(value);
     }
     return value;
     }
     **/


    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符 在保证不删除数据的情况下保存
     * @param value
     * @return 过滤后的值
     */
    private static String xssEncode(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
//        value = value.replaceAll("eval\\((.*)\\)", "");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
//        value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
//        value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
//        value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
//        value = value.replaceAll("'", "‘");// 单引号
//        value = value.replaceAll("--", "－－");// 注释符
//        value = value.replaceAll("\"", "“");// 双引号
//        value = value.replaceAll("<", "＜");// 小于号
//        value = value.replaceAll(">", "＞");// 大于号
//        value = value.replaceAll("%", "％");// 百分比
//        value = value.replaceAll(";", "；");// 分号
////        value = value.replaceAll("&", "＆");// 地址符
//        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "&#41;");//括号
//        value = value.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");   //换行回车
//        value = value.replaceAll("eval\\((.*)\\)", "");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("script", "");
//        value = value.replaceAll("alert", "");
        return value;
    }
}