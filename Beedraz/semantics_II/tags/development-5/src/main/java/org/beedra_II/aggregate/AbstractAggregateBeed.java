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

package org.beedra_II.aggregate;


import org.beedra_II.AbstractBeed;
import org.beedra_II.Beed;
import org.beedra_II.aggregate.PropagatedEvent;
import org.beedra_II.event.Event;
import org.beedra_II.event.Listener;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Support for implementations of {@link BeanBeed}.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractAggregateBeed
    extends AbstractBeed<PropagatedEvent>
    implements AggregateBeed {

  private final Listener<Event<?>> $propagationListener = new Listener<Event<?>>() {

    public void beedChanged(Event<?> event) {
      fireChangeEvent(new PropagatedEvent(AbstractAggregateBeed.this, event));
    }

  };

  public final boolean isAggregateElement(Beed<?> b) {
    return (b != null) && b.isListener($propagationListener);
  }

  public final void registerAggregateElement(Beed<?> b) {
    assert b != null;
    b.addListener($propagationListener);
  }

}
