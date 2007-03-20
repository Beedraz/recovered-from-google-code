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

package org.beedra_II.property.collection.set;


import static org.ppeew.smallfries_I.MultiLineToStringUtil.indent;

import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.beedra_II.aggregate.AggregateBeed;
import org.beedra_II.edit.Edit;
import org.beedra_II.event.Listener;
import org.ppeew.annotations_I.vcs.CvsInfo;
import org.ppeew.smallfries_I.ComparisonUtil;


/**
 * A union beed is a derived beed that returns the union of a given
 * set of {@link SetBeed set beeds}.
 *
 * @author  Nele Smeets
 * @author  Peopleware n.v.
 *
 * @invar  getSources() != null;
 * @invar  (forAll SetBeed source; getSources().contains(source); source != null);
 * @invar  get().size() ==
 *           the size of the union of all sets in the given set beeds
 * @invar  (forAll SetBeed<_Element_> setBeed;
 *            getSources().contains(setBeed);
 *            (forAll _Element_ element;
 *               setBeed.get().contains(element);
 *               get().contains(element)));
 *            get() is the union of all sets in the given set beeds
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class UnionSetBeed<_Element_>
    extends AbstractSetBeed<_Element_, SetEvent<_Element_>>
    implements SetBeed<_Element_, SetEvent<_Element_>> {

  /**
   * @pre   owner != null;
   * @post  getOwner() == owner;
   * @post  getSources().isEmpty();
   * @post  get().isEmpty();
   */
  public UnionSetBeed(AggregateBeed owner) {
    super(owner);
  }


  /*<property name="sources">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final Set<SetBeed<_Element_, ?>> getSources() {
    return Collections.unmodifiableSet($sources);
  }

  /**
   * @param  source
   * @pre    source != null;
   * @post   getSources().contains(source);
   * @post   get() = the union of the sources
   * @post   The UnionBeed is added as listener of the given source.
   * @post   The listeners of this beed are notified when the value changes.
   * @post   The listeners of the size beed are notified when the size of this
   *         set has changed.
   */
  public final void addSource(SetBeed<_Element_, ?> source) {
    assert source != null;
    Set<_Element_> oldValue = $union;
    int oldSize = $union.size();
    // add the source
    $sources.add(source);
    // add this UnionBeed as listener of the given source
    source.addListener($setBeedListener);
    // add the elements of the given source to the union
    $union.addAll(source.get());
    // notify the listeners of this beed if the union has changed
    if (! ComparisonUtil.equalsWithNull(oldValue, $union)) {
      fireChangeEvent(
        new ActualSetEvent<_Element_>(
          UnionSetBeed.this, $union, oldValue, null));
    }
    // change the size beed and notify the size beed listeners when the size of the filtered set
    // has changed
    updateSizeBeed(oldSize, null);
  }

  /**
   * @param  source
   * @post   !getSources().contains(source);
   * @post   get() = the union of the sources
   * @post   The UnionBeed is removed as listener of the given source.
   * @post   The listeners of this beed are notified when the value changes.
   * @post   The listeners of the size beed are notified when the size of this
   *         set has changed.
   */
  public final void removeSource(SetBeed<_Element_, ?> source) {
    if ($sources.contains(source)) {
      assert source != null;
      Set<_Element_> oldValue = $union;
      int oldSize = $union.size();
      // remove the source
      $sources.remove(source);
      // remove this UnionBeed as listener of the given source
      source.removeListener($setBeedListener);
      // remove the elements of the given source from the union
      for (_Element_ element : source.get()) {
        if (!contains(getSources(), element)) {
          $union.remove(element);
        }
      }
      // notify the listeners of this beed if the union has changed
      if (! ComparisonUtil.equalsWithNull(oldValue, $union)) {
        fireChangeEvent(
          new ActualSetEvent<_Element_>(
            UnionSetBeed.this, $union, oldValue, null));
      }
      // change the size beed and notify the size beed listeners when the size of the filtered set
      // has changed
      updateSizeBeed(oldSize, null);
    }
  }

  boolean contains(Set<SetBeed<_Element_, ?>> sources, _Element_ element) {
    for (SetBeed<_Element_, ?> source : sources) {
      if (source.get().contains(element)) {
        return true;
      }
    }
    return false;
  }

  private Set<SetBeed<_Element_, ?>> $sources = new HashSet<SetBeed<_Element_, ?>>();

  /*</property>*/

  /**
   * A listener that will be registered as listener of the different
   * {@link #getSources() sources}.
   */
  private final Listener<SetEvent<_Element_>> $setBeedListener =
        new Listener<SetEvent<_Element_>>() {

    /**
     * @post    The UnionBeed is registered as a listener of all beeds
     *          that are added to the source by the given event. (The reason is that
     *          the UnionBeed should be notified (and then recalculate) when one
     *          of the beeds changes.)
     * @post    The UnionBeed is removed as listener of all beeds
     *          that are removed from the source by the given event.
     * @post    get() == the union of the sources
     * @post    The listeners of this beed are notified when the set changes.
     * @post    The listeners of the size beed are notified when the size of this
     *          set has changed.
     */
    public void beedChanged(SetEvent<_Element_> event) {
      int oldSize = $union.size();
      // consider all beeds that are added by the given event: add them to the union
      Set<_Element_> added = event.getAddedElements();
      Set<_Element_> reallyAdded = new HashSet<_Element_>();
      for (_Element_ element : added) {
        if (!$union.contains(element)) {
          $union.add(element);
          reallyAdded.add(element);
        }
      }
      // consider all beeds that are removed by the given event: remove them from the union
      // (but only when they are not contained in one of the other sources)
      SetBeed<_Element_, ?> source = (SetBeed<_Element_, ?>) event.getSource();
      Set<SetBeed<_Element_, ?>> otherSources = new HashSet<SetBeed<_Element_, ?>>();
      otherSources.addAll(getSources());
      otherSources.remove(source);
      Set<_Element_> removed = event.getRemovedElements();
      Set<_Element_> reallyRemoved = new HashSet<_Element_>();
      for (_Element_ element : removed) {
        if (!contains(otherSources, element)) {
          $union.remove(element);
          reallyRemoved.add(element);
        }
      }

      // notify the listeners if elements are added or removed
      if (!reallyAdded.isEmpty() || !reallyRemoved.isEmpty()) {
        fireChangeEvent(
          new ActualSetEvent<_Element_>(
            UnionSetBeed.this, reallyAdded, reallyRemoved, event.getEdit()));
      }
      // change the size beed and notify the size beed listeners when the size of the filtered set
      // has changed
      updateSizeBeed(oldSize, event.getEdit());
    }

  };

  /**
   * Change the size beed and notify the size beed listeners when the size of the filtered set
   * has changed
   */
  private void updateSizeBeed(int oldSize, Edit<?> edit) {
    if (oldSize != $union.size()) {
      $sizeBeed.setSize($union.size());
      $sizeBeed.fireEvent(oldSize, edit);
    }
  }

  /**
   * @invar  $union != null;
   * @invar  Contains the union of the sets of all sources.
   */
  private Set<_Element_> $union = new HashSet<_Element_>();

  /**
   * @basic
   */
  public final Set<_Element_> get() {
    return new AbstractSet<_Element_>() {

      @Override
      public Iterator<_Element_> iterator() {
        final Iterator<_Element_> unionIterator = $union.iterator();
        return new Iterator<_Element_>() {

          public boolean hasNext() {
            return unionIterator.hasNext();
          }

          public _Element_ next() {
            return unionIterator.next();
          }

          /**
           * optional operation
           */
          public void remove() {
            // NOP
          }

        };
      }

      /**
       * The size of the union of the given source sets.
       */
      @Override
      public int size() {
        return $union.size();
      }

    };
  }

  /**
   * @post  result != null;
   * @post  result.getAddedElements().equals(get());
   * @post  result.getRemovedElements().isEmpty();
   * @post  result.getEdit() == null;
   * @post  result.getEditState() == null;
   */
  @Override
  protected SetEvent<_Element_> createInitialEvent() {
    return new ActualSetEvent<_Element_>(this, get(), null, null);
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
    Iterator<_Element_> iter = get().iterator();
    while (iter.hasNext()) {
      _Element_ element = iter.next();
      sb.append(indent(level + 2) + element.toString() + "\n");
    }
  }

}

