package org.lpw.tephra.freemarker;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;
import org.lpw.tephra.bean.BeanFactory;
import org.lpw.tephra.util.Context;
import org.lpw.tephra.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author lpw
 */
@Component("tephra.freemarker")
public class FreemarkerImpl implements Freemarker {
    @Autowired
    protected Context context;
    @Autowired
    protected Logger logger;
    @Value("${tephra.freemarker.root:/WEB-INF/ftl}")
    protected String root;
    @Value("${tephra.freemarker.suffix:.ftl}")
    protected String suffix;
    protected Configuration configuration;

    @Override
    public String process(String name, Object data) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            process(name, data, output);
            output.close();

            return output.toString();
        } catch (IOException e) {
            logger.warn(e, "解析模版[{}]时发生异常！", name);

            return null;
        }
    }

    @Override
    public void process(String name, Object data, OutputStream output) {
        try {
            getConfiguration().getTemplate(name + suffix).process(BeanFactory.getBean(Model.class).setData(data), new OutputStreamWriter(output));
        } catch (Exception e) {
            logger.warn(e, "解析模版[{}]时发生异常！", name);
        }
    }

    protected synchronized Configuration getConfiguration() throws IOException {
        if (configuration == null) {
            configuration = new Configuration(Configuration.VERSION_2_3_25);
            configuration.setDirectoryForTemplateLoading(new File(context.getAbsolutePath(root)));
            configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_25));
            configuration.setTemplateExceptionHandler((TemplateException te, Environment env, Writer out) -> logger.warn(te, "解析FreeMarker模板时发生异常！"));
        }

        return configuration;
    }
}
