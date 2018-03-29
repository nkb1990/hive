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
package org.apache.hive.testutils.dtest;

import java.util.List;

public interface ResultAnalyzer {

  /**
   * Analyze a log
   * @param name the name of the container
   * @param log the log produced
   */
  void analyzeLog(String name, String log);

  /**
   * Get count of succeeded tests.
   * @return number of tests that succeeded.
   */
  int getSucceeded();

  /**
   * Get list of tests that failed.
   * @return name of each test that failed.
   */
  List<String> getFailed();

  /**
   * Get list of tests that ended in error.
   * @return name of each test that produced an error.
   */
  List<String> getErrors();

}
