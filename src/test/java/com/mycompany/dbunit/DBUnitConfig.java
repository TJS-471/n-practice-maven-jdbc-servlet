package com.mycompany.dbunit;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

import java.util.Properties;


public class DBUnitConfig extends DBTestCase {
	private static final String DRIVER_CLASS = "org.h2.Driver";
	private static final String CONNECTION_URL = "jdbc:h2:mem:D:/n-practice-maven-jdbc-servlet/testdb-unit;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:create-db.sql'";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123";
	
	protected IDatabaseTester tester;
	protected Properties prop;
	protected IDataSet beforeData;

	@Before
	protected void setUp() throws Exception {
//		tester = new JdbcDatabaseTester(prop.getProperty("driverClassName"), prop.getProperty("url"),
//				prop.getProperty("username"), prop.getProperty("password"));
		tester = new JdbcDatabaseTester(DRIVER_CLASS, CONNECTION_URL, USERNAME, PASSWORD);
	}

	public DBUnitConfig(String name) {
		super(name);
//		prop = new Properties();
//		try {
//			prop.load(new FileInputStream("application.properties"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, prop.getProperty("driverClassName"));
//		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, prop.getProperty("url"));
//		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, prop.getProperty("username"));
//		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, prop.getProperty("password"));
//		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "");
		
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DRIVER_CLASS);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, CONNECTION_URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USERNAME);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
//		System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
	}
//    public DBUnitConfig(){}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return beforeData;
	}

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }
}
