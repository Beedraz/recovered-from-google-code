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

package org.beedraz.semantics_II.expression.number.real.double64.stat;


import org.apache.commons.math.stat.descriptive.rank.Max;
import org.beedraz.semantics_II.expression.number.real.double64.DoubleBeed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A beed that computes the maximum of a given set of beeds of type
 * {@link DoubleBeed}.
 *
 * @invar getSource() != null ==>
 *        (forAll DoubleBeed db; getSource().get().contains(db); db.getDouble() != null)
 *            ==> getDouble() == max { db.getDouble() | getSource().get().contains(db)};
 *        If the values of all beeds in the given set are effective,
 *        then the value of the maximum beed is the maximum of
 *        the values of all beeds in the given set. The maximum of an empty set is NaN.
 *        e.g. when  getSource() = {1, 2, 3, 4}
 *             then  getDouble() = 4
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class DoubleMaxBeed extends AbstractDoubleCommonsMathSetComputationBeed {

  /**
   * @post  getSource() == null;
   * @post  getDouble() == null;
   */
  public DoubleMaxBeed() {
    super(new Max());
  }

  @Override
  public final String getOperatorString() {
    return "max";
  }

}
