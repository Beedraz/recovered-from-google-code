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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.beedra_II.event.Event;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * @author Jan Dockx
 *
 * @invar getMaximumRootUpdateSourceDistance() >= 0;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractUpdateSource<_Event_ extends Event>
    implements UpdateSource {

  public final boolean isDependent(Dependent<?> dependent) {
    return $dependents.contains(dependent);
  }

  public final boolean isTransitiveDependent(Dependent<?> dependent) {
    if (isDependent(dependent)) {
      return true;
    }
    else {
      for (Dependent<?> d : $dependents) {
        if (d.getDependentUpdateSource().isTransitiveDependent(dependent)) {
          return true;
        }
      }
      return false;
    }
  }

  /**
   * Don't expose the collection of dependents publicly. It's
   * a secret shared between us and the dependent.
   *
   * @result for (Dependent d) {isDependent(d) ?? result.contains(d)};
   */
  protected final Set<Dependent<?>> getDependents() {
    return Collections.unmodifiableSet($dependents);
  }

  public final void addDependent(Dependent<?> dependent) {
    assert dependent != null;
    assert ! isTransitiveDependent(dependent);
    assert dependent.getMaximumRootUpdateSourceDistance() > getMaximumRootUpdateSourceDistance();
    $dependents.add(dependent);
  }

  public final void removeDependent(Dependent<?> dependent) {
    $dependents.remove(dependent);
  }

  /**
   * The topological update method. First change this update source locally,
   * then described the change in an event, then call this method with that event.
   *
   * @pre event != null;
   */
  public final void updateDependents(_Event_ event) {
    Map<UpdateSource, Event> events = new LinkedHashMap<UpdateSource, Event>();
    Map<Dependent<?>, Event> dependentEvents = new LinkedHashMap<Dependent<?>, Event>();
    // LinkedHashMap to remember topol order for event listener notification
    events.put(this, event);
    if ($dependents.size() >= 1) {
      assert $dependents.size() >= 1 : "initial size of priority queue must be >= 1";
      PriorityQueue<Dependent<?>> queue =
        new PriorityQueue<Dependent<?>>($dependents.size(),
          new Comparator<Dependent<?>>() {
                public int compare(Dependent<?> d1, Dependent<?> d2) {
                  assert d1 != null;
                  assert d2 != null;
                  int mfsd1 = d1.getDependentUpdateSource().getMaximumRootUpdateSourceDistance();
                  int mfsd2 = d2.getDependentUpdateSource().getMaximumRootUpdateSourceDistance();
                  return (mfsd1 < mfsd2) ? -1 : ((mfsd1 == mfsd2) ? 0 : +1);
                }
              });
      queue.addAll($dependents);
      Dependent<?> dependent = queue.poll();
      while (dependent != null) {
        Event dependentEvent = dependent.update(Collections.unmodifiableMap(events));
        if (dependentEvent != null) {
          events.put(dependent.getDependentUpdateSource(), dependentEvent);
          dependentEvents.put(dependent, dependentEvent);
          Set<Dependent<?>> secondDependents =
            new HashSet<Dependent<?>>(dependent.getDependents());
          secondDependents.removeAll(queue);
          queue.addAll(secondDependents);
        }
        dependent = queue.poll();
      }
    }
    fireEvent(event);
    for (Map.Entry<Dependent<?>, Event> entry : dependentEvents.entrySet()) {
      entry.getKey().fireEvent(entry.getValue());
    }
  }

  /**
   * Fire the event to regular (non-topological) listeners.
   */
  protected abstract void fireEvent(_Event_ event);

  /**
   * @invar $dependents != null;
   * @invar Collections.noNull($dependents);
   */
  private final Set<Dependent<?>> $dependents = new HashSet<Dependent<?>>();

}