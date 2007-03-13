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


import org.beedra_II.aggregate.AggregateBeed;
import org.beedra_II.property.simple.EditableSimplePropertyBeed;
import org.ppeew.annotations.vcs.CvsInfo;


/**
 * A editable beed containing a {@link Double} value.
 * Listeners of the beed can receive events of type
 * {@link DoubleEvent}.
 *
 * @author  Nele Smeets
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class EditableDoubleBeed
    extends EditableSimplePropertyBeed<Double, DoubleEvent>
    implements DoubleBeed<DoubleEvent> {

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
  protected DoubleEvent createInitialEvent() {
    return new ActualDoubleEvent(this, null, get(), null);
  }

  /**
   * @return get();
   */
  public final Double getDouble() {
    return get();
  }

}

