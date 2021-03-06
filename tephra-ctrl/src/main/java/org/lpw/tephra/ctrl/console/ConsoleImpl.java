package org.lpw.tephra.ctrl.console;

import net.sf.json.JSONObject;
import org.lpw.tephra.bean.BeanFactory;
import org.lpw.tephra.bean.ContextRefreshedListener;
import org.lpw.tephra.ctrl.context.Header;
import org.lpw.tephra.ctrl.context.Request;
import org.lpw.tephra.util.Converter;
import org.lpw.tephra.util.Logger;
import org.lpw.tephra.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lpw
 */
@Service("tephra.ctrl.console")
public class ConsoleImpl implements Console, ContextRefreshedListener {
    @Autowired
    protected Validator validator;
    @Autowired
    protected Converter converter;
    @Autowired
    protected Logger logger;
    @Autowired
    protected Header header;
    @Autowired
    protected Request request;
    @Value("${tephra.ctrl.console.uri:/tephra/ctrl/console}")
    protected String uri;
    @Value("${tephra.ctrl.console.allow-ips:}")
    protected String allowIps;
    protected boolean enable;
    protected Set<String> allowIpSet;

    @Override
    public boolean isConsole(String uri) {
        return enable && this.uri.equals(uri);
    }

    @Override
    public JSONObject execute() {
        if (!isAllowIp())
            return json(9901, null);

        if (!request.checkSign())
            return json(9902, null);

        String beanName = request.get("beanName");
        if (validator.isEmpty(beanName))
            return json(9903, null);

        String fieldName = request.get("fieldName");
        String methodName = request.get("methodName");
        if (validator.isEmpty(methodName) && validator.isEmpty(fieldName))
            return json(9904, null);

        Object bean = BeanFactory.getBean(beanName);
        if (bean == null)
            return json(9905, null);

        List<Class<?>> classes = new ArrayList<>();
        List<Object> args = new ArrayList<>();
        parseArgs(classes, args);

        try {
            return validator.isEmpty(methodName) ? field(bean, fieldName, args) : method(bean, methodName, classes, args);
        } catch (Exception e) {
            logger.warn(e, "执行控制器时发生异常！");

            return json(9999, null);
        }
    }

    protected boolean isAllowIp() {
        if (allowIpSet == null) {
            allowIpSet = new HashSet<>();
            for (String ip : converter.toArray(allowIps, ","))
                allowIpSet.add(ip);
        }

        if (allowIpSet.isEmpty())
            return false;

        return allowIpSet.contains("*") || allowIpSet.contains(header.getIp());
    }

    protected void parseArgs(List<Class<?>> classes, List<Object> args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String arg = request.get("arg" + i);
            if (validator.isEmpty(arg))
                break;

            int indexOf = arg.indexOf(':');
            if (indexOf == -1)
                continue;

            String type = arg.substring(0, indexOf);
            String value = arg.substring(indexOf + 1);
            if (value.equals("null"))
                value = null;
            if (type.equals("string")) {
                classes.add(String.class);
                args.add(value);

                continue;
            }

            if (type.equals("int")) {
                classes.add(int.class);
                args.add(converter.toInt(value));

                continue;
            }

            if (type.equals("long")) {
                classes.add(long.class);
                args.add(converter.toLong(value));

                continue;
            }

            if (type.equals("boolean")) {
                classes.add(boolean.class);
                args.add(Boolean.parseBoolean(value));

                continue;
            }

            if (type.equals("json")) {
                classes.add(JSONObject.class);
                args.add(JSONObject.fromObject(value));

                continue;
            }
        }
    }

    protected JSONObject field(Object bean, String fieldName, List<Object> args) throws Exception {
        Field field = bean.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        if (args.isEmpty())
            return json(0, field.get(bean));

        field.set(bean, args.get(0));

        return json(0, null);
    }

    protected JSONObject method(Object bean, String methodName, List<Class<?>> classes, List<Object> args) throws Exception {
        Method method = bean.getClass().getDeclaredMethod(methodName, classes.toArray(new Class<?>[0]));
        method.setAccessible(true);

        return json(0, method.invoke(bean, args.toArray()));
    }

    protected JSONObject json(int code, Object result) {
        JSONObject json = new JSONObject();
        json.accumulate("code", code);
        if (result != null)
            json.accumulate("result", result);

        return json;
    }

    @Override
    public int getContextRefreshedSort() {
        return 7;
    }

    @Override
    public void onContextRefreshed() {
        enable = !validator.isEmpty(uri);
        if (logger.isInfoEnable())
            logger.info("设置控制台启动状态：{}", enable);
    }
}
