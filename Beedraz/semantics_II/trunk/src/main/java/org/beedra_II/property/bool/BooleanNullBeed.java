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

package org.beedra_II.property.bool;


import org.beedra_II.aggregate.AggregateBeed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A beed that expresses whether the given beed is null or not.
 *
 * @invar getArgument() != null && !getArgument().isEffective()
 *          ? get() == true
 *          : get() == false;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class BooleanNullBeed extends AbstractRealArgBooleanUnaryExpressionBeed {

  /**
   * @pre   owner != null;
   * @post  getOwner() == owner;
   * @post  getArgument() == null;
   * @post  get() == null;
   */
  public BooleanNullBeed(AggregateBeed owner) {
    super(owner);
  }


  /**
   * @pre $argument != null;
   */
  @Override
  protected void recalculate() {
    assignEffective(true);
    setValue(!getArgument().isEffective());
  }

  /**
   * The operator of this binary expression.
   */
  public String getOperatorString() {
    return "null";
  }

  @Override
  protected boolean calculateValue(double argument) {
    throw new IllegalStateException();
  }
}
