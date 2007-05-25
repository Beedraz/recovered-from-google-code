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


import org.apache.commons.math.stat.descriptive.moment.GeometricMean;
import org.beedra_II.aggregate.AggregateBeed;
import org.beedra_II.property.collection.set.SetBeed;
import org.beedra_II.property.number.real.RealBeed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A beed that computes the geometric mean of a given set of beeds of type
 * {@link DoubleBeed}.
 *
 * @invar getSource() != null ==>
 *        (forAll DoubleBeed db; getSource().get().contains(db); db.getDouble() != null)
 *            ==> getDouble() == geometric-mean { db.getDouble() | getSource().get().contains(db)};
 *        If the values of all beeds in the given set are effective,
 *        then the value of the geometric mean beed is the geometric mean of
 *        the values of all beeds in the given set. The mean of an empty set is NaN.
 *        e.g. getDouble() = (5.1 * 3.2 * 4.9) ^ (1/3)
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class DoubleGeometricMeanBeed extends AbstractDoubleSetComputationBeed {


  /**
   * @pre   owner != null;
   * @post  getSource() == null;
   * @post  getDouble() == null;
   */
  public DoubleGeometricMeanBeed(AggregateBeed owner) {
    super(owner);
  }

  /**
   * The value of this beed is recalculated.
   */
  @Override
  protected final void recalculate(SetBeed<RealBeed<?>, ?> source) {
    $calculator.clear();
    for (RealBeed<?> realBeed : source.get()) {
      if (! realBeed.isEffective()) {
        assignEffective(false);
        return;
      }
      $calculator.increment(realBeed.getdouble());
    }
    assignValue($calculator.getResult());
    assignEffective(true);
  }

  private final GeometricMean $calculator = new GeometricMean();

}
