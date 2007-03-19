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

import java.util.LinkedHashMap;
import java.util.Map;

import org.beedra_II.event.Event;


public class StubRootUpdateSource extends DemoRootUpdateSource {

  @Override
  protected void notifyListeners(LinkedHashMap<UpdateSource, Event> events) {
    $events = new LinkedHashMap<UpdateSource, Event>(events);
  }

  public Map<UpdateSource, Event> $events;

}