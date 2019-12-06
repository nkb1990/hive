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
package org.apache.hadoop.hive.cli.control;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.hadoop.hive.ql.QTestMiniClusters;
import org.apache.hadoop.hive.ql.QTestMiniClusters.MiniClusterType;
import org.apache.hadoop.hive.ql.parse.CoreParseNegative;

public class CliConfigs {

  private static URL testConfigProps = getTestPropsURL();

  private static URL getTestPropsURL() {
    try {
      return new File(
          AbstractCliConfig.HIVE_ROOT + "/itests/src/test/resources/testconfiguration.properties")
              .toURI().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public static class CliConfig extends AbstractCliConfig {

    public CliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");

        excludesFrom(testConfigProps, "minillap.query.files");
        excludesFrom(testConfigProps, "minillaplocal.query.files");
        excludesFrom(testConfigProps, "minimr.query.files");
        excludesFrom(testConfigProps, "minitez.query.files");
        excludesFrom(testConfigProps, "encrypted.query.files");
        excludesFrom(testConfigProps, "disabled.query.files");
        excludesFrom(testConfigProps, "druid.query.files");
        excludesFrom(testConfigProps, "druid.kafka.query.files");
        excludesFrom(testConfigProps, "hive.kafka.query.files");
        excludesFrom(testConfigProps, "erasurecoding.only.query.files");

        excludeQuery("fouter_join_ppr.q"); // Disabled in HIVE-19509
        excludeQuery("udaf_context_ngrams.q"); // disabled in HIVE-20741
        excludeQuery("udaf_corr.q"); // disabled in HIVE-20741
        excludeQuery("udaf_histogram_numeric.q"); // disabled in HIVE-20715
        excludeQuery("stat_estimate_related_col.q"); // disabled in HIVE-20727
        excludeQuery("vector_groupby_reduce.q"); // Disabled in HIVE-21396

        setResultsDir("ql/src/test/results/clientpositive");
        setLogDir("itests/qtest/target/qfile-results/clientpositive");

        setInitScript("q_test_init.sql");
        setCleanupScript("q_test_cleanup.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class ParseNegativeConfig extends AbstractCliConfig {
    public ParseNegativeConfig() {
      super(CoreParseNegative.class);
      try {
        setQueryDir("ql/src/test/queries/negative");

        setResultsDir("ql/src/test/results/compiler/errors");
        setLogDir("itests/qtest/target/qfile-results/negative");

        setInitScript("q_test_init_parse.sql");
        setCleanupScript("q_test_cleanup.sql");

        setHiveConfDir("data/conf/perf-reg/");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class MinimrCliConfig extends AbstractCliConfig {
    public MinimrCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");

        includesFrom(testConfigProps, "minimr.query.files");

        setResultsDir("ql/src/test/results/clientpositive");
        setLogDir("itests/qtest/target/qfile-results/clientpositive");

        setInitScript("q_test_init_for_minimr.sql");
        setCleanupScript("q_test_cleanup.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.MR);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class MiniTezCliConfig extends AbstractCliConfig {
    public MiniTezCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");

        includesFrom(testConfigProps, "minitez.query.files");
        includesFrom(testConfigProps, "minitez.query.files.shared");
        excludesFrom(testConfigProps, "minillap.query.files");
        excludesFrom(testConfigProps, "minillap.shared.query.files");

        setResultsDir("ql/src/test/results/clientpositive/tez");
        setLogDir("itests/qtest/target/qfile-results/clientpositive");

        setInitScript("q_test_init_tez.sql");
        setCleanupScript("q_test_cleanup_tez.sql");

        setHiveConfDir("data/conf/tez");
        setClusterType(MiniClusterType.TEZ);
        setFsType(QTestMiniClusters.FsType.HDFS);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class MiniLlapCliConfig extends AbstractCliConfig {
    public MiniLlapCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");

        includesFrom(testConfigProps, "minillap.query.files");
        includesFrom(testConfigProps, "minillap.shared.query.files");

        setResultsDir("ql/src/test/results/clientpositive/llap");
        setLogDir("itests/qtest/target/qfile-results/clientpositive");

        setInitScript("q_test_init.sql");
        setCleanupScript("q_test_cleanup.sql");

        setHiveConfDir("data/conf/llap");
        setClusterType(MiniClusterType.LLAP);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class MiniDruidCliConfig extends AbstractCliConfig {
    public MiniDruidCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");

        includesFrom(testConfigProps, "druid.query.files");
        excludeQuery("druid_timestamptz.q"); // Disabled in HIVE-20322
        excludeQuery("druidmini_joins.q"); // Disabled in HIVE-20322
        excludeQuery("druidmini_masking.q"); // Disabled in HIVE-20322
        //excludeQuery("druidmini_test1.q"); // Disabled in HIVE-20322

        setResultsDir("ql/src/test/results/clientpositive/druid");
        setLogDir("itests/qtest/target/tmp/log");

        setInitScript("q_test_druid_init.sql");
        setCleanupScript("q_test_cleanup_druid.sql");
        setHiveConfDir("data/conf/llap");
        setClusterType(MiniClusterType.DRUID);
        setFsType(QTestMiniClusters.FsType.HDFS);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class MiniDruidKafkaCliConfig extends AbstractCliConfig {
    public MiniDruidKafkaCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");
        includesFrom(testConfigProps, "druid.kafka.query.files");
        setResultsDir("ql/src/test/results/clientpositive/druid");
        setLogDir("itests/qtest/target/tmp/log");

        setInitScript("q_test_druid_init.sql");
        setCleanupScript("q_test_cleanup_druid.sql");
        setHiveConfDir("data/conf/llap");
        setClusterType(MiniClusterType.DRUID_KAFKA);
        setFsType(QTestMiniClusters.FsType.HDFS);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class MiniKafkaCliConfig extends AbstractCliConfig {
    public MiniKafkaCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");
        includesFrom(testConfigProps, "hive.kafka.query.files");
        setResultsDir("ql/src/test/results/clientpositive/kafka");
        setLogDir("itests/qtest/target/tmp/log");
        setHiveConfDir("data/conf/llap");
        setClusterType(MiniClusterType.KAFKA);
        setFsType(QTestMiniClusters.FsType.HDFS);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class MiniLlapLocalCliConfig extends AbstractCliConfig {

    public MiniLlapLocalCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");

        includesFrom(testConfigProps, "minillaplocal.query.files");
        includesFrom(testConfigProps, "minillaplocal.shared.query.files");
        excludeQuery("bucket_map_join_tez1.q"); // Disabled in HIVE-19509
        excludeQuery("special_character_in_tabnames_1.q"); // Disabled in HIVE-19509
        excludeQuery("tez_smb_1.q"); // Disabled in HIVE-19509
        excludeQuery("union_fast_stats.q"); // Disabled in HIVE-19509
        excludeQuery("schema_evol_orc_acidvec_part.q"); // Disabled in HIVE-19509
        excludeQuery("schema_evol_orc_vec_part_llap_io.q"); // Disabled in HIVE-19509
        excludeQuery("load_dyn_part3.q"); // Disabled in HIVE-20662. Enable in HIVE-20663.
        excludeQuery("cbo_limit.q"); //Disabled in HIVE-20860. Enable in HIVE-20972
        excludeQuery("rfc5424_parser_file_pruning.q"); // Disabled in HIVE-21427
        excludeQuery("cbo_rp_limit.q"); // Disabled in HIVE-21657

        setResultsDir("ql/src/test/results/clientpositive/llap");
        setLogDir("itests/qtest/target/qfile-results/clientpositive");

        setInitScript("q_test_init.sql");
        setCleanupScript("q_test_cleanup.sql");

        setHiveConfDir("data/conf/llap");
        setClusterType(MiniClusterType.LLAP_LOCAL);
        setFsType(QTestMiniClusters.FsType.LOCAL);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class EncryptedHDFSCliConfig extends AbstractCliConfig {
    public EncryptedHDFSCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");

        includesFrom(testConfigProps, "encrypted.query.files");

        setResultsDir("ql/src/test/results/clientpositive/encrypted");
        setLogDir("itests/qtest/target/qfile-results/clientpositive");

        setInitScript("q_test_init_src.sql");
        setCleanupScript("q_test_cleanup_src.sql");


        setClusterType(MiniClusterType.MR);
        setFsType(QTestMiniClusters.FsType.ENCRYPTED_HDFS);
        if (getClusterType() == MiniClusterType.TEZ) {
          setHiveConfDir("data/conf/tez");
        } else {
          setHiveConfDir("data/conf");
        }

      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class ContribCliConfig extends AbstractCliConfig {
    public ContribCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("contrib/src/test/queries/clientpositive");

        setResultsDir("contrib/src/test/results/clientpositive");
        setLogDir("itests/qtest/target/qfile-results/contribclientpositive");

        setInitScript("q_test_init_contrib.sql");
        setCleanupScript("q_test_cleanup_contrib.sql");

        setHiveConfDir("");
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class TezPerfCliConfig extends AbstractCliConfig {
    public TezPerfCliConfig(boolean useConstraints) {
      super(CorePerfCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive/perf");

        if (useConstraints) {
          excludesFrom(testConfigProps, "tez.perf.constraints.disabled.query.files");
        } else {
          excludesFrom(testConfigProps, "tez.perf.disabled.query.files");
        }

        excludesFrom(testConfigProps, "minimr.query.files");
        excludesFrom(testConfigProps, "minitez.query.files");
        excludesFrom(testConfigProps, "encrypted.query.files");
        excludesFrom(testConfigProps, "erasurecoding.only.query.files");

        excludeQuery("cbo_query44.q"); // TODO: Enable when we move to Calcite 1.18
        excludeQuery("cbo_query45.q"); // TODO: Enable when we move to Calcite 1.18
        excludeQuery("cbo_query67.q"); // TODO: Enable when we move to Calcite 1.18
        excludeQuery("cbo_query70.q"); // TODO: Enable when we move to Calcite 1.18
        excludeQuery("cbo_query86.q"); // TODO: Enable when we move to Calcite 1.18

        setLogDir("itests/qtest/target/qfile-results/clientpositive/tez");

        if (useConstraints) {
          setInitScript("q_perf_test_init_constraints.sql");
          setResultsDir("ql/src/test/results/clientpositive/perf/tez/constraints");
        } else {
          setInitScript("q_perf_test_init.sql");
          setResultsDir("ql/src/test/results/clientpositive/perf/tez");
        }
        setCleanupScript("q_perf_test_cleanup.sql");

        setHiveConfDir("data/conf/perf-reg/tez");
        setClusterType(MiniClusterType.TEZ);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class CompareCliConfig extends AbstractCliConfig {
    public CompareCliConfig() {
      super(CoreCompareCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientcompare");

        setResultsDir("ql/src/test/results/clientcompare");
        setLogDir("itests/qtest/target/qfile-results/clientcompare");

        setInitScript("q_test_init_compare.sql");
        setCleanupScript("q_test_cleanup_compare.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class NegativeCliConfig extends AbstractCliConfig {
    public NegativeCliConfig() {
      super(CoreNegativeCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientnegative");

        excludesFrom(testConfigProps, "minimr.query.negative.files");
        excludeQuery("authorization_uri_import.q");

        setResultsDir("ql/src/test/results/clientnegative");
        setLogDir("itests/qtest/target/qfile-results/clientnegative");

        setInitScript("q_test_init.sql");
        setCleanupScript("q_test_cleanup.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class NegativeMinimrCli extends AbstractCliConfig {
    public NegativeMinimrCli() {
      super(CoreNegativeCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientnegative");

        includesFrom(testConfigProps, "minimr.query.negative.files");

        setResultsDir("ql/src/test/results/clientnegative");
        setLogDir("itests/qtest/target/qfile-results/clientnegative");

        setInitScript("q_test_init_src.sql");
        setCleanupScript("q_test_cleanup_src.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.MR);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class HBaseCliConfig extends AbstractCliConfig {
    public HBaseCliConfig() {
      super(CoreHBaseCliDriver.class);
      try {
        setQueryDir("hbase-handler/src/test/queries/positive");

        setResultsDir("hbase-handler/src/test/results/positive");
        setLogDir("itests/qtest/target/qfile-results/hbase-handler/positive");

        setInitScript("q_test_init_src_with_stats.sql");
        setCleanupScript("q_test_cleanup_src.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class DummyConfig extends AbstractCliConfig {
    public DummyConfig() {
      super(CoreDummy.class);
      try {
        setQueryDir("ql/src/test/queries/clientcompare");

        setResultsDir("ql/src/test/results/clientcompare");
        setLogDir("itests/qtest/target/qfile-results/clientcompare");

        setInitScript("q_test_init_compare.sql");
        setCleanupScript("q_test_cleanup_compare.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class HBaseNegativeCliConfig extends AbstractCliConfig {
    public HBaseNegativeCliConfig() {
      super(CoreHBaseNegativeCliDriver.class);
      try {
        setQueryDir("hbase-handler/src/test/queries/negative");

        setResultsDir("hbase-handler/src/test/results/negative");
        setLogDir("itests/qtest/target/qfile-results/hbase-handler/negative");

        setInitScript("q_test_init_src.sql");
        setCleanupScript("q_test_cleanup_src.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class ContribNegativeCliConfig extends AbstractCliConfig {
    public ContribNegativeCliConfig() {
      super(CoreNegativeCliDriver.class);
      try {
        setQueryDir("contrib/src/test/queries/clientnegative");

        setResultsDir("contrib/src/test/results/clientnegative");
        setLogDir("itests/qtest/target/qfile-results/contribclientnegative");

        setInitScript("q_test_init.sql");
        setCleanupScript("q_test_cleanup.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class BeeLineConfig extends AbstractCliConfig {
    public BeeLineConfig() {
      super(CoreBeeLineDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");

        includesFrom(testConfigProps, "beeline.positive.include");

        setResultsDir("ql/src/test/results/clientpositive/beeline");
        setLogDir("itests/qtest/target/qfile-results/beelinepositive");

        setInitScript("q_test_init_src.sql");
        setCleanupScript("q_test_cleanup_src.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class AccumuloCliConfig extends AbstractCliConfig {
    public AccumuloCliConfig() {
      super(CoreAccumuloCliDriver.class);
      try {
        setQueryDir("accumulo-handler/src/test/queries/positive");
        excludeQuery("accumulo_joins.q");

        setResultsDir("accumulo-handler/src/test/results/positive");
        setLogDir("itests/qtest/target/qfile-results/accumulo-handler/positive");

        setInitScript("q_test_init_src_with_stats.sql");
        setCleanupScript("q_test_cleanup_src.sql");

        setHiveConfDir("");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class BlobstoreCliConfig extends AbstractCliConfig {
    public BlobstoreCliConfig() {
      super(CoreBlobstoreCliDriver.class);
      try {
        setQueryDir("itests/hive-blobstore/src/test/queries/clientpositive");

        setResultsDir("itests/hive-blobstore/src/test/results/clientpositive");
        setLogDir("itests/hive-blobstore/target/qfile-results/clientpositive");

        setInitScript("blobstore_test_init.q");
        setCleanupScript("blobstore_test_cleanup.q");

        setHiveConfDir("itests/hive-blobstore/src/test/resources");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class BlobstoreNegativeCliConfig extends AbstractCliConfig {
    public BlobstoreNegativeCliConfig() {
      super(CoreBlobstoreNegativeCliDriver.class);
      try {
        setQueryDir("itests/hive-blobstore/src/test/queries/clientnegative");

        setResultsDir("itests/hive-blobstore/src/test/results/clientnegative");
        setLogDir("itests/hive-blobstore/target/qfile-results/clientnegative");

        setInitScript("blobstore_test_init.q");
        setCleanupScript("blobstore_test_cleanup.q");

        setHiveConfDir("itests/hive-blobstore/src/test/resources");
        setClusterType(MiniClusterType.NONE);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  /**
   * Configuration for TestErasureCodingHDFSCliDriver.
   */
  public static class ErasureCodingHDFSCliConfig extends AbstractCliConfig {
    public ErasureCodingHDFSCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");

        includesFrom(testConfigProps, "erasurecoding.shared.query.files");
        includesFrom(testConfigProps, "erasurecoding.only.query.files");

        setResultsDir("ql/src/test/results/clientpositive/erasurecoding");
        setLogDir("itests/qtest/target/qfile-results/clientpositive");

        setInitScript("q_test_init_src.sql");
        setCleanupScript("q_test_cleanup_src.sql");

        setClusterType(MiniClusterType.MR);
        setFsType(QTestMiniClusters.FsType.ERASURE_CODED_HDFS);
        setHiveConfDir(getClusterType());
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }

    /**
     * Set the appropriate conf dir based on the cluster type.
     */
    private void setHiveConfDir(MiniClusterType clusterType) {
      switch (clusterType) {
      case TEZ:
        setHiveConfDir("data/conf/tez");
        break;
      default:
        setHiveConfDir("data/conf");
        break;
      }
    }
  }

  public static class MiniDruidLlapLocalCliConfig extends AbstractCliConfig {
    public MiniDruidLlapLocalCliConfig() {
      super(CoreCliDriver.class);
      try {
        setQueryDir("ql/src/test/queries/clientpositive");

        includesFrom(testConfigProps, "druid.llap.local.query.files");

        setResultsDir("ql/src/test/results/clientpositive/druid");
        setLogDir("itests/qtest/target/tmp/log");

        setInitScript("q_test_druid_init.sql");
        setCleanupScript("q_test_cleanup_druid.sql");
        setHiveConfDir("data/conf/llap");
        setClusterType(MiniClusterType.DRUID_LOCAL);
        setFsType(QTestMiniClusters.FsType.LOCAL);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  /**
   * The CliConfig implementation for Kudu.
   */
  public static class KuduCliConfig extends AbstractCliConfig {
    public KuduCliConfig() {
      super(CoreKuduCliDriver.class);
      try {
        setQueryDir("kudu-handler/src/test/queries/positive");

        setResultsDir("kudu-handler/src/test/results/positive");
        setLogDir("itests/qtest/target/qfile-results/kudu/positive");

        setInitScript("q_test_init_src.sql");
        setCleanupScript("q_test_cleanup_src.sql");

        setHiveConfDir("data/conf/llap");
        setClusterType(MiniClusterType.TEZ_LOCAL);
        setFsType(QTestMiniClusters.FsType.LOCAL);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }

  public static class KuduNegativeCliConfig extends AbstractCliConfig {
    public KuduNegativeCliConfig() {
      super(CoreKuduNegativeCliDriver.class);
      try {
        setQueryDir("kudu-handler/src/test/queries/negative");

        setResultsDir("kudu-handler/src/test/results/negative");
        setLogDir("itests/qtest/target/qfile-results/kudu/negative");

        setInitScript("q_test_init_src.sql");
        setCleanupScript("q_test_cleanup_src.sql");

        setHiveConfDir("data/conf/llap");
        setClusterType(MiniClusterType.TEZ_LOCAL);
        setFsType(QTestMiniClusters.FsType.LOCAL);
      } catch (Exception e) {
        throw new RuntimeException("can't construct cliconfig", e);
      }
    }
  }
}
