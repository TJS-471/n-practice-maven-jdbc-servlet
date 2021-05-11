package com.mycompany.dbunit;/*
 * Created by Julie on 01.03.2021 */


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({RoleDaoCreateTest.class,
        RoleDaoUpdateTest.class,
        RoleDaoFindByNameTest.class,
        RoleDaoRemoveTest.class,
        UserDaoCreateTest.class,
        UserDaoUpdateTest.class,
        UserDaoFindAllTest.class,
        UserDaoFindByEmailTest.class,
        UserDaoFindByLoginTest.class,
        UserDaoRemoveTest.class
})
public class DBUnitTestSuite {
}
