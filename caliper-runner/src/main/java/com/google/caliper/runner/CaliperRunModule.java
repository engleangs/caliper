/*
 * Copyright (C) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.caliper.runner;

import com.google.caliper.core.BenchmarkClassModel;
import com.google.caliper.model.Host;
import com.google.caliper.runner.experiment.BenchmarkParameters;
import com.google.caliper.runner.options.CaliperOptions;
import com.google.caliper.runner.target.Target;
import com.google.caliper.runner.worker.dryrun.DryRunComponent;
import com.google.caliper.runner.worker.targetinfo.TargetInfo;
import com.google.caliper.runner.worker.trial.TrialComponent;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import dagger.Module;
import dagger.Provides;

/** Configures the {@link CaliperRun}. */
@Module(subcomponents = {DryRunComponent.class, TrialComponent.class})
abstract class CaliperRunModule {
  private CaliperRunModule() {}

  @Provides
  static CaliperRun provideCaliperRun(ExperimentingCaliperRun experimentingCaliperRun) {
    return experimentingCaliperRun;
  }

  @Provides
  @BenchmarkParameters
  static ImmutableSetMultimap<String, String> provideBenchmarkParameters(
      BenchmarkClassModel benchmarkClass, CaliperOptions options) {
    return benchmarkClass.fillInDefaultParameterValues(options.userParameters());
  }

  @Provides
  static BenchmarkClassModel provideBenchmarkClassModel(TargetInfo targetInfo) {
    return targetInfo.benchmarkClassModel();
  }

  @Provides
  static ImmutableMap<Target, Host> provideTargetHosts(TargetInfo targetInfo) {
    return targetInfo.hosts();
  }
}
