/*
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

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.conf.MetastoreConf;
import org.apache.hadoop.hive.metastore.conf.MetastoreConf.ConfVars;
import org.apache.hadoop.hive.metastore.security.HadoopThriftAuthBridge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * TestMetaStoreInitListener. Test case for
 * {@link org.apache.hadoop.hive.metastore.MetaStoreInitListener}
 */
public class TestMetaStoreInitListener {
  private Configuration conf;

  @Before
  public void setUp() throws Exception {
    System.setProperty("hive.metastore.init.hooks", DummyMetaStoreInitListener.class.getName());
    int port = MetaStoreTestUtils.findFreePort();
    conf = MetastoreConf.newMetastoreConf();
    MetastoreConf.setVar(conf, ConfVars.THRIFT_URIS, "thrift://localhost:" + port);
    MetastoreConf.setLongVar(conf, ConfVars.THRIFT_CONNECTION_RETRIES, 3);
    MetastoreConf.setBoolVar(conf, ConfVars.HIVE_SUPPORT_CONCURRENCY, false);
    MetastoreConf.setClass(conf, ConfVars.EXPRESSION_PROXY_CLASS,
        DefaultPartitionExpressionProxy.class, PartitionExpressionProxy.class);
    MetaStoreTestUtils.startMetaStore(port, HadoopThriftAuthBridge.getBridge(), conf);
  }

  @Test
  public void testMetaStoreInitListener() throws Exception {
    // DummyMataStoreInitListener's onInit will be called at HMSHandler
    // initialization, and set this to true
    Assert.assertTrue(DummyMetaStoreInitListener.wasCalled);
  }

}