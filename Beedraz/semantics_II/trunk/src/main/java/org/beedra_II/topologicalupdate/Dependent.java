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
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * @author Jan Dockx
 *
 * @invar getMaximumRootUpdateSourceDistance() > 0;
 * @invar for (UpdateSource us : getUpdateSources()) {};
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class Dependent<_Event_ extends Event> {

  public abstract UpdateSource getDependentUpdateSource();

  /**
   * Return {@code null} if this dependent didn't change its value
   * during this update; otherwise, an effective {@code _Event_}
   * that describes the change in this dependents value.
   *
   * @pre events != null;
   */
  public abstract _Event_ update(Map<UpdateSource, Event> events);


  /*<section name="update sources">*/
  //-----------------------------------------------------------------

  /**
   * @basic
   */
  public final Set<UpdateSource> getUpdateSources() {
    return Collections.unmodifiableSet($updateSources);
  }

  /**
   * @pre updateSource != null;
   * @post getUpdateSources().contains(updateSource);
   * @post updateSource.getDependents().contains(this);
   */
  public final void addUpdateSource(UpdateSource updateSource) {
    assert updateSource != null;
    $updateSources.add(updateSource);
    updateSource.addDependent(this);
    updateMaximumRootUpdateSourceDistanceUp(updateSource.getMaximumRootUpdateSourceDistance());
  }

  /**
   * @pre updateSource != null;
   * @post ! getUpdateSources().contains(updateSource);
   * @post ! updateSource.getDependents().contains(this);
   */
  public final void removeUpdateSource(UpdateSource updateSource) {
    assert updateSource != null;
    updateSource.removeDependent(this);
    $updateSources.remove(updateSource);
    updateMaximumRootUpdateSourceDistanceDown(updateSource.getMaximumRootUpdateSourceDistance());
  }

  /**
   * @invar $updateSources != null;
   * @invar Collections.noNull($updateSources);
   */
  private final Set<UpdateSource> $updateSources = new HashSet<UpdateSource>();

  /*</section>*/



  /*<section name="maximum root update source distance">*/
  //-----------------------------------------------------------------

  /**
   * @basic
   */
  public final int getMaximumRootUpdateSourceDistance() {
    return $maximumRootUpdateSourceDistance;
  }

  public void updateMaximumRootUpdateSourceDistanceUp(int newSourceMaximumFinalSourceDistance) {
    int potentialNewMaxDistance = newSourceMaximumFinalSourceDistance + 1;
    if (potentialNewMaxDistance > $maximumRootUpdateSourceDistance) {
      $maximumRootUpdateSourceDistance = potentialNewMaxDistance;
      for (Dependent<?> dependent : getDependentUpdateSource().getDependents()) {
        dependent.updateMaximumRootUpdateSourceDistanceUp($maximumRootUpdateSourceDistance);
      }
    }
  }

  public void updateMaximumRootUpdateSourceDistanceDown(int oldSourceMaximumFinalSourceDistance) {
    if ($maximumRootUpdateSourceDistance == oldSourceMaximumFinalSourceDistance + 1) {
      int oldMaximumFinalSourceDistance = $maximumRootUpdateSourceDistance;
      $maximumRootUpdateSourceDistance = 0;
      for (UpdateSource otherIUpdateSource : getUpdateSources()) {
        int potentialNewMaxDistance = otherIUpdateSource.getMaximumRootUpdateSourceDistance() + 1;
        if (potentialNewMaxDistance > $maximumRootUpdateSourceDistance) {
          $maximumRootUpdateSourceDistance = potentialNewMaxDistance;
        }
      }
      if (oldMaximumFinalSourceDistance != $maximumRootUpdateSourceDistance) {
        for (Dependent<?> dependent : getDependentUpdateSource().getDependents()) {
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
