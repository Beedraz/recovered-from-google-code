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


import static org.ppeew.smallfries_I.MathUtil.castToBigDecimal;

import java.math.BigDecimal;

import org.beedra_II.aggregate.AggregateBeed;
import org.beedra_II.property.number.real.RealEvent;
import org.beedra_II.property.simple.EditableSimplePropertyBeed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A editable beed containing a {@link Double} value.
 * Listeners of the beed can receive events of type
 * {@link RealEvent}.
 *
 * @author  Nele Smeets
 *
 * @mudo break link with editable simple property beed and use double instead of Double internally.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class EditableDoubleBeed
    extends EditableSimplePropertyBeed<Double, ActualDoubleEvent>
    implements DoubleBeed {

  /**
   * @pre   owner != null;
   * @post  getOwner() == owner;
   */
  public EditableDoubleBeed(AggregateBeed owner) {
    super(owner);
  }

  /**
   * @post  result != null;
   * @post  result.getSource() == this;
   * @post  result.getOldDouble() == null;
   * @post  result.getNewDouble() == get();
   * @post  result.getEdit() == null;
   * @post  result.getEditState() == null;
   */
  @Override
  protected ActualDoubleEvent createInitialEvent() {
    return new ActualDoubleEvent(this, null, get(), null);
  }

  /**
   * @return get();
   */
  public final Double getDouble() {
    return get();
  }

  public final BigDecimal getBigDecimal() {
    return castToBigDecimal(getDouble());
  }

  public double getdouble() {
    return get().doubleValue();
  }

  public boolean isEffective() {
    return get() != null;
  }

}
