package org.lpw.tephra.util;

/**
 * 验证器。用于提供常用验证。
 *
 * @author lpw
 */
public interface Validator {
    /**
     * 判断对象是否为空。如果要判断的实例类型为字符串则去除前后空格后验证长度是否为0；如果要判断的实例类型为集合则判断是否为空集；
     * 其他类型则判断是否为null。
     *
     * @param object 要验证的对象。
     * @return 如果为空则返回true；否则返回false。
     */
    boolean isEmpty(Object object);

    /**
     * 验证Email地址是否合法。
     *
     * @param email 要进行验证的Email地址。
     * @return 如果为合法Email地址则返回true；否则返回false。
     */
    boolean isEmail(String email);

    /**
     * 验证字符串是否符合指定正则表达式所定义的规则。
     *
     * @param regex  目标正则表达式。
     * @param string 要进行验证的字符串。
     * @return 如果符合则返回true；否则返回false。
     */
    boolean isMatchRegex(String regex, String string);

    /**
     * 判断文件是否为图片。仅支持JPEG、PNG、GIF等格式验证。
     *
     * @param contentType 文件类型。
     * @param name        文件名。
     * @return 如果是图片则返回true；否则返回false。
     */
    boolean isImage(String contentType, String name);
}
