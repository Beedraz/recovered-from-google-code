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

package org.beedra_II.topologicalupdate;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.beedra_II.event.Event;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * @author Jan Dockx
 *
 * @invar getMaximumRootUpdateSourceDistance() == 0;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class RootUpdateSource<_Event_ extends Event>
    extends AbstractUpdateSource {

  public final int getMaximumRootUpdateSourceDistance() {
    return 0;
  }

  protected final void updateDependents(_Event_ event) {
    Map<UpdateSource, Event> events = new HashMap<UpdateSource, Event>();
    events.put(this, event);
    PriorityQueue<Dependent<?>> queue =
      new PriorityQueue<Dependent<?>>(getDependents().size(),
        new Comparator<Dependent<?>>() {
              public int compare(Dependent<?> d1, Dependent<?> d2) {
                assert d1 != null;
                assert d2 != null;
                int mfsd1 = d1.getDependentUpdateSource().getMaximumRootUpdateSourceDistance();
                int mfsd2 = d2.getDependentUpdateSource().getMaximumRootUpdateSourceDistance();
                return (mfsd1 < mfsd2) ? -1 : ((mfsd1 == mfsd2) ? 0 : +1);
              }
            });
    queue.addAll(getDependents());
    Dependent<?> dependent = queue.poll();
    while (dependent != null) {
      Event dependentEvent = dependent.update(Collections.unmodifiableMap(events));
      if (dependentEvent != null) {
        events.put(dependent.getDependentUpdateSource(), dependentEvent);
        Set<Dependent<?>> secondDependents =
          new HashSet<Dependent<?>>(dependent.getDependentUpdateSource().getDependents());
        secondDependents.removeAll(queue);
        queue.addAll(secondDependents);
      }
      dependent = queue.poll();
    }
  }

}
