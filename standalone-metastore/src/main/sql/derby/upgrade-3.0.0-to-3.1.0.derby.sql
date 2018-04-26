-- Upgrade MetaStore schema from 3.0.0 to 3.1.0
-- HIVE-19440
ALTER TABLE "APP"."GLOBAL_PRIVS" ADD "AUTHORIZER" VARCHAR(128);
DROP INDEX "APP"."GLOBALPRIVILEGEINDEX";
CREATE UNIQUE INDEX "APP"."GLOBALPRIVILEGEINDEX" ON "APP"."GLOBAL_PRIVS" ("AUTHORIZER", "PRINCIPAL_NAME", "PRINCIPAL_TYPE", "USER_PRIV", "GRANTOR", "GRANTOR_TYPE");

ALTER TABLE "APP"."DB_PRIVS" ADD "AUTHORIZER" VARCHAR(128);
DROP INDEX "APP"."DBPRIVILEGEINDEX";
CREATE UNIQUE INDEX "APP"."DBPRIVILEGEINDEX" ON "APP"."DB_PRIVS" ("AUTHORIZER", "DB_ID", "PRINCIPAL_NAME", "PRINCIPAL_TYPE", "DB_PRIV", "GRANTOR", "GRANTOR_TYPE");

ALTER TABLE "APP"."TBL_PRIVS" ADD "AUTHORIZER" VARCHAR(128);
DROP INDEX "APP"."TABLEPRIVILEGEINDEX";
CREATE INDEX "APP"."TABLEPRIVILEGEINDEX" ON "APP"."TBL_PRIVS" ("AUTHORIZER", "TBL_ID", "PRINCIPAL_NAME", "PRINCIPAL_TYPE", "TBL_PRIV", "GRANTOR", "GRANTOR_TYPE");

ALTER TABLE "APP"."PART_PRIVS" ADD "AUTHORIZER" VARCHAR(128);
DROP INDEX "APP"."PARTPRIVILEGEINDEX";
CREATE INDEX "APP"."PARTPRIVILEGEINDEX" ON "APP"."PART_PRIVS" ("AUTHORIZER", "PART_ID", "PRINCIPAL_NAME", "PRINCIPAL_TYPE", "PART_PRIV", "GRANTOR", "GRANTOR_TYPE");

ALTER TABLE "APP"."TBL_COL_PRIVS" ADD "AUTHORIZER" VARCHAR(128);
DROP INDEX "APP"."TABLECOLUMNPRIVILEGEINDEX";
CREATE INDEX "APP"."TABLECOLUMNPRIVILEGEINDEX" ON "APP"."TBL_COL_PRIVS" ("AUTHORIZER", "TBL_ID", "COLUMN_NAME", "PRINCIPAL_NAME", "PRINCIPAL_TYPE", "TBL_COL_PRIV", "GRANTOR", "GRANTOR_TYPE");

ALTER TABLE "APP"."PART_COL_PRIVS" ADD "AUTHORIZER" VARCHAR(128);
DROP INDEX "APP"."PARTITIONCOLUMNPRIVILEGEINDEX";
CREATE INDEX "APP"."PARTITIONCOLUMNPRIVILEGEINDEX" ON "APP"."PART_COL_PRIVS" ("AUTHORIZER", "PART_ID", "COLUMN_NAME", "PRINCIPAL_NAME", "PRINCIPAL_TYPE", "PART_COL_PRIV", "GRANTOR", "GRANTOR_TYPE");

-- HIVE-19340
ALTER TABLE TXNS ADD COLUMN TXN_TYPE integer;

CREATE INDEX "APP"."TAB_COL_STATS_IDX" ON "APP"."TAB_COL_STATS" ("CAT_NAME", "DB_NAME", "TABLE_NAME", "COLUMN_NAME");

-- HIVE-18973, add catalogs to TXN system
-- Add to TXN_COMPONENTS
ALTER TABLE TXN_COMPONENTS ADD COLUMN TC_CATALOG VARCHAR(256);
UPDATE TXN_COMPONENTS 
  SET TC_CATALOG = 'hive';
ALTER TABLE TXN_COMPONENTS ALTER COLUMN TC_CATALOG NOT NULL;

-- Add to COMPLETED_TXN_COMPONENTS
ALTER TABLE COMPLETED_TXN_COMPONENTS ADD COLUMN CTC_CATALOG VARCHAR(256);
UPDATE COMPLETED_TXN_COMPONENTS 
  SET CTC_CATALOG = 'hive';
ALTER TABLE COMPLETED_TXN_COMPONENTS ALTER COLUMN CTC_CATALOG NOT NULL;

DROP INDEX COMPLETED_TXN_COMPONENTS_IDX; 
CREATE INDEX COMPLETED_TXN_COMPONENTS_IDX ON COMPLETED_TXN_COMPONENTS (CTC_CATALOG, CTC_DATABASE, CTC_TABLE, CTC_PARTITION);

-- Add to HIVE_LOCKS
ALTER TABLE HIVE_LOCKS ADD COLUMN HL_CATALOG VARCHAR(256);
UPDATE HIVE_LOCKS 
  SET HL_CATALOG = 'hive';
ALTER TABLE HIVE_LOCKS ALTER COLUMN HL_CATALOG NOT NULL;

-- Add to COMPACTION_QUEUE
ALTER TABLE COMPACTION_QUEUE ADD COLUMN CQ_CATALOG VARCHAR(256);
UPDATE COMPACTION_QUEUE 
  SET CQ_CATALOG = 'hive';
ALTER TABLE COMPACTION_QUEUE ALTER COLUMN CQ_CATALOG NOT NULL;

-- Add to COMPLETED_COMPACTIONS
ALTER TABLE COMPLETED_COMPACTIONS ADD COLUMN CC_CATALOG VARCHAR(256);
UPDATE COMPLETED_COMPACTIONS 
  SET CC_CATALOG = 'hive';
ALTER TABLE COMPLETED_COMPACTIONS ALTER COLUMN CC_CATALOG NOT NULL;

-- Add to WRITE_SET
ALTER TABLE WRITE_SET ADD COLUMN WS_CATALOG VARCHAR(256);
UPDATE WRITE_SET 
  SET WS_CATALOG = 'hive';
ALTER TABLE WRITE_SET ALTER COLUMN WS_CATALOG NOT NULL;

-- Add to TXN_TO_WRITE_ID
ALTER TABLE TXN_TO_WRITE_ID ADD COLUMN T2W_CATALOG VARCHAR(256);
UPDATE TXN_TO_WRITE_ID 
  SET T2W_CATALOG = 'hive';
ALTER TABLE TXN_TO_WRITE_ID ALTER COLUMN T2W_CATALOG NOT NULL;

DROP INDEX TBL_TO_TXN_ID_IDX;
DROP INDEX TBL_TO_WRITE_ID_IDX;
CREATE UNIQUE INDEX TBL_TO_TXN_ID_IDX ON TXN_TO_WRITE_ID (T2W_CATALOG, T2W_DATABASE, T2W_TABLE, T2W_TXNID);
CREATE UNIQUE INDEX TBL_TO_WRITE_ID_IDX ON TXN_TO_WRITE_ID (T2W_CATALOG, T2W_DATABASE, T2W_TABLE, T2W_WRITEID);

-- Add to NEXT_WRITE_ID
ALTER TABLE NEXT_WRITE_ID ADD COLUMN NWI_CATALOG VARCHAR(256);
UPDATE NEXT_WRITE_ID 
  SET NWI_CATALOG = 'hive';
ALTER TABLE NEXT_WRITE_ID ALTER COLUMN NWI_CATALOG NOT NULL;

DROP INDEX NEXT_WRITE_ID_IDX;
CREATE UNIQUE INDEX NEXT_WRITE_ID_IDX ON NEXT_WRITE_ID (NWI_CATALOG, NWI_DATABASE, NWI_TABLE);

-- This needs to be the last thing done.  Insert any changes above this line.
UPDATE "APP".VERSION SET SCHEMA_VERSION='3.1.0', VERSION_COMMENT='Hive release version 3.1.0' where VER_ID=1;
