package org.lpw.tephra.ctrl.console;

import net.sf.json.JSONObject;

/**
 * 控制台。
 *
 * @author lpw
 */
public interface Console {
    /**
     * 验证是否执行控制服务。
     *
     * @param uri 服务URI地址。
     * @return 如果是执行控制服务则返回true；否则返回false。
     */
    boolean isConsole(String uri);

    /**
     * 执行控制功能。
     *
     * @return 执行结果。
     */
    JSONObject execute();
}
