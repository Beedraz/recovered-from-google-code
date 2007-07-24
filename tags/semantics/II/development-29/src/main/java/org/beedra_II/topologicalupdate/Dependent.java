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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.beedra_II.event.Event;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * <p>The object responsible for the dependent role in topological updates.
 *   It is implemented as a delegate. The dependent role has 2 functions:
 *   updating the dependent in the topological algorithm, and maintaining
 *   the {@link #getMaximumRootUpdateSourceDistance()} for the dependee.</p>
 * <p>The methods {@link #update(Map)}, {@link #fireEvent(Event)},
 *   {@link #getDependentUpdateSource()}, {@link #getMaximumRootUpdateSourceDistance()}
 *   and {@link #getDependent()} are needed for the topological update algorithm.</p>
 * <p>To support the type invariant</p>
 * <pre>
 * for (Dependent d: getDependents() {
 *   d.getMaximumRootUpdateSourceDistance() > getMaximumRootUpdateSourceDistance()
 * };
 * </pre>
 * <p>on both this type and {@link UpdateSource}, the
 *   {@link #getMaximumRootUpdateSourceDistance()} must be maintained on all structural
 *   changes. For this algorithm to work, all dependents must support the methods
 *   {@link #updateMaximumRootUpdateSourceDistanceDown(int)} and
 *   {@link #updateMaximumRootUpdateSourceDistanceDown(int)}, and
 *   {@link #getDependents()} and {@link #getUpdateSources()}.</p>
 * <p>The type {@link UpdateSource} must be an interface, because it is a supertype
 *   for beeds, and beeds require multiple inheritance. The methods
 *   {@link UpdateSource#addDependent(Dependent)} and
 *   {@link UpdateSource#removeDependent(Dependent)} must be defined in that type, and
 *   thus must public. That is deplorable, since {@link UpdateSource#addDependent(Dependent)}
 *   has a hard precondition, and the bidirectional, many-to-many association between
 *   update sources and dependents is adequately supported by add an remove methods on the
 *   dependent side, and not on the update source side. When {@link #addUpdateSource(UpdateSource)}
 *   and {@link #removeUpdateSource(UpdateSource)} of this class are used, everything is cared
 *   for. It is ill-advised to use the methods {@link UpdateSource#addDependent(Dependent)}
 *   and {@link UpdateSource#removeDependent(Dependent)} yourself. Implementing the maintenance
 *   of the bidirectional many-to-many association yourself is ill-advised to. By making this
 *   type a class that supports all that, and not an interface, and demanding that all dependents
 *   are of this type in {@link UpdateSource}, we give a strong hint to using the framework
 *   that way.</p>
 * <p>Because the 2 roles now come together in this class, and all dependents have to be of this
 *   type, access to parts of the algorithm can be restricted.</p>
 *
 * @author Jan Dockx
 *
 * @invar getDependentUpdateSource() != null;
 * @invar for (Dependent d: getDependents() {
 *          d.getMaximumRootUpdateSourceDistance() > getMaximumRootUpdateSourceDistance()
 *        };
 * @invar getUpdateSource().getMaximumRootUpdateSourceDistance() > 0;
 * @invar getUpdateSource().getMaximumRootUpdateSourceDistance() == getMaximumRootUpdateSourceDistance();
 * @invar for (UpdateSource us : getUpdateSources()) {
 *          us.getMaximalRootUpdateSourceDistance() < getMaximalRootUpdateSourceDistance()
 *        };
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class Dependent<_UpdateSource_ extends UpdateSource> {

  /*<property name="dependent update source">*/
  //-----------------------------------------------------------------

  /**
   * The dependent update source, for which we are the delegate.
   *
   * @basic
   *
   * @protected Implement preferably as <code>return <var>OuterClassName</var>.this</code>.
   */
  public abstract UpdateSource getDependentUpdateSource();

  /**
   * <p>This method is used by the topological update algorithm in
   *   {@link AbstractUpdateSource#updateDependents(Event)}.</p>
   * <p>It is the dependent update source that holds the dependents, not us.
   *   And how it holds the dependents, is unknown to use at this level
   *   ({@link UpdateSource} does not offer the collection of dependents).
   *   Therefor, this method is abstract.</p>
   *
   * @result result != null;
   * @result for (Dependent d) {getUpdateSource().isDependent(d) ? result.contains(d)};
   * @result for (Dependent d : result) {getUpdateSource().isDependent(d)};
   */
  abstract Set<Dependent<?>> getDependents();

  /**
   * It is the dependent update source that must fire events, not us.
   * And how it does, is unknown to use at this level
   * ({@link UpdateSource} does not offer firing events).
   * Therefor, this method is abstract.
   * The given {@code event} is the event that was returned
   * by {@link #update(Map)} earlier.
   *
   * @pre event != null;
   * @pre event.getSource() == getUpdateSource();
   * @pre event instanceof _Event_
   */
  abstract void fireEvent(Event event);

  /*</property>*/


  /*<section name="update">*/
  //-----------------------------------------------------------------

  /**
   * The method that updates this dependent, and reports on the change.
   * The returned event is the one that will be offered to
   * {@link #fireEvent(Event)} later on.
   *
   * @pre events != null;
   */
  abstract Event update(Map<UpdateSource, Event> events);

  /*</section>*/


  /*<section name="update sources">*/
  //-----------------------------------------------------------------

  /**
   * @basic
   */
  public final Set<_UpdateSource_> getUpdateSources() {
    return Collections.unmodifiableSet($updateSources);
  }

  /**
   * @pre updateSource != null;
   * @pre ! getDependentsTransitiveClosure().contains(updateSource);
   *      no loops
   * @post getUpdateSources().contains(updateSource);
   * @post updateSource.getDependents().contains(this);
   * @post updateMaximumRootUpdateSourceDistanceUp(updateSource.getMaximumRootUpdateSourceDistance());
   */
  public final void addUpdateSource(_UpdateSource_ updateSource) {
    assert updateSource != null;
//    assert ! isTransitiveDependent(updateSource);
    $updateSources.add(updateSource);
    updateMaximumRootUpdateSourceDistanceUp(updateSource.getMaximumRootUpdateSourceDistance());
    updateSource.addDependent(this);
  }

  /**
   * @pre updateSource != null;
   * @pre 'getUpdateSources().contains(updateSource);
   * @post ! getUpdateSources().contains(updateSource);
   * @post ! updateSource.getDependents().contains(this);
   * @post updateMaximumRootUpdateSourceDistanceDown(updateSource.getMaximumRootUpdateSourceDistance());
   */
  public final void removeUpdateSource(_UpdateSource_ updateSource) {
    assert updateSource != null;
    assert getUpdateSources().contains(updateSource);
    updateSource.removeDependent(this);
    updateMaximumRootUpdateSourceDistanceDown(updateSource.getMaximumRootUpdateSourceDistance());
    $updateSources.remove(updateSource);
  }

  /**
   * @invar $updateSources != null;
   * @invar Collections.noNull($updateSources);
   */
  private final Set<_UpdateSource_> $updateSources = new HashSet<_UpdateSource_>();

  /*</section>*/



  /*<section name="maximum root update source distance">*/
  //-----------------------------------------------------------------

  /**
   * @basic
   */
  public final int getMaximumRootUpdateSourceDistance() {
    return $maximumRootUpdateSourceDistance;
  }

  /**
   * @pre newSourceMaximumFinalSourceDistance < Integer.MAX_VALUE
   * @post getMaximumRootUpdateSourceDistance() ==
   *         max('getMaximumRootUpdateSourceDistance(), newSourceMROSD + 1);
   * @post getMaximumRootUpdateSourceDistance() > 'getMaximumRootUpdateSourceDistance() ?
   *         for (Dependents d: getDependents()) {
   *           d.updateMaximumRootUpdateSourceDistanceUp(getMaximumRootUpdateSourceDistance())
   *         }
   */
  final void updateMaximumRootUpdateSourceDistanceUp(int newSourceMROSD) {
    assert newSourceMROSD < Integer.MAX_VALUE;
    int potentialNewMaxDistance = newSourceMROSD + 1;
    if (potentialNewMaxDistance > $maximumRootUpdateSourceDistance) {
      $maximumRootUpdateSourceDistance = potentialNewMaxDistance;
      for (Dependent<?> dependent : getDependents()) {
        dependent.updateMaximumRootUpdateSourceDistanceUp($maximumRootUpdateSourceDistance);
      }
    }
  }

  /**
   * @post 'getMaximumRootUpdateSourceDistance() > oldSourceMROSD + 1 ?
   *         getMaximumRootUpdateSourceDistance() == 'getMaximumRootUpdateSourceDistance();
   * @post 'getMaximumRootUpdateSourceDistance() == oldSourceMROSD + 1 ?
   *         getMaximumRootUpdateSourceDistance() ==
   *           max(UpdateSource us: getUpdateSources()) {us.getMaximumRootUpdateSourceDistance()} + 1;
   * @post getMaximumRootUpdateSourceDistance() < 'getMaximumRootUpdateSourceDistance() ?
   *         for (Dependents d: getDependents()) {
   *           d.updateMaximumRootUpdateSourceDistanceDown('getMaximumRootUpdateSourceDistance())
   *         }
   */
  final void updateMaximumRootUpdateSourceDistanceDown(int oldSourceMROSD) {
    if ($maximumRootUpdateSourceDistance == oldSourceMROSD + 1) {
        // no overflow problem in if
      int oldMaximumFinalSourceDistance = $maximumRootUpdateSourceDistance;
      $maximumRootUpdateSourceDistance = 0;
      for (UpdateSource otherUpdateSource : getUpdateSources()) {
        int potentialNewMaxDistance = otherUpdateSource.getMaximumRootUpdateSourceDistance() + 1;
          // no overflow problem due to invariant
        if (potentialNewMaxDistance > $maximumRootUpdateSourceDistance) {
          $maximumRootUpdateSourceDistance = potentialNewMaxDistance;
        }
      }
      if (oldMaximumFinalSourceDistance != $maximumRootUpdateSourceDistance) {
        for (Dependent<?> dependent : getDependents()) {
          dependent.updateMaximumRootUpdateSourceDistanceDown(oldMaximumFinalSourceDistance);
        }
      }
    }
  }

  /**
   * @invar $maximumRootUpdateSourceDistance >= 0;
   */
  private int $maximumRootUpdateSourceDistance;

  /*</section>*/

}