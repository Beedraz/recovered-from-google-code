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

package org.beedra_II.property.date;


import java.util.Date;

import org.beedra_II.property.EditableSimpleExpressionBeed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * An editable beed containing a {@link Date} value.
 * Listeners of the beed can receive events of type
 * {@link DateEvent}.
 *
 * @author  Nele Smeets
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class EditableDateBeed
    extends EditableSimpleExpressionBeed<Date, DateEvent>
    implements DateBeed {

  /**
   * @pre   owner != null;
   * @post  getOwner() == owner;
   */
  public EditableDateBeed() {
    super(null);
  }

  @Override
  protected Date safeValueCopy(Date original) {
    return original != null ? (Date) original.clone() : null;
  }

}

