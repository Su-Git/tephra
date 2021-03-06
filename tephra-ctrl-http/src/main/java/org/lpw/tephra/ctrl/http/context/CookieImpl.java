package org.lpw.tephra.ctrl.http.context;

import org.lpw.tephra.util.Converter;
import org.lpw.tephra.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lpw
 */
@Controller("tephra.ctrl.http.cookie")
public class CookieImpl implements org.lpw.tephra.ctrl.http.context.Cookie, CookieAware {
    protected ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
    protected ThreadLocal<HttpServletResponse> response = new ThreadLocal<>();

    @Autowired
    protected Validator validator;
    @Autowired
    protected Converter converter;

    @Override
    public void add(String name, String value, String path, int expiry) {
        Cookie cookie = new Cookie(name, value);
        if (!validator.isEmpty(path))
            cookie.setPath(path);
        if (expiry > 0)
            cookie.setMaxAge(expiry);
        response.get().addCookie(cookie);
    }

    @Override
    public String get(String name) {
        String[] array = getAll(name);

        return array.length == 0 ? null : array[array.length - 1];
    }

    @Override
    public String[] getAll(String name) {
        String[] array = new String[0];
        Cookie[] cookies = request.get().getCookies();
        if (validator.isEmpty(cookies))
            return array;

        List<String> list = new ArrayList<>();
        for (Cookie cookie : cookies)
            if (cookie.getName().equals(name))
                list.add(cookie.getValue());

        return list.toArray(array);
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
    public Date getAsDate(String name) {
        return converter.toDate(get(name));
    }

    @Override
    public void set(HttpServletRequest request, HttpServletResponse response) {
        this.request.set(request);
        this.response.set(response);
    }
}
