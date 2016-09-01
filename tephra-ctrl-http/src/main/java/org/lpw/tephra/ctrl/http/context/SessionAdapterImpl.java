package org.lpw.tephra.ctrl.http.context;

import org.lpw.tephra.ctrl.context.SessionAdapter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lpw
 */
public class SessionAdapterImpl implements SessionAdapter {
    protected String sessionId;

    public SessionAdapterImpl(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getId() {
        return sessionId;
    }
}
