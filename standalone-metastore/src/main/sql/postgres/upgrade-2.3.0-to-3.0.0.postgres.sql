SELECT 'Upgrading MetaStore schema from 2.3.0 to 3.0.0';

--\i 040-HIVE-16556.postgres.sql;
CREATE TABLE "METASTORE_DB_PROPERTIES"
(
  "PROPERTY_KEY" VARCHAR(255) NOT NULL,
  "PROPERTY_VALUE" VARCHAR(1000) NOT NULL,
  "DESCRIPTION" VARCHAR(1000)
);

ALTER TABLE ONLY "METASTORE_DB_PROPERTIES"
  ADD CONSTRAINT "PROPERTY_KEY_PK" PRIMARY KEY ("PROPERTY_KEY");

--\i 041-HIVE-16575.postgres.sql;
CREATE INDEX "CONSTRAINTS_CONSTRAINT_TYPE_INDEX" ON "KEY_CONSTRAINTS" USING BTREE ("CONSTRAINT_TYPE");

--\i 042-HIVE-16922.postgres.sql;
UPDATE SERDE_PARAMS
SET PARAM_KEY='collection.delim'
WHERE PARAM_KEY='colelction.delim';

--\i 043-HIVE-16997.postgres.sql;
ALTER TABLE "PART_COL_STATS" ADD COLUMN "BIT_VECTOR" BYTEA;
ALTER TABLE "TAB_COL_STATS" ADD COLUMN "BIT_VECTOR" BYTEA;

--\i 044-HIVE-16886.postgres.sql;
INSERT INTO "NOTIFICATION_SEQUENCE" ("NNI_ID", "NEXT_EVENT_ID") SELECT 1,1 WHERE NOT EXISTS ( SELECT "NEXT_EVENT_ID" FROM "NOTIFICATION_SEQUENCE");

--\i 045-HIVE-17566.postgres.sql;
CREATE TABLE "WM_RESOURCEPLAN" (
    "RP_ID" bigint NOT NULL,
    "NAME" character varying(128) NOT NULL,
    "QUERY_PARALLELISM" integer,
    "STATUS" character varying(20) NOT NULL
);

ALTER TABLE ONLY "WM_RESOURCEPLAN"
    ADD CONSTRAINT "WM_RESOURCEPLAN_pkey" PRIMARY KEY ("RP_ID");

ALTER TABLE ONLY "WM_RESOURCEPLAN"
    ADD CONSTRAINT "UNIQUE_WM_RESOURCEPLAN" UNIQUE ("NAME");


CREATE TABLE "WM_POOL" (
    "POOL_ID" bigint NOT NULL,
    "RP_ID" bigint NOT NULL,
    "PATH" character varying(1024) NOT NULL,
    "PARENT_POOL_ID" bigint,
    "ALLOC_FRACTION" double precision,
    "QUERY_PARALLELISM" integer
);

ALTER TABLE ONLY "WM_POOL"
    ADD CONSTRAINT "WM_POOL_pkey" PRIMARY KEY ("POOL_ID");

ALTER TABLE ONLY "WM_POOL"
    ADD CONSTRAINT "UNIQUE_WM_POOL" UNIQUE ("RP_ID", "PATH");

ALTER TABLE ONLY "WM_POOL"
    ADD CONSTRAINT "WM_POOL_FK1" FOREIGN KEY ("RP_ID") REFERENCES "WM_RESOURCEPLAN" ("RP_ID") DEFERRABLE;
ALTER TABLE ONLY "WM_POOL"
    ADD CONSTRAINT "WM_POOL_FK2" FOREIGN KEY ("PARENT_POOL_ID") REFERENCES "WM_POOL" ("POOL_ID") DEFERRABLE;


CREATE TABLE "WM_TRIGGER" (
    "TRIGGER_ID" bigint NOT NULL,
    "RP_ID" bigint NOT NULL,
    "NAME" character varying(128) NOT NULL,
    "TRIGGER_EXPRESSION" character varying(1024) DEFAULT NULL::character varying,
    "ACTION_EXPRESSION" character varying(1024) DEFAULT NULL::character varying
);

ALTER TABLE ONLY "WM_TRIGGER"
    ADD CONSTRAINT "WM_TRIGGER_pkey" PRIMARY KEY ("TRIGGER_ID");

ALTER TABLE ONLY "WM_TRIGGER"
    ADD CONSTRAINT "UNIQUE_WM_TRIGGER" UNIQUE ("RP_ID", "NAME");

ALTER TABLE ONLY "WM_TRIGGER"
    ADD CONSTRAINT "WM_TRIGGER_FK1" FOREIGN KEY ("RP_ID") REFERENCES "WM_RESOURCEPLAN" ("RP_ID") DEFERRABLE;


CREATE TABLE "WM_POOL_TO_TRIGGER" (
    "POOL_ID" bigint NOT NULL,
    "TRIGGER_ID" bigint NOT NULL
);

ALTER TABLE ONLY "WM_POOL_TO_TRIGGER"
    ADD CONSTRAINT "WM_POOL_TO_TRIGGER_pkey" PRIMARY KEY ("POOL_ID", "TRIGGER_ID");

ALTER TABLE ONLY "WM_POOL_TO_TRIGGER"
    ADD CONSTRAINT "WM_POOL_TO_TRIGGER_FK1" FOREIGN KEY ("POOL_ID") REFERENCES "WM_POOL" ("POOL_ID") DEFERRABLE;

ALTER TABLE ONLY "WM_POOL_TO_TRIGGER"
    ADD CONSTRAINT "WM_POOL_TO_TRIGGER_FK2" FOREIGN KEY ("TRIGGER_ID") REFERENCES "WM_TRIGGER" ("TRIGGER_ID") DEFERRABLE;


CREATE TABLE "WM_MAPPING" (
    "MAPPING_ID" bigint NOT NULL,
    "RP_ID" bigint NOT NULL,
    "ENTITY_TYPE" character varying(10) NOT NULL,
    "ENTITY_NAME" character varying(128) NOT NULL,
    "POOL_ID" bigint NOT NULL,
    "ORDERING" integer
);

ALTER TABLE ONLY "WM_MAPPING"
    ADD CONSTRAINT "WM_MAPPING_pkey" PRIMARY KEY ("MAPPING_ID");

ALTER TABLE ONLY "WM_MAPPING"
    ADD CONSTRAINT "UNIQUE_WM_MAPPING" UNIQUE ("RP_ID", "ENTITY_TYPE", "ENTITY_NAME");

ALTER TABLE ONLY "WM_MAPPING"
    ADD CONSTRAINT "WM_MAPPING_FK1" FOREIGN KEY ("RP_ID") REFERENCES "WM_RESOURCEPLAN" ("RP_ID") DEFERRABLE;

ALTER TABLE ONLY "WM_MAPPING"
    ADD CONSTRAINT "WM_MAPPING_FK2" FOREIGN KEY ("POOL_ID") REFERENCES "WM_POOL" ("POOL_ID") DEFERRABLE;

-- Upgrades for Schema Registry objects
ALTER TABLE "SERDES" ADD COLUMN "DESCRIPTION" VARCHAR(4000);
ALTER TABLE "SERDES" ADD COLUMN "SERIALIZER_CLASS" VARCHAR(4000);
ALTER TABLE "SERDES" ADD COLUMN "DESERIALIZER_CLASS" VARCHAR(4000);
ALTER TABLE "SERDES" ADD COLUMN "SERDE_TYPE" INTEGER;

CREATE TABLE "I_SCHEMA" (
  "SCHEMA_ID" bigint primary key,
  "SCHEMA_TYPE" integer not null,
  "NAME" varchar(256) unique,
  "DB_ID" bigint references "DBS" ("DB_ID"),
  "COMPATIBILITY" integer not null,
  "VALIDATION_LEVEL" integer not null,
  "CAN_EVOLVE" boolean not null,
  "SCHEMA_GROUP" varchar(256),
  "DESCRIPTION" varchar(4000)
);

CREATE TABLE "SCHEMA_VERSION" (
  "SCHEMA_VERSION_ID" bigint primary key,
  "SCHEMA_ID" bigint references "I_SCHEMA" ("SCHEMA_ID"),
  "VERSION" integer not null,
  "CREATED_AT" bigint not null,
  "CD_ID" bigint references "CDS" ("CD_ID"), 
  "STATE" integer not null,
  "DESCRIPTION" varchar(4000),
  "SCHEMA_TEXT" text,
  "FINGERPRINT" varchar(256),
  "SCHEMA_VERSION_NAME" varchar(256),
  "SERDE_ID" bigint references "SERDES" ("SERDE_ID"), 
  unique ("SCHEMA_ID", "VERSION")
);


UPDATE "VERSION" SET "SCHEMA_VERSION"='3.0.0', "VERSION_COMMENT"='Hive release version 3.0.0' where "VER_ID"=1;
SELECT 'Finished upgrading MetaStore schema from 2.3.0 to 3.0.0';

