package org.lpw.tephra.ctrl.http;

import org.lpw.tephra.util.Logger;
import org.lpw.tephra.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @author lpw
 */
@Controller("tephra.ctrl.http.security-helper")
public class SecurityHelperImpl implements SecurityHelper {
    private static final Pattern SCRIPT = Pattern.compile("<\\s*[sS]\\s*[cC]\\s*[rR]\\s*[iI]\\s*[pP]\\s*[tT].*>");

    @Autowired
    protected Validator validator;
    @Autowired
    protected Logger logger;
    @Value("${tephra.ctrl.http.security.jsp.enable:false}")
    protected boolean enable;
    @Value("${tephra.ctrl.http.security.xss:true}")
    protected boolean xss;
    @Value("${tephra.ctrl.http.security.xss.ignore:}")
    protected String ignore;

    @Override
    public boolean isEnable(HttpServletRequest request) {
        String uri = request.getRequestURI().toLowerCase();
        if (!enable && suffix(uri)) {
            logger.warn(null, "疑似JSP请求[{}]，拒绝访问！", uri);

            return false;
        }

        if (!xss || ignore.contains(uri))
            return true;

        for (String key : request.getParameterMap().keySet()) {
            String value = request.getParameter(key);
            if (value == null)
                continue;

            if (value.indexOf('<') < value.lastIndexOf('>') && SCRIPT.matcher(value).find()) {
                logger.warn(null, "疑似提交跨站脚本参数[{}]，拒绝请求[{}]！", value, uri);

                return false;
            }
        }

        return true;
    }

    protected boolean suffix(String uri) {
        int indexOf = uri.lastIndexOf('.');
        if (indexOf == -1)
            return false;

        String suffix = uri.substring(uri.lastIndexOf('.'));

        return suffix.contains("jsp");
    }
}
