package org.lpw.tephra.cache.lr;

import org.lpw.tephra.nio.Client;
import org.lpw.tephra.nio.ClientListener;
import org.lpw.tephra.nio.ClientManager;
import org.lpw.tephra.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author lpw
 */
@Component("tephra.cache.lr.channel")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ChannelImpl implements Channel, ClientListener {
    @Autowired
    protected Logger logger;
    @Autowired
    protected ClientManager clientManager;
    @Autowired
    protected Remote remote;
    @Value("${tephra.cache.listen-port:0}")
    protected int port;
    protected Client client;
    protected String ip;
    protected State state;
    protected String sessionId;

    public ChannelImpl() {
        state = State.Disconnect;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public void connect() {
        if (port < 1)
            return;

        if (client == null)
            client = clientManager.get();
        state = State.Disconnect;
        client.connect(this, ip, port);
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void connect(String sessionId) {
        this.sessionId = sessionId;
        state = State.Connected;
    }

    @Override
    public void receive(String sessionId, byte[] message) {
        this.sessionId = sessionId;
        if (Arrays.equals(remote.getId().getBytes(), message)) {
            state = State.Self;
            client.close();
        }
    }

    @Override
    public void disconnect() {
        if (state == State.Connected)
            state = State.Disconnect;
    }
}
