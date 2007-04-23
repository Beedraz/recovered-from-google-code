/*<license>
 Copyright 2007 - $Date$ by the authors mentioned below.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 </license>*/

package org.beedra_II.expression.number.real.double64.stat;


import org.beedra_II.Event;
import org.beedra_II.expression.number.real.double64.stat.DoubleSampleStandardDeviationBeed;
import org.junit.Test;
import org.ppeew.smallfries_I.MathUtil;


public class TestDoubleSampleStandardDeviationBeed
    extends AbstractTestDoubleCommonsMathSetComputationBeed<MyDoubleSampleStandardDeviationBeed>{

  @Test
  public void forceTest() {
    // to let the compiler know that this is a unit test
  }

  @Override
  protected double computeStatistic(double... values) {
    return MathUtil.sampleStandardDeviation(values);
  }

  @Override
  protected MyDoubleSampleStandardDeviationBeed createSubject() {
    return new MyDoubleSampleStandardDeviationBeed();
  }

  @Override
  protected void recalculate(MyDoubleSampleStandardDeviationBeed subject) {
    subject.publicRecalculate();
  }

  @Override
  protected void updateDependents(MyDoubleSampleStandardDeviationBeed subject, Event event) {
    subject.publicUpdateDependents(event);
  }

}

class MyDoubleSampleStandardDeviationBeed extends DoubleSampleStandardDeviationBeed {
  public MyDoubleSampleStandardDeviationBeed() {
    super();
  }

  /**
   * updateDependents is made public for testing reasons
   */
  public void publicUpdateDependents(Event event) {
    updateDependents(event);
  }

  public final void publicRecalculate() {
    assert getSource() != null;
    recalculate(getSource());
  }

}