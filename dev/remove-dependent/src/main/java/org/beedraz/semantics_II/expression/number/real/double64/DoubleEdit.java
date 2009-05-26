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

package org.beedraz.semantics_II.expression.number.real.double64;


import static org.ppwcode.metainfo_I.License.Type.APACHE_V2;

import java.util.Map;

import org.beedraz.semantics_II.AbstractBeed;
import org.beedraz.semantics_II.expression.SimpleExpressionEdit;
import org.ppwcode.metainfo_I.Copyright;
import org.ppwcode.metainfo_I.License;
import org.ppwcode.metainfo_I.vcs.SvnInfo;


/**
 * An {@link org.beedraz.semantics_II.Edit} for a beed of type {@link EditableDoubleBeed}.
 * This edit can change the value of the target beed, and send
 * {@link ActualDoubleEvent events} to the listeners of that beed.
 *
 * @author  Nele Smeets
 */
@Copyright("2007 - $Date$, Beedraz authors")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision$",
         date     = "$Date$")
public final class DoubleEdit
    extends SimpleExpressionEdit<Double, EditableDoubleBeed, ActualDoubleEvent> {

  /**
   * @pre  target != null;
   * @post getTarget() == target;
   */
  public DoubleEdit(EditableDoubleBeed target) {
    super(target);
  }

  /**
   * @post  result.size() == 1;
   * @post  result.get(getTarget()) = event;
   * @post  result.get(getTarget()).getSource() == getTarget();
   * @post  getOldValue() == null ? result.get(getTarget()).getOldValue() == null :
   *                                result.get(getTarget()).getOldValue().equals(getOldValue());
   * @post  getNewValue() == null ? result.get(getTarget()).getNewValue() == null :
   *                                result.get(getTarget()).getNewValue().equals(getNewValue());
   * @post  result.get(getTarget()).getEdit() == this;
   */
  @Override
  protected Map<AbstractBeed<?>, ActualDoubleEvent> createEvents() {
    return singletonEventMap(new ActualDoubleEvent(getTarget(), getOldValue(), getNewValue(), this));
  }

}


