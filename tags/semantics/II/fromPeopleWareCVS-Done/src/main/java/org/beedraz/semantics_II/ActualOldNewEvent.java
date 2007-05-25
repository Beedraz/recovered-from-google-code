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


import static org.ppeew.smallfries_I.MultiLineToStringUtil.indent;

import org.beedraz.semantics_II.edit.Edit;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * {@link Event} that carries a simple old and new value,
 * expressing the changed that occured in {@link #getSource()}.
 * The {@link #getSource() source} must be a {@link SimplePB}.
 *
 * @author Jan Dockx
 *
 * @invar (getOldValue() != null) && (getNewValue() != null) ? ! getOldValue().equals(getNewValue());
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ActualOldNewEvent<_Type_>
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
    $oldValue = oldValue;
    $newValue = newValue;
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
