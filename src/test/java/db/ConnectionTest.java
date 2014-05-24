package db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Test;

public class ConnectionTest {
	
	@AfterClass
	public static void staticCloseDBConnection() throws SQLException {
		ConnectionHelper.closeConnection();
	}
	
	@Test
	public void getConnectionTest1() throws IOException, SQLException {
		Connection conn = ConnectionHelper.getConnection();
		assertNotNull(conn);
	}
	
	@Test
	public void getConnectionTest2() throws IOException, SQLException {
		Connection conn = ConnectionHelper.getConnection();
		assertNotNull(conn);
		conn = ConnectionHelper.getConnection();
		assertNotNull(conn);
	}
	
	@Test
	public void getConnectionTest3() throws IOException, SQLException {
		Connection conn1 = ConnectionHelper.getConnection();
		Connection conn2 = ConnectionHelper.getConnection();
		assertSame(conn1, conn2);
	}
	
}
