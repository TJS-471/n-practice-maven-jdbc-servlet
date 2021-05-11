package com.mycompany.dao;


import org.apache.commons.dbcp.BasicDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * An abstract class that reads properties from a property file and creates a {@link Connection} with a db.
 * @author Julia Tsukanova
 * @version 1.0
 */
public abstract class AbstractjdbcDao {
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public Connection getConnection() throws IOException, SQLException {
		return createConnection();
	}

	protected PreparedStatement getPreparedStatement(String sql) throws IOException, SQLException {
		connection = getConnection();
		connection.setAutoCommit(false);
		return connection.prepareStatement(sql);
	}

	public void closeResources() throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
	private Connection createConnection() throws IOException, SQLException {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(propertyParser("driverClassName"));
		dataSource.setUrl(propertyParser("url"));
		dataSource.setUsername(propertyParser("username"));
		dataSource.setPassword(propertyParser("password"));
		dataSource.setInitialSize(Integer.parseInt(propertyParser("initialSize")));
		dataSource.setMaxActive(Integer.parseInt(propertyParser("maxActive")));
		dataSource.setMaxIdle(Integer.parseInt(propertyParser("maxIdle")));
		dataSource.setMinIdle(Integer.parseInt(propertyParser("minIdle")));
		dataSource.setMaxWait(Long.parseLong(propertyParser("maxWait")));

		Connection conn = dataSource.getConnection();
		System.out.println(conn);
		return conn;
	}

	private String propertyParser(String property) throws IOException {
		String propFileName = "D:\\n-practice-maven-jdbc-servlet\\src\\config\\application.properties";
		Properties prop = readPropertiesFile(propFileName);
		return prop.getProperty(property);
	}

	private Properties readPropertiesFile(String fileName) throws IOException {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
		    if(fis != null) {
                fis.close();
            }
		}
		return prop;
	}

	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
