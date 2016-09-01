package org.lpw.tephra.ctrl.execute;

import org.lpw.tephra.ctrl.template.Template;
import org.lpw.tephra.ctrl.validate.Validate;

import java.lang.reflect.Method;

/**
 * @author lpw
 */
public class ExecutorImpl implements Executor {
    private final Object bean;
    private final Method method;
    private final Validate[] validates;
    private final Template template;
    private final String view;

    public ExecutorImpl(Object bean, Method method, Validate[] validates, Template template, String view) {
        this.bean = bean;
        this.method = method;
        this.validates = validates;
        this.template = template;
        this.view = view;
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Validate[] getValidates() {
        return validates;
    }

    @Override
    public Template getTemplate() {
        return template;
    }

    @Override
    public String getView() {
        return view;
    }
}
