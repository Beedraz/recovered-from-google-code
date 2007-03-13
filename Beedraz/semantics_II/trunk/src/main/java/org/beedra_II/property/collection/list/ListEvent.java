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

package org.beedra_II.property.collection.list;


import java.util.List;

import org.beedra_II.property.collection.CollectionEvent;
import org.beedra_II.property.simple.OldNewEvent;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Event that notifies of changes in a {@link ListBeed}.
 *   The changes are represented by the list before the change {@link #getOldValue()}
 *   and {@link #getNewValue()}. The lists returned by {@link #getAddedElements()}
 *   and {@link #getRemovedElements()} are provided to be compatible with the super
 *   type {@link CollectionEvent}, but do not express the complete change. From these
 *   2 lists, it is impossible to know at which position elements where added or
 *   removed in the source. Still, these 2 delta-lists might be more interesting for
 *   receiving listeners than the old and the new list.</p>
 *
 * @note In contrast to supertypes, the collection type is not generic in this
 *       class, since there are no more subtypes defined in the Java Collection
 *       API below {@link List}. When this should change, this class should
 *       be made generic with respect to the collection type too.</p>
 *
 * @package
 * <p>Actual {@link ListBeed} instances will send instances of
 *   {@link ActualListEvent} as events, but that is hidden for the user.</p>
 *
 * @author Jan Dockx
 *
 * @invar getSource() instanceof ListBeed
 * @invar getOldValue() != null;
 * @invar getNewValue() != null;
 * @invar getAddedElements().equals(getNewValue().removeAll(getOldValue()));
 * @invar getRemovedElements().equals(getOldValue().removeAll(getNewValue()));
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface ListEvent<_Element_>
    extends CollectionEvent<_Element_, List<_Element_>>, OldNewEvent<List<_Element_>> {

  // NOP

}
