package org.lpw.tephra.ctrl.template;

/**
 * 模板支持。
 *
 * @author lpw
 */
public interface TemplateHelper {
    /**
     * 获取当前请求模板文件名。
     *
     * @return 当前请求模板文件名。
     */
    String getTemplate();

    /**
     * 设置当前请求模板文件名。
     *
     * @param template 当前请求模板文件名。
     */
    void setTemplate(String template);
}
