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

package org.beedra_II.property.number.integer.long64;


import static org.ppeew.smallfries_I.MathUtil.castToDouble;

import org.beedra_II.aggregate.AggregateBeed;
import org.beedra_II.edit.Edit;
import org.beedra_II.property.number.AbstractUnaryExpressionBeed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * <p>General code for Intger implementations of {@link AbstractUnaryExpressionBeed}.</p>
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractLongUnaryExpressionBeed
    extends AbstractUnaryExpressionBeed<Integer, LongBeed, LongEvent>
    implements LongBeed {

  /**
   * @pre   owner != null;
   * @post  getInteger() == null;
   * @post  getArgument() == null;
   */
  public AbstractLongUnaryExpressionBeed(AggregateBeed owner) {
    super(owner);
  }

  public final Double getDouble() {
    return castToDouble(getInteger());
  }

  public final Integer getInteger() {
    return get();
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
  protected final LongEvent createInitialEvent() {
    return new ActualLongEvent(this, null, getInteger(), null);
  }

  @Override
  protected final LongEvent createNewEvent(Integer oldValue, Integer newValue, Edit<?> edit) {
    return new ActualLongEvent(this, oldValue, newValue, edit);
  }

  @Override
  protected final Integer newValueFrom(LongEvent event) {
    return event.getNewInteger();
  }

  @Override
  protected final Integer valueFrom(LongBeed beed) {
    return beed.getInteger();
  }

}

