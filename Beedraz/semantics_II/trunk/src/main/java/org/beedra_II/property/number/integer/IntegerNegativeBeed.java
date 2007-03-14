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

package org.beedra_II.property.number.integer;


import static org.ppeew.smallfries_I.MathUtil.castToDouble;

import org.beedra_II.aggregate.AggregateBeed;
import org.beedra_II.edit.Edit;
import org.beedra_II.property.integer.ActualIntegerEvent;
import org.beedra_II.property.integer.IntegerBeed;
import org.beedra_II.property.integer.IntegerEvent;
import org.beedra_II.property.number.AbstractNegativeBeed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A beed that is the negative of a {@link #getArgument() argument} {@link IntegerBeed}.
 *
 * @invar getArgument() == null ? getInteger() == null;
 * @invar getArgument() != null ? getInteger() == - getArgument().getInteger();
 *
 * @mudo overflow: -MIN_VALUE == MIN_VALUE
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class IntegerNegativeBeed
    extends AbstractNegativeBeed<Integer, IntegerBeed, IntegerEvent>
    implements IntegerBeed {

  /**
   * @pre   owner != null;
   * @post  getInteger() == null;
   * @post  getArgument() == null;
   */
  public IntegerNegativeBeed(AggregateBeed owner) {
    super(owner);
  }

  public final Double getDouble() {
    return castToDouble(getInteger());
  }

  public final Integer getInteger() {
    return get();
  }

  /**
   * @pre argumentValue != null;
   */
  @Override
  protected Integer calculateValue(Integer argumentValue) {
    assert argumentValue != null;
    return -argumentValue;
  }

  /**
   * @post  result != null;
   * @post  result.getArgument() == this;
   * @post  result.getOldInteger() == null;
   * @post  result.getNewInteger() == getInteger();
   * @post  result.getEdit() == null;
   * @post  result.getEditState() == null;
   */
  @Override
  protected final IntegerEvent createInitialEvent() {
    return new ActualIntegerEvent(this, null, getInteger(), null);
  }

  @Override
  protected IntegerEvent createNewEvent(Integer oldValue, Integer newValue, Edit<?> edit) {
    return new ActualIntegerEvent(this, oldValue, newValue, edit);
  }

  @Override
  protected Integer newValueFrom(IntegerEvent event) {
    return event.getNewInteger();
  }

  @Override
  protected Integer valueFrom(IntegerBeed beed) {
    return beed.getInteger();
  }

}
