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

package org.beedra_II.property.association.set;


import static org.ppeew.smallfries_I.MultiLineToStringUtil.indent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.beedra_II.bean.BeanBeed;
import org.beedra_II.property.collection.set.AbstractSetBeed;
import org.beedra_II.property.collection.set.ActualSetEvent;
import org.beedra_II.property.collection.set.SetBeed;
import org.beedra_II.property.collection.set.SetEvent;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * @invar getOwner() instanceof _One_;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class BidirToManyBeed<_One_ extends BeanBeed,
                             _Many_ extends BeanBeed>
    extends AbstractSetBeed<_Many_, SetEvent<_Many_>>
    implements SetBeed<_Many_, SetEvent<_Many_>> {

  public BidirToManyBeed(_One_ bean) {
    super(bean);
  }

  @Override
  @SuppressWarnings("unchecked")
  public final _One_ getOwner() {
    return (_One_)super.getOwner();
  }

  /**
   * @default  true;
   */
  public boolean isAcceptable(_Many_ goal) {
    return true;
  }

  public final Set<_Many_> get() {
    return Collections.unmodifiableSet($many);
  }

  /**
   * @pre  many != null;
   * @pre  !get().contains(many);
   * @post get().contains(many);
   */
  void add(_Many_ many) {
    assert many != null;
    assert !get().contains(many);
    $many.add(many);
    $sizeBeed.setSize($sizeBeed.get() + 1);
  }

  /**
   * Fire a {@link SetEvent} saying that the given element is added
   * by the given edit.
   * Fire an event on the $sizeBeed, containing the old size of the set beed
   * and the edit that caused the change.
   *
   * @pre  get().contains(many);
   */
  void fireAddedEvent(_Many_ many, BidirToOneEdit<_One_, _Many_> edit) {
    Set<_Many_> added = new HashSet<_Many_>();
    added.add(many);
    fireChangeEvent(new ActualSetEvent<_Many_>(this, added, null, edit));
    int oldSize = $sizeBeed.get() - 1;
    $sizeBeed.fireEvent(oldSize, edit);
  }

  /**
   * @pre  get().contains(many);
   * @post !get().contains(many);
   */
  void remove(_Many_ many) {
    assert !get().contains(many);
    $many.remove(many);
    $sizeBeed.setSize($sizeBeed.get() - 1);
  }

  /**
   * Fire a {@link SetEvent} saying that the given element is removed
   * by the given edit.
   * Fire an event on the $sizeBeed, containing the old size of the set beed
   * and the edit that caused the change.
   */
  void fireRemovedEvent(_Many_ many, BidirToOneEdit<_One_, _Many_> edit) {
    Set<_Many_> removed = new HashSet<_Many_>();
    removed.add(many);
    fireChangeEvent(new ActualSetEvent<_Many_>(this, null, removed, edit));
    int oldSize = $sizeBeed.get() + 1;
    $sizeBeed.fireEvent(oldSize, edit);
  }

  private final Set<_Many_> $many = new HashSet<_Many_>();

  /**
   * @post  result != null;
   * @post  result.getAddedElements().equals(get());
   * @post  result.getRemovedElements().isEmpty();
   * @post  result.getEdit() == null;
   * @post  result.getEditState() == null;
   */
  @Override
  protected final SetEvent<_Many_> createInitialEvent() {
    return new ActualSetEvent<_Many_>(this, $many, null, null); // event constructor copies set
  }

  @Override
  protected String otherToStringInformation() {
    return "hashCode: " + hashCode() +
           "; #: " + get().size();
  }

  @Override
  public void toString(StringBuffer sb, int level) {
    super.toString(sb, level);
    sb.append(indent(level + 1) + "elements:\n");
    Iterator<_Many_> iter = $many.iterator();
    while (iter.hasNext()) {
      _Many_ element = iter.next();
      element.toString(sb, level + 2);
    }
  }

}
