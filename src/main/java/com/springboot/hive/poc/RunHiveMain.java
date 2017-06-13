package com.springboot.hive.poc;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.springboot.hive.service.HiveService;

import java.util.Iterator;
import java.util.List;

/**
 * Created by VenkataRamesh on 6/11/2017.
 */

@Configuration
@ComponentScan
public class RunHiveMain {

    private static final Logger logger = Logger.getLogger(RunHiveMain.class);

    public static void main(String[] args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
            assert (context != null);

            String[] queryParams = args;
            if (queryParams.length == 0) {
                System.out.println("Enter hive table name..exiting now...");
                System.exit(0);
            }

            HiveService service = context.getBean(HiveService.class);
            /*logger.info("Executing query for record count");
            Long recordCount = service.recordCount(queryParams[0]);
            System.out.println("Record count in table " + queryParams[0] + " :" + recordCount);*/

            logger.info("Executing query for records in table");
            List<String> queryresults = service.fetchRecords(queryParams[0]);
            Iterator<String> it = queryresults.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
