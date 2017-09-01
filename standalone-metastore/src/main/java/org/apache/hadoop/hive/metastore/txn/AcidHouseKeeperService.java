/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.metastore.txn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.RunnableConfigurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performs background tasks for Transaction management in Hive.
 * Runs inside Hive Metastore Service.
 */
public class AcidHouseKeeperService implements RunnableConfigurable {
  private static final Logger LOG = LoggerFactory.getLogger(AcidHouseKeeperService.class);

  private Configuration conf;

  @Override
  public void setConf(Configuration configuration) {
    this.conf = configuration;
  }

  @Override
  public Configuration getConf() {
    return conf;
  }

  @Override
  public void run() {
    TxnStore.MutexAPI.LockHandle handle = null;
    try {
      TxnStore txnHandler = TxnUtils.getTxnStore(conf);
      handle = txnHandler.getMutexAPI().acquireLock(TxnStore.MUTEX_KEY.HouseKeeper.name());
      long startTime = System.currentTimeMillis();
      txnHandler.performTimeOuts();
      LOG.debug("timeout reaper ran for " + (System.currentTimeMillis() - startTime)/1000 +
          "seconds.");
    } catch(Throwable t) {
      LOG.error("Serious error in {}", Thread.currentThread().getName(), ": {}" + t.getMessage(), t);
    } finally {
      if(handle != null) {
        handle.releaseLocks();
      }
    }
  }
}
