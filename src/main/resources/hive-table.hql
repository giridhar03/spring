drop table if exists testHiveBatchTable;
create table if not exists testHiveBatchTable (key string, value string);

#load data from external into hive
#load data inpath '${hiveconf:inputFile}' into table testHiveInputTable;
