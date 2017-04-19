package com.zhao.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.zhao.greendao.entity.SysData;

import com.zhao.greendao.gen.SysDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig sysDataDaoConfig;

    private final SysDataDao sysDataDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        sysDataDaoConfig = daoConfigMap.get(SysDataDao.class).clone();
        sysDataDaoConfig.initIdentityScope(type);

        sysDataDao = new SysDataDao(sysDataDaoConfig, this);

        registerDao(SysData.class, sysDataDao);
    }
    
    public void clear() {
        sysDataDaoConfig.clearIdentityScope();
    }

    public SysDataDao getSysDataDao() {
        return sysDataDao;
    }

}
