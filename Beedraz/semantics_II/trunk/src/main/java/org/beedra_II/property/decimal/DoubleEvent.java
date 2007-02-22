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

package org.beedra_II.property.decimal;


import static org.beedra.util_I.MultiLineToStringUtil.indent;

import org.beedra_II.property.simple.OldNewEvent;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * {@link OldNewEvent} whose source is a {@link DoubleBeed},
 * that carries a simple old and new value of type {@link Double},
 * and that references an edit of type {@link DoubleEdit}.
 *
 * @author  Nele Smeets
 *
 * @invar getSource() instanceof DoubleBeed;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public final class DoubleEvent extends OldNewEvent<Double, DoubleEdit> {

  /**
   * @pre  source != null;
   * @pre  edit != null;
   * @post getSource() == source;
   * @post oldValue == null ? getOldValue() == null : getOldValue().equals(oldValue);
   * @post newValue == null ? getNewValue() == null : getNewValue().equals(newValue);
   * @post getEdit() == edit;
   * @post getEditState() == edit.getState();
   * @post oldValue == null || newValue == null ? getDelta() == null : getDelta() = newValue - oldValue;
   */
  public DoubleEvent(DoubleBeed source, Double oldValue, Double newValue, DoubleEdit edit) {
    super(source, oldValue, newValue, edit);
    $delta = ((oldValue == null) || (newValue == null)) ? null : newValue - oldValue; // MUDO overflow
  }

  /**
   * @return (getOldValue() == null) || (getNewValue() == null)
   *             ? null
   *             : getNewValue() - getOldValue();
   */
  public final Double getDelta() {
    return $delta;
  }

  private final Double $delta;

  @Override
  protected String otherToStringInformation() {
    return super.otherToStringInformation() +
           ", delta: " + getDelta();
  }

  @Override
  public void toString(StringBuffer sb, int level) {
    super.toString(sb, level);
    sb.append(indent(level + 1) + "delta:" + getDelta() + "\n");
  }

}

