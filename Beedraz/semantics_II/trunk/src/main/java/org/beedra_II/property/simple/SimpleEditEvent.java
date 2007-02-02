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

package org.beedra_II.property.simple;


import static org.beedra.util_I.MultiLineToStringUtil.indent;
import static org.beedra_II.edit.Edit.State.DONE;

import org.beedra_II.event.AbstractEditEvent;
import org.beedra_II.event.Event;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * {@link Event} that carries a simple old and new value,
 * expressing the changed that occured in {@link #getSource()}.
 * The {@link #getSource() source} must be a {@link SimplePB}.
 *
 * @author Jan Dockx
 *
 * @invar getSource() instanceof IntegerBeed;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class SimpleEditEvent<_Type_>
    extends AbstractEditEvent<SimpleEdit<_Type_, ?>>
    implements OldNewEvent<_Type_> {

  /**
   * @pre source != null;
   * @post getSource() == sourcel
   * @post oldValue == null ? getOldValue() == null : getOldValue().equals(oldValue);
   * @post newValue == null ? getNewValue() == null : getNewValue().equals(newValue);
   */
  public SimpleEditEvent(SimpleEdit<_Type_, ?> edit) { // MUDO tyoe
    super(edit);
  }

  /**
   * @basic
   */
  public final _Type_ getOldValue() {
    return getEditState() == DONE ? getEdit().getInitial() : getEdit().getGoal();
  }

  /**
   * @basic
   */
  public final _Type_ getNewValue() {
    return getEditState() == DONE ? getEdit().getGoal() : getEdit().getInitial();
  }

  protected String otherToStringInformation() {
    return super.otherToStringInformation() +
           ", old value: " + getOldValue() +
           ", new value: " + getNewValue();
  }

  public void toString(StringBuffer sb, int level) {
    super.toString(sb, level);
    sb.append(indent(level + 1) + "old value: " + getOldValue() + "\n");
    sb.append(indent(level + 1) + "new value: " + getNewValue() + "\n");
  }

}

