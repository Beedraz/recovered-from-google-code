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

package org.beedra_II.beedpath;


import java.util.Map;

import org.beedra_II.BeedMapping;
import org.beedra_II.bean.BeanBeed;
import org.beedra_II.edit.Edit;
import org.beedra_II.event.Event;
import org.beedra_II.property.PropertyBeed;
import org.beedra_II.property.association.set.BidirToOneEvent;
import org.beedra_II.property.association.set.EditableBidirToOneBeed;
import org.beedra_II.topologicalupdate.AbstractUpdateSourceDependentDelegate;
import org.beedra_II.topologicalupdate.Dependent;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * <p>{@link BeedPath} that selects a {@link BeanBeed} from an
 *   {@link BidirToOneBeed}. The {@link #get() returned} {@link BeanBeed}
 *   has the {@link #getOwner() owner aggregate beed} as
 *   {@link PropertyBeed#getOwner()}.</p>
 * <p>When the {@link #getOwner() owner} is {@code null}, the
 *   {@link #get() resulting} {@link PropertyBeed} is {@code null}
 *   too. When the {@link #getOwner() owner} changes, the
 *   {@link #get() resulting} {@link PropertyBeed} can change to. The
 *   {@link #get() resulting} {@link PropertyBeed}  cannot change for
 *   any other reason. When the {@link #get() resulting} {@link PropertyBeed}
 *   changes, dependents and listeners are warned.</p>
 *
 * @author Jan Dockx
 *
 * @note instead of inhering from {@link BeedMapping}, we could use it as a strategy
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class ToOneBeanBeedPath<_One_ extends BeanBeed>
    extends AbstractBeedPath<_One_>
    implements BeedPath<_One_> {


  /*<section name="dependent">*/
  //-----------------------------------------------------------------

  private final Dependent<EditableBidirToOneBeed<_One_, ?>> $dependent =
    new AbstractUpdateSourceDependentDelegate<EditableBidirToOneBeed<_One_, ?>, BeedPathEvent<_One_>>(this) {

      @Override
      protected BeedPathEvent<_One_> filteredUpdate(Map<EditableBidirToOneBeed<_One_, ?>, Event> events, Edit<?> edit) {
        assert events != null;
        assert events.size() == 1;
        BidirToOneEvent<_One_, ?> event = (BidirToOneEvent<_One_, ?>)events.get($from);
        assert event != null;
        assert $one == event.getOldOne();
        $one = event.getNewOne();
        return new BeedPathEvent<_One_>(ToOneBeanBeedPath.this, event.getOldOne(), $one, edit);
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

  /*</section>*/



  /*<property name="from">*/
  //-----------------------------------------------------------------

  public final EditableBidirToOneBeed<_One_, ?> getFrom() {
    return $from;
  }

  public final void setFrom(EditableBidirToOneBeed<_One_, ?> from) {
    if ($from != null) {
      $dependent.removeUpdateSource($from);
    }
    $from = from;
    if ($from != null) {
      $dependent.addUpdateSource($from);
    }
    _One_ oldOne = $one;
    if ($from == null) {
      $one = null;
    }
    else {
      $one = $from.getOne();
    }
    if ($one != oldOne) {
      updateDependents(new BeedPathEvent<_One_>(this, oldOne, $one, null));
    }
  }


  private EditableBidirToOneBeed<_One_, ?> $from;

  /*</property>*/



  /*<property name="selected">*/
  //-----------------------------------------------------------------

  public final _One_ get() {
    return $one;
  }

  private _One_ $one;

  /*</property>*/

}
