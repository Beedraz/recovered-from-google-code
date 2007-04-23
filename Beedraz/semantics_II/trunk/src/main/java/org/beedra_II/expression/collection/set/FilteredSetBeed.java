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

package org.beedra_II.expression.collection.set;


import static org.ppeew.smallfries_I.MultiLineToStringUtil.indent;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.beedra_II.AbstractDependentBeed;
import org.beedra_II.Beed;
import org.beedra_II.Event;
import org.beedra_II.edit.Edit;
import org.beedra_II.expression.bool.BooleanBeed;
import org.beedra_II.expression.bool.BooleanEvent;
import org.beedra_II.path.AbstractDependentPath;
import org.beedra_II.path.Path;
import org.beedra_II.path.PathEvent;
import org.beedra_II.path.PathFactory;
import org.beedra_II.topologicalupdate.AbstractUpdateSourceDependentDelegate;
import org.beedra_II.topologicalupdate.Dependent;
import org.beedra_II.topologicalupdate.UpdateSource;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A {@link SetBeed} that returns a filtered subset of a given {@link SetBeed}.
 * The criterion is a {@link BooleanBeed}, selected through a {@link Path},
 * which is created for each element in the {@link #getSource() source set beed}
 * by a {@link PathFactory} (called {@link #getCriterion()} criterion) given at
 * construction.
 *
 * @author  Nele Smeets
 * @author  Jan Dockx
 * @author  Peopleware n.v.
 *
 * @invar  getCriterion() != null;
 * @invar  getCriterion() == 'getCriterion();
 * @invar  getSource() == null
 *           ? get().isEmpty()
 *           : true;
 * @invar  getSource() != null
 *           ? get() == {element : getSource().get().contains(element) &&
 *                                 getCriterion().createPath(element).get().getboolean() == true}
 *           : true;
 * @invar  getSourcePath() != null
 *           ? getSource() == getSourcePath().get()
 *           : getSource() == null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class FilteredSetBeed<_Element_ extends Beed<?>>
    extends AbstractSetBeed<_Element_, SetEvent<_Element_>>
    implements SetBeed<_Element_, SetEvent<_Element_>> {

  /**
   * @pre   criterion != null;
   * @post  getCriterion() == criterion;
   * @post  getSource() == null;
   * @post  getSourcePath() == null;
   * @post  get().isEmpty();
   */
  public FilteredSetBeed(PathFactory<_Element_, BooleanBeed> criterion) {
    $criterion = criterion;
  }

  private final Dependent $dependent = new AbstractUpdateSourceDependentDelegate(this) {

      @Override
      protected SetEvent<_Element_> filteredUpdate(Map<UpdateSource, Event> events, Edit<?> edit) {
        assert events != null;
        assert events.size() > 0;
        /* Events can come:
         * - from the source path,
         * - from the source (elements added or removed) and
         * - from the element criteria of each of the source elements
         * If an event comes from the source path, we might also get
         * events from the source or even elements. But these don't
         * matter, since we have a new source, and all elements have
         * been removed, and new added.
         * If an event comes from the source, about added or removed elements,
         * we might also get events from elements. Events from elements that
         * are just removed should not be dealt with.
         */
//        System.out.println("=================");
//        System.out.println(events.size() + "{{" + events + "}}");
//        for (Map.Entry<UpdateSource, Event> entry : events.entrySet()) {
//          System.out.println(entry);
////          System.out.println(us.getClass());
////          System.out.println(Integer.toHexString(us.hashCode()));
//        }
//        System.out.println("=================");
        HashSet<_Element_> addedFilteredElements = new HashSet<_Element_>();
        HashSet<_Element_> removedFilteredElements = new HashSet<_Element_>();
        PathEvent<SetBeed<_Element_, ?>> pathEvent = (PathEvent<SetBeed<_Element_, ?>>)events.get($sourcePath);
        if (pathEvent != null) {
          handleSourcePathEvent(pathEvent, addedFilteredElements, removedFilteredElements);
          // if there is a path event, don't deal with other events
        }
        else {
          @SuppressWarnings("unchecked")
          SetEvent<_Element_> setEvent = (SetEvent<_Element_>)events.get($source);
          if (setEvent != null) {
            handleSourceEvent(setEvent, addedFilteredElements, removedFilteredElements);
          }
          for (Event event : events.values()) {
            handleElementCriterionEvent(event, addedFilteredElements, removedFilteredElements);
          }
        }
        return createEvent(addedFilteredElements, removedFilteredElements, edit);
      }

      /**
       * If the given event is of type {@link PathEvent}, then it is caused by
       * the {@link FilteredSetBeed#getSourcePath()}. In this case, we
       * replace the old source by the new value in the given event.
       * The elements that are added by this operation are gathered in
       * <code>addedFilteredElements</code>.
       * The elements that are removed by this operation are gathered in
       * <code>removedFilteredElements</code>.
       *
       * @pre  pathEvent != null;
       * @pre  addedFilteredElements != null;
       * @pre  removedFilteredElements != null;
       */
      private void handleSourcePathEvent(PathEvent<SetBeed<_Element_, ?>> pathEvent,
                                         HashSet<_Element_> addedFilteredElements,
                                         HashSet<_Element_> removedFilteredElements) {
        assert pathEvent != null;
        assert addedFilteredElements != null;
        assert removedFilteredElements != null;
        assert pathEvent.getSource() == $sourcePath;
        SetBeed<_Element_, ?> newSource = pathEvent.getNewValue();
        setSource(newSource, addedFilteredElements, removedFilteredElements);
      }

      /**
       * If the given event is of type {@link SetEvent}, then it is caused by
       * the {@link FilteredSetBeed#getSource()}.
       * In this case, we remove the elements in {@link SetEvent#getRemovedElements()}
       * and we add the elements in {@link SetEvent#getAddedElements()}.
       * The elements that are added to {@link FilteredSetBeed#$filteredSet}
       * by this operation are gathered in <code>addedFilteredElements</code>.
       * The elements that are removed from {@link FilteredSetBeed#$filteredSet}
       * by this operation are gathered in <code>removedFilteredElements</code>.
       *
       * @pre  event != null;
       * @pre  addedFilteredElements != null;
       * @pre  removedFilteredElements != null;
       */
      private void handleSourceEvent(SetEvent<_Element_> setEvent,
                                     HashSet<_Element_> addedFilteredElements,
                                     HashSet<_Element_> removedFilteredElements) {
        assert setEvent != null;
        assert addedFilteredElements != null;
        assert removedFilteredElements != null;
//        System.out.println("---------------");
//        System.out.println(this);
//        System.out.println(setEvent);
//        System.out.println(setEvent.getSource());
//        System.out.println($source);
//        System.out.println(setEvent.getSource() == $source);
//        System.out.println("---------------");
        assert setEvent.getSource() == $source;
        for (_Element_ e : setEvent.getAddedElements()) {
          sourceElementAdded(e, addedFilteredElements);
        }
        for (_Element_ e : setEvent.getRemovedElements()) {
          sourceElementRemoved(e, removedFilteredElements);
        }
      }

      /**
       * If the given event is of type {@link BooleanEvent}, then it is caused by
       * one of the {@link ElementCriterion filter criteria}.
       * Since a {@link ElementCriterion filter criterion} is a {@link BooleanBeed},
       * and since it can only be true or false (not null), this event is sent when
       * the value of the {@link ElementCriterion filter criterion} changed from true
       * to false, or from false to true.
       * When the {@link BooleanEvent#getNewValue() new value} of the event is true,
       * we add the element of the {@link ElementCriterion filter criterion} to
       * {@link FilteredSetBeed#$filteredSet} and to <code>addedFilteredElements</code>.
       * When the {@link BooleanEvent#getNewValue() new value} of the event is false,
       * we remove the element of the {@link ElementCriterion filter criterion} from
       * {@link FilteredSetBeed#$filteredSet} and add it to
       * <code>removedFilteredElements</code>.
       * We don't handle events of sources that are already in {@code removedFilteredElements}.
       *
       * @pre  event != null;
       * @pre  addedFilteredElements != null;
       * @pre  removedFilteredElements != null;
       */
      private void handleElementCriterionEvent(Event event,
                                               HashSet<_Element_> addedFilteredElements,
                                               HashSet<_Element_> removedFilteredElements) {
        assert event != null;
        assert addedFilteredElements != null;
        assert removedFilteredElements != null;
        try {
          BooleanEvent criterionEvent = (BooleanEvent)event;
          assert criterionEvent.getSource() instanceof FilteredSetBeed.ElementCriterion;
          ElementCriterion ec = (ElementCriterion)criterionEvent.getSource();
          _Element_ element = ec.getElement();
          if (! removedFilteredElements.contains(element)) {
            if (criterionEvent.getNewValue()) {
              $filteredSet.add(element);
              addedFilteredElements.add(element);
            }
            else {
              $filteredSet.remove(element);
              removedFilteredElements.add(element);
            }
          }
        }
        catch (ClassCastException ccExc) {
          // NOP
        }
      }

    };

  public final int getMaximumRootUpdateSourceDistance() {
    /* FIX FOR CONSTRUCTION PROBLEM
     * At construction, the super constructor is called with the future owner
     * of this property beed. Eventually, in the constructor code of AbstractPropertyBeed,
     * this object is registered as update source with the dependent of the
     * aggregate beed. During that registration process, the dependent
     * checks to see if we need to ++ our maximum root update source distance.
     * This involves a call to this method getMaximumRootUpdateSourceDistance.
     * Since however, we are still doing initialization in AbstractPropertyBeed,
     * initialization code (and construction code) further down is not yet executed.
     * This means that our $dependent is still null, and this results in a NullPointerException.
     * On the other hand, we cannot move the concept of $dependent up, since not all
     * property beeds have a dependent.
     * The fix implemented here is the following:
     * This problem only occurs during construction. During construction, we will
     * not have any update sources, so our maximum root update source distance is
     * effectively 0.
     */
    /*
     * TODO This only works if we only add 1 update source during construction,
     *      so a better solution should be sought.
     */
    return $dependent == null ? 0 : $dependent.getMaximumRootUpdateSourceDistance();
  }



  /*<property name="criterion">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final PathFactory<_Element_, BooleanBeed> getCriterion() {
    return $criterion;
  }

  /**
   * Fabricated class. This {@link BooleanBeed} uses a
   * {@link Path} to a true {@link BooleanBeed} for an
   * {@code _Element_} (created by {@link #getCriterion()}).
   * If the result of the path is {@code true}, this {@link BooleanBeed}
   * returns {@code true}.
   * If the result of the path is {@code false}, this {@link BooleanBeed}
   * returns {@code false}.
   * If the result of the path is {@code null} (either the resulting {@link BooleanBeed}
   * is not effective, or its {@link BooleanBeed#getBoolean()} method returns
   * {@code null}), this {@link BooleanBeed} returns {@code false}.
   * So, this {@link BooleanBeed} never returns {@code null}.
   *
   * The value of this {@link BooleanBeed} can change if:
   * - the value of the resulting {@link BooleanBeed} changes, or
   * - the result of the given path changes, i.e. the path returns another
   *   {@link BooleanBeed}.
   *
   * @invar get() != null; The value of this beed is always true or false, never null.
   * @invar getElement() != null;
   */
  private class ElementCriterion
      extends AbstractDependentBeed<BooleanEvent>
      implements BooleanBeed {

    /**
     * @pre   element != null;
     * @post  getElement() == element;
     */
    public ElementCriterion(_Element_ element) {
      assert element != null;
      $element = element;
      $bbPath = getCriterion().createPath(element);
      assert $bbPath != null;
      if ($bbPath instanceof AbstractDependentPath) {
        addUpdateSource($bbPath);
      }
      $bb = $bbPath.get();
      if ($bb != null) {
        addUpdateSource($bb);
      }
      $value = ($bb == null) ? false : $bb.getboolean();
    }

    /**
     * There are two update sources:
     * - {@link ElementCriterion#$bbPath} and
     * - {@link ElementCriterion#$bb} (if it is effective)
     * If {@link ElementCriterion#$bbPath} changes, then it sends a {@link PathEvent},
     * containing the old and new value of the path, i.e. containing the old and new
     * {@link BooleanBeed}. We remove the old {@link BooleanBeed} as {@link UpdateSource}
     * and add the new {@link BooleanBeed} as {@link UpdateSource}. The value of
     * {@link ElementCriterion#$bb} is replaced by the new {@link BooleanBeed}. The
     * {@link ElementCriterion#$value} is updated to the value of the new
     * {@link BooleanBeed}.
     * If {@link ElementCriterion#$bb} changes, then it sends a {@link BooleanEvent},
     * containing the old and new value of the {@link BooleanBeed}. The
     * {@link ElementCriterion#$value} is updated to the new value of the
     * {@link ElementCriterion#$bb}.
     *
     * If the {@link ElementCriterion#$value} has changed, then we return a
     * {@link BooleanEvent} containing the old and new value.
     */
    @Override
    protected BooleanEvent filteredUpdate(Map<UpdateSource, Event> events, Edit<?> edit) {
      // events are from the $bbPath or the $bb (if it is effective)
      assert events != null;
      assert events.size() > 0;
      boolean oldValue = getboolean();
      PathEvent<BooleanBeed> pathEvent = (PathEvent<BooleanBeed>)events.get($bbPath);
      if (pathEvent != null) {
        if ($bb != null) {
          removeUpdateSource($bb);
        }
        $bb = pathEvent.getNewValue();
        if ($bb != null) {
          addUpdateSource($bb);
        }
      }
      $value = $bb == null ? false : $bb.getboolean();
      if (oldValue != $value) {
        return new BooleanEvent(ElementCriterion.this, oldValue, $value, edit);
      }
      else {
        return null;
      }
    }

    /**
     * The element we are the criterion for.
     *
     */
    public final _Element_ getElement() {
      return $element;
    }

    /**
     * The element we are the criterion for.
     *
     * @invar element != null;
     */
    private _Element_ $element;

    /**
     * @invar $bbPath != null;
     */
    private final Path<? extends BooleanBeed> $bbPath;

    /**
     * @invar $bb = $bbPath.get();
     */
    private BooleanBeed $bb;

    /**
     * @invar $bb == null ? $value == false : $value == $bb.getboolean();
     */
    private boolean $value;

    public Boolean getBoolean() {
      return $value;
    }

    public boolean getboolean() {
      return $value;
    }

    /**
     * The value of this beed is never null.
     */
    public boolean isEffective() {
      return true;
    }

    public Boolean get() {
      return $value;
    }

  }

  private PathFactory<_Element_, BooleanBeed> $criterion;

  /*</property>*/



  /*<property name="source">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final Path<? extends SetBeed<_Element_, ?>> getSourcePath() {
    return $sourcePath;
  }

  /**
   * @return getSourcePath() == null ? null : getSourcePath().get();
   */
  public final SetBeed<_Element_, ?> getSource() {
    return $source;
  }

  /**
   * The old source path is removed as update source.
   * The new source path is added as update source.
   * The source is replaced by the new source: see {@link #setSource(SetBeed)}.
   */
  public final void setSourcePath(Path<? extends SetBeed<_Element_, ?>> sourcePath) {
    if ($sourcePath instanceof AbstractDependentPath) {
      $dependent.removeUpdateSource($sourcePath);
    }
    $sourcePath = sourcePath;
    if ($sourcePath instanceof AbstractDependentPath) {
      $dependent.addUpdateSource($sourcePath);
    }
    if ($sourcePath != null) {
      setSource($sourcePath.get());
    }
    else {
      setSource(null);
    }
  }

  /**
   * @param   source
   * @post    getSource() == source;
   * @post    get() == the result of filtering the given source
   * @post    The {@link FilteredSetBeed} is registered as a listener of the given SetBeed.
   * @post    The {@link FilteredSetBeed} is registered as a listener of all beeds in
   *          the given source. (The reason is that the {@link FilteredSetBeed} should be
   *          notified (and then recalculate) when one of the beeds in the source
   *          changes.)
   * @post    The listeners of this beed are notified when the value changes.
   */
  private final void setSource(SetBeed<_Element_, ?> source) {
    HashSet<_Element_> addedFilteredElements = new HashSet<_Element_>();
    HashSet<_Element_> removedFilteredElements = new HashSet<_Element_>();
    setSource(source, addedFilteredElements, removedFilteredElements);
    ActualSetEvent<_Element_> event = createEvent(addedFilteredElements, removedFilteredElements, null);
    if (event != null) {
      updateDependents(event);
    }
  }

  /**
   * Replace the old {@link #getSource() source} by the given source.
   * Remove the old {@link #getSource() source} and all the
   * {@link ElementCriterion filter criteria} of its elements as
   * {@link UpdateSource}.
   * Add the given source and all the {@link ElementCriterion filter criteria}
   * of its elements as {@link UpdateSource}.
   * Update the {@link #$filteredSet}:
   * - remove the elements that were there, and put them in
   *   <code>removedFilteredElements</code>.
   * - add the elements in the given source that satisfy the
   *   {@link ElementCriterion filter criterion} and put them in
   *   <code>addedFilteredElements</code>.
   * Update the {@link #$elementCriteria}:
   * - remove the elements that were there.
   * - add the elements in the given source, with their corresponding
   *   {@link ElementCriterion filter criteria}.
   *
   * @pre  addedFilteredElements != null;
   * @pre  removedFilteredElements != null;
   */
  private final void setSource(SetBeed<_Element_, ?> source,
      HashSet<_Element_> addedFilteredElements, HashSet<_Element_> removedFilteredElements) {
    assert addedFilteredElements != null;
    assert removedFilteredElements != null;
    if ($source != null) {
      for (_Element_ beed : $source.get()) {
        sourceElementRemoved(beed, removedFilteredElements);
      }
      $dependent.removeUpdateSource($source);
    }
    // set the source
    $source = source;
    if ($source != null) {
      $dependent.addUpdateSource($source);
      for (_Element_ beed : $source.get()) {
        sourceElementAdded(beed, addedFilteredElements);
      }
    }
  }

  private ActualSetEvent<_Element_> createEvent(
      HashSet<_Element_> addedFilteredElements,
      HashSet<_Element_> removedFilteredElements,
      Edit<?> edit) {
    ActualSetEvent<_Element_> event;
    @SuppressWarnings("unchecked")
    Set<_Element_> addedClone = (Set<_Element_>)addedFilteredElements.clone();
    addedFilteredElements.removeAll(removedFilteredElements);
    removedFilteredElements.removeAll(addedClone);
    if ((! removedFilteredElements.isEmpty()) || (! addedFilteredElements.isEmpty())) {
      event = new ActualSetEvent<_Element_>(FilteredSetBeed.this,
                                            addedFilteredElements,
                                            removedFilteredElements,
                                            edit);
    }
    else {
      event = null;
    }
    return event;
  }

  private Path<? extends SetBeed<_Element_, ?>> $sourcePath;

  private SetBeed<_Element_, ?> $source;

  /*</property>*/


  /**
   * Add the {@link ElementCriterion filter criterion} of the given element as
   * {@link UpdateSource}.
   * Update the {@link #$filteredSet}: add the element if it satisfies
   * the {@link ElementCriterion filter criterion} and put it in
   * <code>addedFilteredElements</code>.
   * Update the {@link #$elementCriteria}: add the given element.
   *
   * @pre  element != null;
   * @pre  ! $elementCriteria.containsKey(element);
   * @pre  addedFilteredElements != null;
   */
  private void sourceElementAdded(_Element_ element, Set<_Element_> addedFilteredElements) {
    assert element != null;
    assert ! $elementCriteria.containsKey(element);
    assert addedFilteredElements != null;
    ElementCriterion ec = new ElementCriterion(element);
    $elementCriteria.put(element, ec);
    $dependent.addUpdateSource(ec);
    if (ec.getboolean()) {
      $filteredSet.add(element);
      addedFilteredElements.add(element);
    }
  }

  /**
   * Remove the {@link ElementCriterion filter criterion} of the given element as
   * {@link UpdateSource}.
   * Update the {@link #$filteredSet}: remove the element (if it was there)
   * and then put it in <code>removedFilteredElements</code>.
   * Update the {@link #$elementCriteria}: remove the given element.
   *
   * @pre  element != null;
   * @pre  $elementCriteria.containsKey(element);
   * @pre  removedFilteredElements != null;
   */
  private void sourceElementRemoved(_Element_ element, Set<_Element_> removedFilteredElements) {
    assert element != null;
    assert $elementCriteria.containsKey(element);
    assert removedFilteredElements != null;
    boolean removed = $filteredSet.remove(element);
    ElementCriterion ec = $elementCriteria.get(element);
    $elementCriteria.remove(element);
    assert ec != null;
    $dependent.removeUpdateSource(ec);
    if (removed) {
      removedFilteredElements.add(element);
    }
  }

  /**
   * @invar  $elementCriteria != null;
   */
  private Map<_Element_, ElementCriterion> $elementCriteria = new HashMap<_Element_, ElementCriterion>();


  /**
   * The set of filtered elements in the {@link #getSource()}.
   *
   * @invar  $filteredSet != null;
   */
  private final HashSet<_Element_> $filteredSet = new HashSet<_Element_>();


  /**
   * @basic
   */
  public final Set<_Element_> get() {
    return Collections.unmodifiableSet($filteredSet);
  }

  public final Set<? extends UpdateSource> getUpdateSources() {
    return $dependent.getUpdateSources();
  }

  private final static Set<? extends UpdateSource> PHI = Collections.emptySet();

  public final Set<? extends UpdateSource> getUpdateSourcesTransitiveClosure() {
    /* fixed to make it possible to use this method during construction,
     * before $dependent is initialized. But that is bad code, and should be
     * fixed.
     */
    return $dependent == null ? PHI : $dependent.getUpdateSourcesTransitiveClosure();
  }

  @Override
  protected String otherToStringInformation() {
    return "#: " + get().size();
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
