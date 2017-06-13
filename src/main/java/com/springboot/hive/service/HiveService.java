package com.springboot.hive.service;

import com.springboot.hive.dao.HiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by VenkataRamesh on 6/11/2017.
 */

@Component
public class HiveService {
    @Autowired
    private HiveRepository repo;

    public Long recordCount(String tableName) {
        return repo.recordCount(tableName);
    }

    public List<String> fetchRecords(String tableName) {
        return repo.queryTable(tableName);
    }


}
