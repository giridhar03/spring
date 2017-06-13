package com.springboot.hive.poc;


import org.apache.hive.service.server.HiveServer2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveClientCallback;
import org.springframework.data.hadoop.hive.HiveOperations;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.assertTrue;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by VenkataRamesh on 6/12/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("application-context.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class HiveTestRunner {

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private HiveOperations hiveTemplate;

    @Test
    public void testHiveConnection() throws Exception {
        JdbcTemplate jdbc = ctx.getBean("template", JdbcTemplate.class);
        String tableName = "testHiveDriverTable";

        jdbc.execute("drop table if exists " + tableName);
        jdbc.execute("create table " + tableName + " (key int, value string)");
        Long rowCount = jdbc.query("select count(1) from " + tableName, new ResultSetExtractor<Long>() {
            public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
                return rs.getLong(1);
            }
        });

        assertTrue(rowCount == 0);
    }

    @Test
    public void testQueryForInsert() throws Exception {
        String tableName = "testHiveDriverTable";
        hiveTemplate.query("create table if not exists " + tableName + " (key int, value string)");
        hiveTemplate.query("insert into " + tableName + " values (1,'Value_1')");
        System.out.println(hiveTemplate.query("show tables"));
        assertEquals(Integer.valueOf(1), hiveTemplate.queryForInt("select count(1) as cnt from " + tableName));
    }

    @Test
    public void testQueryForInt() throws Exception {
        String tableName = "testHiveDriverTable";
        hiveTemplate.query("create table if not exists " + tableName + " (key int, value string)");
        System.out.println(hiveTemplate.query("show tables"));
        assertEquals(Integer.valueOf(0), hiveTemplate.queryForInt("select count(1) as cnt from " + tableName));
    }

    @Test
    public void testQueryForLong() throws Exception {
        String tableName = "testHiveDriverTable";
        assertEquals(Long.valueOf(0), hiveTemplate.queryForLong("select count(1) as cnt from " + tableName + ";"));
    }

    @Test
    public void testHiveTemplate() throws Exception {
        System.out.println(hiveTemplate.execute(new HiveClientCallback<Object>() {
            @Override
            public Object doInHive(HiveClient hiveClient) throws Exception {
                TODO:
                return hiveClient.execute("show tables");
            }
        }));
    }

    @Test
    public void testHiveServer() throws Exception {
        HiveServer2 server = ctx.getBean(HiveServer2.class);
        assertNotNull(server);
        assertTrue(server.getServiceState().equals(HiveServer2.STATE.STARTED));
    }

}
