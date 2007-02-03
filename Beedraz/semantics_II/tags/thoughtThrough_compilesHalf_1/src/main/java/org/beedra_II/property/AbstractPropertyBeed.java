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

package org.beedra_II.property;


import org.beedra_II.AbstractBeed;
import org.beedra_II.Beed;
import org.beedra_II.event.Event;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Support for implementations of {@link PropertyBeed}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractPropertyBeed<_EventSource_ extends PropertyBeed<_EventSource_, _Event_, _Owner_>,
                                           _Event_ extends Event<_EventSource_, _Event_>,
                                           _Owner_ extends Beed<?, ?>>
    extends AbstractBeed<_EventSource_, _Event_>
    implements PropertyBeed<_EventSource_, _Event_, _Owner_> {

  /**
   * @pre ownerBeed != null;
   */
  protected AbstractPropertyBeed(_Owner_ ownerBeed) {
    assert ownerBeed != null;
    $ownerBeed = ownerBeed;
  }

  public _Owner_ getOwnerBeed() {
    return $ownerBeed;
  }

  /**
   * @invar $ownerBeed != null;
   */
  private final _Owner_ $ownerBeed;

 }