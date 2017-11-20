/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.metastore;

import java.io.File;
import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.apache.hadoop.hive.metastore.conf.MetastoreConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.hive.cli.CliSessionState;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hive.common.util.HiveStringUtils;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.ObjectStore;
import org.apache.hadoop.hive.ql.Driver;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.session.SessionState;

public class TestMetastoreVersion extends TestCase {
  private static final Logger LOG = LoggerFactory.getLogger(TestMetastoreVersion.class);
  protected HiveConf hiveConf;
  private Driver driver;
  private String metaStoreRoot;
  private String testMetastoreDB;
  private IMetaStoreSchemaInfo metastoreSchemaInfo;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    Field defDb = HiveMetaStore.HMSHandler.class.getDeclaredField("currentUrl");
    defDb.setAccessible(true);
    defDb.set(null, null);
    // reset defaults
    ObjectStore.setSchemaVerified(false);
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION.getHiveName(), "false");
    System.setProperty(MetastoreConf.ConfVars.AUTO_CREATE_ALL.getHiveName(), "true");
    hiveConf = new HiveConf(this.getClass());
    System.setProperty("hive.support.concurrency", "false");
    System.setProperty("hive.metastore.event.listeners",
        DummyListener.class.getName());
    System.setProperty("hive.metastore.pre.event.listeners",
        DummyPreListener.class.getName());
    testMetastoreDB = System.getProperty("java.io.tmpdir") +
      File.separator + "test_metastore-" + System.currentTimeMillis();
    System.setProperty(MetastoreConf.ConfVars.CONNECTURLKEY.getHiveName(),
        "jdbc:derby:" + testMetastoreDB + ";create=true");
    metaStoreRoot = System.getProperty("test.tmp.dir");
    metastoreSchemaInfo = MetaStoreSchemaInfoFactory.get(hiveConf,
        System.getProperty("test.tmp.dir", "target/tmp"), "derby");
  }

  @Override
  protected void tearDown() throws Exception {
    File metaStoreDir = new File(testMetastoreDB);
    if (metaStoreDir.exists()) {
      FileUtils.forceDeleteOnExit(metaStoreDir);
    }
  }

  /***
   * Test config defaults
   */
  public void testDefaults() {
    System.clearProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION.toString());
    hiveConf = new HiveConf(this.getClass());
    assertFalse(MetastoreConf.getBoolVar(hiveConf, MetastoreConf.ConfVars.SCHEMA_VERIFICATION));
    assertTrue(MetastoreConf.getBoolVar(hiveConf, MetastoreConf.ConfVars.AUTO_CREATE_ALL));
  }

  /***
   * Test schema verification property
   * @throws Exception
   */
  public void testVersionRestriction () throws Exception {
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION_RECORD_VERSION.getHiveName(), "true");
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION.getHiveName(), "true");
    hiveConf = new HiveConf(this.getClass());
    assertTrue(MetastoreConf.getBoolVar(hiveConf, MetastoreConf.ConfVars.SCHEMA_VERIFICATION));
    assertFalse(MetastoreConf.getBoolVar(hiveConf, MetastoreConf.ConfVars.AUTO_CREATE_ALL));

    // session creation should fail since the schema didn't get created
    try {
      SessionState.start(new CliSessionState(hiveConf));
      Hive.get(hiveConf).getMSC();
      fail("An exception is expected since schema is not created.");
    } catch (Exception re) {
      LOG.info("Exception in testVersionRestriction: " + re, re);
      String msg = HiveStringUtils.stringifyException(re);
      assertTrue("Expected 'Version information not found in metastore' in: " + msg, msg
        .contains("Version information not found in metastore"));
    }
  }

  /***
   * Test that with no verification, and record verification enabled, hive populates the schema
   * and version correctly
   * @throws Exception
   */
  public void testMetastoreVersion () throws Exception {
    // let the schema and version be auto created
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION.getHiveName(), "false");
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION_RECORD_VERSION.getHiveName(), "true");
    hiveConf = new HiveConf(this.getClass());
    SessionState.start(new CliSessionState(hiveConf));
    driver = new Driver(hiveConf);
    driver.run("show tables");

    // correct version stored by Metastore during startup
    assertEquals(metastoreSchemaInfo.getHiveSchemaVersion(), getVersion(hiveConf));
    setVersion(hiveConf, "foo");
    assertEquals("foo", getVersion(hiveConf));
  }

  /***
   * Test that with verification enabled, hive works when the correct schema is already populated
   * @throws Exception
   */
  public void testVersionMatching () throws Exception {
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION_RECORD_VERSION.getHiveName(), "true");
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION.getHiveName(), "false");
    hiveConf = new HiveConf(this.getClass());
    SessionState.start(new CliSessionState(hiveConf));
    driver = new Driver(hiveConf);
    driver.run("show tables");

    ObjectStore.setSchemaVerified(false);
    MetastoreConf.setBoolVar(hiveConf, MetastoreConf.ConfVars.SCHEMA_VERIFICATION, true);
    setVersion(hiveConf, metastoreSchemaInfo.getHiveSchemaVersion());
    driver = new Driver(hiveConf);
    CommandProcessorResponse proc = driver.run("show tables");
    assertTrue(proc.getResponseCode() == 0);
  }

  /**
   * Store garbage version in metastore and verify that hive fails when verification is on
   * @throws Exception
   */
  public void testVersionMisMatch () throws Exception {
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION_RECORD_VERSION.getHiveName(), "true");
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION.getHiveName(), "false");
    hiveConf = new HiveConf(this.getClass());
    SessionState.start(new CliSessionState(hiveConf));
    driver = new Driver(hiveConf);
    driver.run("show tables");

    ObjectStore.setSchemaVerified(false);
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION.getHiveName(), "true");
    hiveConf = new HiveConf(this.getClass());
    setVersion(hiveConf, "fooVersion");
    SessionState.start(new CliSessionState(hiveConf));
    driver = new Driver(hiveConf);
    CommandProcessorResponse proc = driver.run("show tables");
    assertTrue(proc.getResponseCode() != 0);
  }

  /**
   * Store higher version in metastore and verify that hive works with the compatible
   * version
   * @throws Exception
   */
  public void testVersionCompatibility () throws Exception {
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION_RECORD_VERSION.getHiveName(), "true");
    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION.getHiveName(), "false");
    hiveConf = new HiveConf(this.getClass());
    SessionState.start(new CliSessionState(hiveConf));
    driver = new Driver(hiveConf);
    driver.run("show tables");

    System.setProperty(MetastoreConf.ConfVars.SCHEMA_VERIFICATION.getHiveName(), "true");
    hiveConf = new HiveConf(this.getClass());
    setVersion(hiveConf, "3.9000.0");
    SessionState.start(new CliSessionState(hiveConf));
    driver = new Driver(hiveConf);
    CommandProcessorResponse proc = driver.run("show tables");
    assertEquals(0, proc.getResponseCode());
  }

  //  write the given version to metastore
  private String getVersion(HiveConf conf) throws HiveMetaException {
    return getMetaStoreVersion();
  }

  //  write the given version to metastore
  private void setVersion(HiveConf conf, String version) throws HiveMetaException {
    setMetaStoreVersion(version, "setVersion test");
  }

  // Load the version stored in the metastore db
  public String getMetaStoreVersion() throws HiveMetaException {
    ObjectStore objStore = new ObjectStore();
    objStore.setConf(hiveConf);
    try {
      return objStore.getMetaStoreSchemaVersion();
    } catch (MetaException e) {
      throw new HiveMetaException("Failed to get version", e);
    }
  }

  // Store the given version and comment in the metastore
  public void setMetaStoreVersion(String newVersion, String comment) throws HiveMetaException {
    ObjectStore objStore = new ObjectStore();
    objStore.setConf(hiveConf);
    try {
      objStore.setMetaStoreSchemaVersion(newVersion, comment);
    } catch (MetaException e) {
      throw new HiveMetaException("Failed to set version", e);
    }
  }


}

