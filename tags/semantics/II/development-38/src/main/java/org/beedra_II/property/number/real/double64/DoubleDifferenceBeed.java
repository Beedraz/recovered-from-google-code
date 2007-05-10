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
import org.beedra_II.property.number.real.RealBeed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A beed that is the difference of a {@link #getLeftArgument()}
 * and a {@link #getRightArgument()}.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class DoubleDifferenceBeed extends AbstractDoubleBinaryExpressionBeed {

  /**
   * @pre   owner != null;
   * @post  getDouble() == null;
   * @post  getFirstArgument() == null;
   * @post  getSecondArgument() == null;
   */
  public DoubleDifferenceBeed(AggregateBeed owner) {
    super(owner);
  }

  /**
   * @basic
   */
  public final RealBeed<?> getFirstArgument() {
    return getLeftArgument();
  }

  /**
   * @post getFirstArgument() == firstArgument;
   */
  public final void setFirstArgument(RealBeed<?> firstArgument) {
    setLeftArgument(firstArgument);
  }

  /**
   * @basic
   */
  public final RealBeed<?> getSecondArgument() {
    return getRightArgument();
  }

  /**
   * @post getSecondArgument() == secondArgument;
   */
  public final void setSecondArgument(RealBeed<?> secondArgument) {
    setRightArgument(secondArgument);
  }
  /**
   * @pre leftArgument != null;
   * @pre rightArgument != null;
   */
  @Override
  protected final double calculateValue(double firstArgument, double secondArgument) {
    return firstArgument - secondArgument;
  }

  @Override
  public final String getOperatorString() {
    return "-";
  }

}
