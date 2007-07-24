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


import org.beedra_II.event.Event;
import org.beedra_II.property.simple.OldNewEvent;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * {@link OldNewEvent} whose source is a {@link DoubleBeed} and
 * that carries a simple old and new value of type {@link Double}.
 *
 * @author  Nele Smeets
 *
 * @invar getSource() instanceof DoubleBeed;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface DoubleEvent extends Event {

  /**
   * @basic
   */
  Double getOldDouble();

  /**
   * @basic
   */
  Double getNewDouble();

  /**
   * @return (getOldValue() == null) || (getNewValue() == null)
   *             ? null
   *             : getNewValue() - getOldValue();
   */
  Double getDoubleDelta();

}
