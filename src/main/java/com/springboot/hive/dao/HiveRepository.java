package com.springboot.hive.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by VenkataRamesh on 6/11/2017.
 */

@Repository
public class HiveRepository {

    private static final Logger logger = Logger.getLogger(HiveRepository.class);

    @Autowired
    private HiveTemplate hiveTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> queryTable(String tableName) {
        logger.info("Querying table: " + tableName);
        List<String> results = jdbcTemplate.query("select * from " + tableName, new ResultSetExtractor<List<String>>() {
            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<String> results = new ArrayList<>();
                while (rs.next()) {
                    results.add("key: " + rs.getString(1) + " value: " + rs.getString(2));
                }
                return results;
            }
        });
        return results;
    }


    public Long recordCount(String tableName) {
        logger.info("Querying table: " + tableName + "for record count");
        Long count = hiveTemplate.queryForLong("select count(*) from " + tableName + ";");
        return count;
    }
}
