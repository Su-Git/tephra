package org.lpw.tephra.ctrl.context;

import org.lpw.tephra.util.Converter;
import org.lpw.tephra.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author lpw
 */
@Controller("tephra.ctrl.context.header")
public class HeaderImpl implements Header, HeaderAware {
    @Autowired
    protected Validator validator;
    @Autowired
    protected Converter converter;
    @Value("${tephra.ctrl.context.header.real-ip:}")
    protected String realIp;
    protected ThreadLocal<HeaderAdapter> adapter = new ThreadLocal<>();

    @Override
    public String get(String name) {
        return adapter.get().get(name);
    }

    @Override
    public int getAsInt(String name) {
        return converter.toInt(get(name));
    }

    @Override
    public long getAsLong(String name) {
        return converter.toLong(get(name));
    }

    @Override
    public String getIp() {
        return validator.isEmpty(realIp) ? adapter.get().getIp() : get(realIp);
    }

    @Override
    public Map<String, String> getMap() {
        return adapter.get().getMap();
    }

    @Override
    public void set(HeaderAdapter adapter) {
        this.adapter.set(adapter);
    }
}
