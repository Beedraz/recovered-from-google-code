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

package org.beedra_II.expression.number.real.double64;


import static org.ppeew.smallfries_I.MathUtil.castToBigDecimal;

import java.math.BigDecimal;

import org.beedra_II.edit.Edit;
import org.beedra_II.expression.number.AbstractRealArgUnaryExpressionBeed;
import org.beedra_II.expression.number.real.RealBeed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * <p>General code for Double implementations of {@link AbstractRealArgUnaryExpressionBeed}.</p>
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractRealArgDoubleUnaryExpressionBeed
    extends AbstractRealArgUnaryExpressionBeed<Double, ActualDoubleEvent, RealBeed<?>>
    implements DoubleBeed {

  /**
   * @post  getDouble() == null;
   * @post  getOperand() == null;
   */
  protected AbstractRealArgDoubleUnaryExpressionBeed() {
    super();
  }

  public final double getdouble() {
    return $value;
  }

  private double $value;

  @Override
  protected final ActualDoubleEvent createNewEvent(Double oldValue, Double newValue, Edit<?> edit) {
    return new ActualDoubleEvent(this, oldValue, newValue, edit);
  }

  public final BigDecimal getBigDecimal() {
    return castToBigDecimal(getDouble());
  }

  @Override
  protected final void recalculateFrom(RealBeed<?> operand) {
    $value = calculateValue(operand.getdouble());
  }

  protected abstract double calculateValue(double operand);

  @Override
  public final Double get() {
    return getDouble();
  }

}
