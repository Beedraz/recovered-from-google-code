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

package org.beedra_II.property.set;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.beedra_II.event.Event;
import org.beedra_II.property.simple.SimplePB;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Event that notifies of changes in a {@link SetBeed}.
 *
 * @author Jan Dockx
 *
 * @invar getAddedElements() != null;
 * @invar getRemovedElements() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class SetEvent<_Element_, _Source_ extends SimplePB<Set<_Element_>, SetEvent<_Element_, _Source_>>>
    extends Event<_Source_> {

  /**
   * @pre source != null;
   * @post getSource() == source;
   * @post addedElements != null ? getAddedElements().equals(addedElements) : getAddedElements().isEmpty();
   * @post removedElements != null ? getRemovedElements().equals(removedElements) : getRemovedElements().isEmpty();
   */
  public SetEvent(_Source_ source, Set<? extends _Element_> addedElements, Set<? extends _Element_> removedElements) {
    super(source);
    $addedElements = addedElements != null ?
                       new HashSet<_Element_>(addedElements) :
                       new HashSet<_Element_>();
    $removedElements = removedElements != null ?
                         new HashSet<_Element_>(removedElements) :
                         new HashSet<_Element_>();
  }

  /**
   * @basic
   */
  public final Set<_Element_> getAddedElements() {
    return Collections.unmodifiableSet($addedElements);
  }

  /**
   * @invar $addedElements != null;
   */
  private final Set<_Element_> $addedElements;

  /**
   * @basic
   */
  public final Set<_Element_> getRemovedElements() {
    return Collections.unmodifiableSet($removedElements);
  }

  /**
   * @invar $removedElements != null;
   */
  private final Set<_Element_> $removedElements;

}
