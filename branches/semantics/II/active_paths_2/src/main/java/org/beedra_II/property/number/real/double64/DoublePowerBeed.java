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

package org.beedra_II.property.number.real.double64;


import org.beedra_II.aggregate.AggregateBeed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A beed that is the {@link #getConstant()}-power of an
 * {@link #getArgument() argument} {@link DoubleBeed}.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class DoublePowerBeed
    extends AbstractDoubleConstantUnaryExpressionBeed {

  /**
   * @pre   owner != null;
   * @post  getDouble() == null;
   * @post  getArgument() == null;
   * @post  getConstant() == constant;
   */
  public DoublePowerBeed(AggregateBeed owner, double constant) {
    super(owner, constant);
  }

  /**
   * @pre argumentValue != null;
   */
  @Override
  protected final double calculateValue(double argumentValue) {
    return Math.pow(argumentValue, getConstant());
  }

  @Override
  public final String getOperatorString() {
    return "^" + getConstant();
  }

}
