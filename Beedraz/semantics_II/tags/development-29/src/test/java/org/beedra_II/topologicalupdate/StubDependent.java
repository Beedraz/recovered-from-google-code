/*<license>
Copyright 2007 - $Date$ by PeopleWare n.v..

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
import java.util.Map;
import java.util.Set;

import org.beedra_II.Beed;
import org.beedra_II.event.Event;
import org.beedra_II.event.Listener;
import org.beedra_II.event.StubEvent;


public class StubDependent extends Dependent<StubUpdateSource>
    implements Beed<StubEvent> {

  @Override
  void fireEvent(Event event) {
    $firedEvent = event;
  }

  @Override
  public UpdateSource getDependentUpdateSource() {
    return $myDependentUpdateSource;
  }

  @Override
  Set<Dependent<?>> getDependents() {
    return Collections.emptySet();
  }

  @Override
  Event update(Map<UpdateSource, Event> events) {
    $updated++;
    $events = events;
    return $myEvent;
  }

  public int $updated = 0;

  public Map<UpdateSource, Event> $events;

  public final StubUpdateSource $myDependentUpdateSource = new StubUpdateSource();

  public final StubEvent $myEvent = new StubEvent(this);

  public Event $firedEvent;



  // stub beed methods

  public void addListener(Listener<? super StubEvent> listener) {
    // NOP
  }

  public boolean isListener(Listener<? super StubEvent> listener) {
    return false;
  }

  public void removeListener(Listener<? super StubEvent> listener) {
    // NOP
  }

  public void toString(StringBuffer sb, int i) {
    // NOP
  }

}