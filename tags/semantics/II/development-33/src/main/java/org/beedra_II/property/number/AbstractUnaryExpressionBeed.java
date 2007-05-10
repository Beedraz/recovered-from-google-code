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

package org.beedra_II.property.number;


import static org.ppeew.smallfries_I.MathUtil.equalValue;
import static org.ppeew.smallfries_I.MultiLineToStringUtil.indent;

import java.util.Map;

import org.beedra_II.aggregate.AggregateBeed;
import org.beedra_II.event.Event;
import org.beedra_II.property.number.real.RealBeed;
import org.beedra_II.property.number.real.RealEvent;
import org.beedra_II.topologicalupdate.AbstractUpdateSourceDependentDelegate;
import org.beedra_II.topologicalupdate.Dependent;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * Abstract implementation of unary expression number beeds, represent the a value derived
 * from an {@link #getArgument() argument}.
 *
 * @invar getArgument() == null ? get() == null;
 *
 * @protected
 * @invar getArgument() != null && valueFrom(getArgument()) == null ? get() == null;
 * @invar getArgument() != null && ! valueFrom(getArgument()) != null ?
 *              get().equals(calculateValue(valueFrom(getArgument())));
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractUnaryExpressionBeed<_Number_ extends Number,
                                                  _ArgumentBeed_ extends RealBeed<? extends _ArgumentEvent_>,
                                                  _ArgumentEvent_ extends RealEvent,
                                                  _SendingEvent_ extends RealEvent>
    extends AbstractExpressionBeed<_Number_, _SendingEvent_>  {

  /**
   * @pre   owner != null;
   * @post  getArgument() == null;
   * @post  get() == null;
   */
  public AbstractUnaryExpressionBeed(AggregateBeed owner) {
    super(owner);
  }


  private final Dependent<_ArgumentBeed_> $dependent =
    new AbstractUpdateSourceDependentDelegate<_ArgumentBeed_, _SendingEvent_>(this) {

      @Override
      protected _SendingEvent_ filteredUpdate(Map<_ArgumentBeed_, Event> events) {
        assert $argument != null;
        _Number_ oldValue = get();
        @SuppressWarnings("unchecked")
        _ArgumentEvent_ event = (_ArgumentEvent_)events.get($argument);
        assignValue(calculateValueInternal(newValueFrom(event)));
        if (! equalValue(oldValue, get())) {
          return createNewEvent(oldValue, get(), event.getEdit());
        }
        else {
          return null;
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


  /*<property name="argument">*/
  //-----------------------------------------------------------------


  /**
   * @basic
   */
  public final _ArgumentBeed_ getArgument() {
    return $argument;
  }

  /**
   * @post getArgument() == argument;
   */
  public final void setArgument(_ArgumentBeed_ argument) {
    _Number_ oldValue = get();
    if ($argument != null) {
      $dependent.removeUpdateSource($argument);
    }
    $argument = argument;
    if ($argument != null) {
      assignValue(calculateValueInternal(valueFrom($argument)));
      $dependent.addUpdateSource($argument);
    }
    else {
      assignValue(null);
    }
    if (! equalValue(oldValue, get())) {
      _SendingEvent_ event = createNewEvent(oldValue, get(), null);
      updateDependents(event);
    }
  }

  /**
   * @pre beed != null;
   */
  protected abstract _Number_ valueFrom(_ArgumentBeed_ beed);

  private _ArgumentBeed_ $argument;

  /**
   * @pre event != null;
   */
  protected abstract _Number_ newValueFrom(_ArgumentEvent_ event);

  /*</property>*/


  private _Number_ calculateValueInternal(_Number_ argumentValue) {
    return argumentValue == null ? null : calculateValue(argumentValue);
  }

  /**
   * @pre argumentValue != null;
   */
  protected abstract _Number_ calculateValue(_Number_ argumentValue);

  @Override
  public final void toString(StringBuffer sb, int level) {
    super.toString(sb, level);
    sb.append(indent(level + 1) + "value:" + get() + "\n");
    sb.append(indent(level + 1) + "argument:\n");
    if (getArgument() != null) {
      getArgument().toString(sb, level + 2);
    }
    else {
      sb.append(indent(level + 2) + "null");
    }
  }

//  public void refresh() {
//    getArgument().refresh();
//    assignValue(calculateValueInternal(valueFrom($argument)));
//  }

}
