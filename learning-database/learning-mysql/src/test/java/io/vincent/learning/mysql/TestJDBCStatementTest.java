package io.vincent.learning.mysql;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 2020/6/3.
 *
 * @author Vincent
 * @since 1.0, 2020/6/3
 */
public class TestJDBCStatementTest {

	@Test
	public void testDDL() {
		TestJDBCStatement.testDDL();
	}

	@Test
	public void testInsert() {
		TestJDBCStatement.testInsert();
	}

	@Test
	public void testUpdate() {
		TestJDBCStatement.testUpdate();
	}

	@Test
	public void testUpdateError() {
		TestJDBCStatement.testUpdateError();
	}
}
