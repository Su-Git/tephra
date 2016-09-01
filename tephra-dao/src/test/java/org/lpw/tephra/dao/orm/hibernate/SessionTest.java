package org.lpw.tephra.dao.orm.hibernate;

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
        org.hibernate.Session session1 = session.get(null, Mode.Write);
        Assert.assertNotNull(session1);
        org.hibernate.Session session2 = session.get(null, Mode.Read);
        Assert.assertEquals(session1.hashCode(), session2.hashCode());

        DaoUtil.createSessionFactory("HasReadonlyBeginTransaction", sessionFactory);
        org.hibernate.Session session3 = session.get("HasReadonlyBeginTransaction", Mode.Write);
        Assert.assertNotNull(session3);
        org.hibernate.Session session4 = session.get("HasReadonlyBeginTransaction", Mode.Read);
        Assert.assertEquals(session3.hashCode(), session4.hashCode());
        session.close();
    }

    @Test
    public void get() {
        org.hibernate.Session session1 = session.get(null, Mode.Write);
        Assert.assertNotNull(session1);
        org.hibernate.Session session2 = session.get(null, Mode.Read);
        Assert.assertEquals(session1.hashCode(), session2.hashCode());

        DaoUtil.createSessionFactory("HasReadonlyGet", sessionFactory);
        org.hibernate.Session session3 = session.get("HasReadonlyGet", Mode.Write);
        Assert.assertNotNull(session3);
        org.hibernate.Session session4 = session.get("HasReadonlyGet", Mode.Read);
        Assert.assertNotEquals(session3.hashCode(), session4.hashCode());
        session.close();
    }
}
