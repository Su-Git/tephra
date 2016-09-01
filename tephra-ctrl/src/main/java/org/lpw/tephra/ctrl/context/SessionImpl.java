package org.lpw.tephra.ctrl.context;

import org.lpw.tephra.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller("tephra.ctrl.context.session")
public class SessionImpl implements Session, SessionAware {
    private static final String CACHE = "tephra.ctrl.context.session:";

    @Autowired
    protected Cache cache;
    protected ThreadLocal<SessionAdapter> adapter = new ThreadLocal<>();

    @Override
    public void set(String key, Object value) {
        set(getId(), key, value);
    }

    @Override
    public void set(String id, String key, Object value) {
        cache.put(getKey(id, key), value, false);
    }

    @Override
    public <T> T get(String key) {
        return get(getId(), key);
    }

    @Override
    public <T> T get(String id, String key) {
        return cache.get(getKey(id, key));
    }

    @Override
    public void remove(String key) {
        remove(getId(), key);
    }

    @Override
    public void remove(String id, String key) {
        cache.remove(getKey(id, key));
    }

    protected String getKey(String id, String key) {
        return new StringBuilder().append(CACHE).append(id).append('-').append(key).toString();
    }

    @Override
    public String getId() {
        return adapter.get().getId();
    }

    @Override
    public void set(SessionAdapter adapter) {
        this.adapter.set(adapter);
    }
}
