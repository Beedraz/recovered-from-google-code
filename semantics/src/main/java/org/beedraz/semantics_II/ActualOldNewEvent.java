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

package org.beedraz.semantics_II;


import static org.ppeew.annotations_I.License.Type.APACHE_V2;
import static org.ppeew.smallfries_I.MultiLineToStringUtil.indent;
import static org.beedraz.semantics_II.CannotCombineEventsException.Reason.INCOMPATIBLE_STATES;

import org.ppeew.annotations_I.Copyright;
import org.ppeew.annotations_I.License;
import org.ppeew.annotations_I.vcs.SvnInfo;
import org.ppeew.smallfries_I.ComparisonUtil;


/**
 * {@link Event} that carries a simple old and new value,
 * expressing the changed that occured in {@link #getSource()}.
 * The {@link #getSource() source} must be a {@link SimplePB}.
 *
 * @author Jan Dockx
 *
 * @invar (getOldValue() != null) && (getNewValue() != null) ? ! getOldValue().equals(getNewValue());
 *
 * @mudo rename to AbstractOldNewEvent or OldNewEvent
 */
@Copyright("2007 - $Date$, Beedraz authors")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision$",
         date     = "$Date$")
public abstract class ActualOldNewEvent<_Type_>
    extends AbstractEvent
    implements OldNewEvent<_Type_> {

  /**
   * @pre  source != null;
   * @pre  (edit != null) ? (edit.getState() == DONE) || (edit.getState() == UNDONE);
   * @pre  (oldValue != null) && (newValue != null)
   *          ? ! oldValue.equals(newValue)
   *          : true;
   *
   * @post getSource() == source;
   * @post getEdit() == edit;
   * @post (edit != null) ? getEditState() == edit.getState() : getEditState() == null;
   * @post oldValue == null ? getOldValue() == null : getOldValue().equals(oldValue);
   * @post newValue == null ? getNewValue() == null : getNewValue().equals(newValue);
   */
  public ActualOldNewEvent(Beed<?> source,
                           _Type_ oldValue,
                           _Type_ newValue,
                           Edit<?> edit) {
    super(source, edit);
    assert ((oldValue != null) && (newValue != null) ? ! oldValue.equals(newValue) : true) :
           "oldValue: " + oldValue + "; newValue: " + newValue;
    $oldValue = oldValue; // MUDO make a type safe copy
    $newValue = newValue; // MUDO make a type safe copy
  }

  /**
   * @basic
   */
  public final _Type_ getOldValue() {
    return $oldValue;
  }

  private final _Type_ $oldValue;

  /**
   * @basic
   */
  public final _Type_ getNewValue() {
    return $newValue;
  }

  private final _Type_ $newValue;

  @Override
  protected String otherToStringInformation() {
    return super.otherToStringInformation() +
           ", old value: " + getOldValue() +
           ", new value: " + getNewValue();
  }

  /**
   * @mudo needs unit test
   */
  @Override
  protected final ActualOldNewEvent<_Type_> createCombinedEvent(Event other, CompoundEdit<?, ?> edit)
      throws CannotCombineEventsException {
    ActualOldNewEvent<_Type_> otherAONE = (ActualOldNewEvent<_Type_>)other;
    if (! ComparisonUtil.equalsWithNull($newValue, otherAONE.getOldValue())) {
      throw new CannotCombineEventsException(this, otherAONE, INCOMPATIBLE_STATES);
    }
    return safeCreateCombinedEvent(otherAONE, edit);
  }

  /**
   * @pre other != null;
   * @pre edit != null;
   * @pre getEdit() != null;
   * @pre other.getEdit() != null;
   * @pre edit.deepContains(this.getEdit());
   * @pre edit.deepContains(other.getEdit());
   * @pre getClass() == other.getClass();
   * @pre getSource() == other.getSource();
   * @pre isNewStateCompatibleWithOldState(other);
   * @post result.getClass() == getClass();
   * @post result.getSource() == getSource();
   * @post result.getEdit() == edit;
   * @post result.getEditState() == edit.getState();
   * @post ; result initial state is this initial state
   * @post ; result goal state is {@code other} initial state
   */
  protected abstract ActualOldNewEvent<_Type_> safeCreateCombinedEvent(ActualOldNewEvent<_Type_> other, CompoundEdit<?, ?> compoundEdit);


  @Override
  public void toString(StringBuffer sb, int level) {
    super.toString(sb, level);
    toStringOldNew(sb, level + 1);
  }

  protected void toStringOldNew(StringBuffer sb, int level) {
    sb.append(indent(level + 1) + "old value: " + getOldValue() + "\n");
    sb.append(indent(level + 1) + "new value: " + getNewValue() + "\n");
  }

}

