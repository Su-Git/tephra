package org.lpw.tephra.dao.orm.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lpw.tephra.bean.BeanFactory;
import org.lpw.tephra.dao.DaoUtil;
import org.lpw.tephra.dao.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lpw
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:**/spring.xml"})
public class SessionTest {
    @Autowired
    protected SessionFactory sessionFactory;
    @Autowired
    protected Session session;

    @Test
    public void transactional() {
        BeanFactory.getBean(TransactionalImpl.class).get();
    }

    @Test
    public void beginTransaction() {
        session.beginTransaction();
        SqlSession session1 = session.get(null, Mode.Write);
        Assert.assertNotNull(session1);
        SqlSession session2 = session.get(null, Mode.Read);
        Assert.assertEquals(session1.hashCode(), session2.hashCode());

        DaoUtil.createSessionFactory("HasReadonlyBeginTransaction", sessionFactory);
        SqlSession session3 = session.get("HasReadonlyBeginTransaction", Mode.Write);
        Assert.assertNotNull(session3);
        SqlSession session4 = session.get("HasReadonlyBeginTransaction", Mode.Read);
        Assert.assertEquals(session3.hashCode(), session4.hashCode());
        session.close();
    }

    @Test
    public void get() {
        SqlSession session1 = session.get(null, Mode.Write);
        Assert.assertNotNull(session1);
        SqlSession session2 = session.get(null, Mode.Read);
        Assert.assertEquals(session1.hashCode(), session2.hashCode());

        DaoUtil.createSessionFactory("HasReadonlyGet", sessionFactory);
        SqlSession session3 = session.get("HasReadonlyGet", Mode.Write);
        Assert.assertNotNull(session3);
        SqlSession session4 = session.get("HasReadonlyGet", Mode.Read);
        Assert.assertNotEquals(session3.hashCode(), session4.hashCode());
        session.close();
    }
}
