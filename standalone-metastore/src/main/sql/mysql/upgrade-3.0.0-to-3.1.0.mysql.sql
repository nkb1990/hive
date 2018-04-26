SELECT 'Upgrading MetaStore schema from 3.0.0 to 3.1.0' AS ' ';
  
-- HIVE-19440
ALTER TABLE `GLOBAL_PRIVS` ADD `AUTHORIZER` varchar(128) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL;
ALTER TABLE `GLOBAL_PRIVS` DROP INDEX `GLOBALPRIVILEGEINDEX`;
ALTER TABLE `GLOBAL_PRIVS` ADD CONSTRAINT `GLOBALPRIVILEGEINDEX` UNIQUE(`AUTHORIZER`,`PRINCIPAL_NAME`,`PRINCIPAL_TYPE`,`USER_PRIV`,`GRANTOR`,`GRANTOR_TYPE`);

ALTER TABLE `DB_PRIVS` ADD `AUTHORIZER` varchar(128) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL;
ALTER TABLE `DB_PRIVS` DROP INDEX `DBPRIVILEGEINDEX`;
ALTER TABLE `DB_PRIVS` ADD CONSTRAINT `DBPRIVILEGEINDEX` UNIQUE(`AUTHORIZER`,`DB_ID`,`PRINCIPAL_NAME`,`PRINCIPAL_TYPE`,`DB_PRIV`,`GRANTOR`,`GRANTOR_TYPE`);

ALTER TABLE `TBL_PRIVS` ADD `AUTHORIZER` varchar(128) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL;
ALTER TABLE `TBL_PRIVS` DROP INDEX `TABLEPRIVILEGEINDEX`;
ALTER TABLE `TBL_PRIVS` ADD INDEX `TABLEPRIVILEGEINDEX` (`AUTHORIZER`,`TBL_ID`,`PRINCIPAL_NAME`,`PRINCIPAL_TYPE`,`TBL_PRIV`,`GRANTOR`,`GRANTOR_TYPE`);

ALTER TABLE `PART_PRIVS` ADD `AUTHORIZER` varchar(128) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL;
ALTER TABLE `PART_PRIVS` DROP INDEX `PARTPRIVILEGEINDEX`;
ALTER TABLE `PART_PRIVS` ADD INDEX `PARTPRIVILEGEINDEX` (`AUTHORIZER`,`PART_ID`,`PRINCIPAL_NAME`,`PRINCIPAL_TYPE`,`PART_PRIV`,`GRANTOR`,`GRANTOR_TYPE`);

ALTER TABLE `TBL_COL_PRIVS` ADD `AUTHORIZER` varchar(128) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL;
ALTER TABLE `TBL_COL_PRIVS` DROP INDEX `TABLECOLUMNPRIVILEGEINDEX`;
ALTER TABLE `TBL_COL_PRIVS` ADD INDEX `TABLECOLUMNPRIVILEGEINDEX` (`AUTHORIZER`,`TBL_ID`,`COLUMN_NAME`,`PRINCIPAL_NAME`,`PRINCIPAL_TYPE`,`TBL_COL_PRIV`,`GRANTOR`,`GRANTOR_TYPE`);

ALTER TABLE `PART_COL_PRIVS` ADD `AUTHORIZER` varchar(128) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL;
ALTER TABLE `PART_COL_PRIVS` DROP INDEX `PARTITIONCOLUMNPRIVILEGEINDEX`;
ALTER TABLE `PART_COL_PRIVS` ADD INDEX `PARTITIONCOLUMNPRIVILEGEINDEX` (`AUTHORIZER`,`PART_ID`,`COLUMN_NAME`,`PRINCIPAL_NAME`,`PRINCIPAL_TYPE`,`PART_COL_PRIV`,`GRANTOR`,`GRANTOR_TYPE`);

-- HIVE-19340
ALTER TABLE TXNS ADD COLUMN TXN_TYPE int DEFAULT NULL;

CREATE INDEX TAB_COL_STATS_IDX ON TAB_COL_STATS (CAT_NAME, DB_NAME, TABLE_NAME, COLUMN_NAME) USING BTREE;

--HIVE-19323
-- Update TXN_COMPONENTS
ALTER TABLE TXN_COMPONENTS ADD COLUMN TC_CATALOG VARCHAR(256);
UPDATE TXN_COMPONENTS 
  SET TC_CATALOG = 'hive';
ALTER TABLE TXN_COMPONENTS CHANGE COLUMN TC_CATALOG TC_CATALOG varchar(256) NOT NULL;

-- Update COMPLETED_TXN_COMPONENTS
ALTER TABLE COMPLETED_TXN_COMPONENTS ADD COLUMN CTC_CATALOG VARCHAR(256);
UPDATE COMPLETED_TXN_COMPONENTS 
  SET CTC_CATALOG = 'hive';
ALTER TABLE COMPLETED_TXN_COMPONENTS CHANGE COLUMN CTC_CATALOG CTC_CATALOG varchar(256) NOT NULL;

DROP INDEX COMPLETED_TXN_COMPONENTS_IDX ON COMPLETED_TXN_COMPONENTS;
CREATE INDEX COMPLETED_TXN_COMPONENTS_IDX ON COMPLETED_TXN_COMPONENTS (CTC_CATALOG, CTC_DATABASE, CTC_TABLE, CTC_PARTITION) USING BTREE;

-- Update HIVE_LOCKS
ALTER TABLE HIVE_LOCKS ADD COLUMN HL_CATALOG VARCHAR(256);
UPDATE HIVE_LOCKS 
  SET HL_CATALOG = 'hive';
ALTER TABLE HIVE_LOCKS CHANGE COLUMN HL_CATALOG HL_CATALOG varchar(256) NOT NULL;

-- Update COMPACTION_QUEUE
ALTER TABLE COMPACTION_QUEUE ADD COLUMN CQ_CATALOG VARCHAR(256);
UPDATE COMPACTION_QUEUE 
  SET CQ_CATALOG = 'hive';
ALTER TABLE COMPACTION_QUEUE CHANGE COLUMN CQ_CATALOG CQ_CATALOG varchar(256) NOT NULL;

-- Update COMPLETED_COMPACTIONS
ALTER TABLE COMPLETED_COMPACTIONS ADD COLUMN CC_CATALOG VARCHAR(256);
UPDATE COMPLETED_COMPACTIONS 
  SET CC_CATALOG = 'hive';
ALTER TABLE COMPLETED_COMPACTIONS CHANGE COLUMN CC_CATALOG CC_CATALOG varchar(256) NOT NULL;

-- Update WRITE_SET
ALTER TABLE WRITE_SET ADD COLUMN WS_CATALOG VARCHAR(256);
UPDATE WRITE_SET 
  SET WS_CATALOG = 'hive';
ALTER TABLE WRITE_SET CHANGE COLUMN WS_CATALOG WS_CATALOG varchar(256) NOT NULL;

-- Update TXN_TO_WRITE_ID
ALTER TABLE TXN_TO_WRITE_ID ADD COLUMN T2W_CATALOG VARCHAR(256);
UPDATE TXN_TO_WRITE_ID 
  SET T2W_CATALOG = 'hive';
ALTER TABLE TXN_TO_WRITE_ID CHANGE COLUMN T2W_CATALOG T2W_CATALOG varchar(256) NOT NULL;

DROP INDEX TBL_TO_TXN_ID_IDX ON TXN_TO_WRITE_ID;
DROP INDEX TBL_TO_WRITE_ID_IDX ON TXN_TO_WRITE_ID;
CREATE UNIQUE INDEX TBL_TO_TXN_ID_IDX ON TXN_TO_WRITE_ID (T2W_CATALOG, T2W_DATABASE, T2W_TABLE, T2W_TXNID);
CREATE UNIQUE INDEX TBL_TO_WRITE_ID_IDX ON TXN_TO_WRITE_ID (T2W_CATALOG, T2W_DATABASE, T2W_TABLE, T2W_WRITEID);

-- Update NEXT_WRITE_ID
ALTER TABLE NEXT_WRITE_ID ADD COLUMN NWI_CATALOG VARCHAR(256);
UPDATE NEXT_WRITE_ID 
  SET NWI_CATALOG = 'hive';
ALTER TABLE NEXT_WRITE_ID CHANGE COLUMN NWI_CATALOG NWI_CATALOG varchar(256) NOT NULL;

DROP INDEX NEXT_WRITE_ID_IDX ON NEXT_WRITE_ID;
CREATE UNIQUE INDEX NEXT_WRITE_ID_IDX ON NEXT_WRITE_ID (NWI_CATALOG, NWI_DATABASE, NWI_TABLE);

-- These lines need to be last.  Insert any changes above.
UPDATE VERSION SET SCHEMA_VERSION='3.1.0', VERSION_COMMENT='Hive release version 3.1.0' where VER_ID=1;
SELECT 'Finished upgrading MetaStore schema from 3.0.0 to 3.1.0' AS ' ';
