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

package org.beedra_II.property.number.real.double64;


import static org.ppeew.smallfries_I.MathUtil.castToBigDecimal;
import static org.ppeew.smallfries_I.MultiLineToStringUtil.indent;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beedra_II.aggregate.AggregateBeed;
import org.beedra_II.edit.Edit;
import org.beedra_II.event.Event;
import org.beedra_II.property.AbstractPropertyBeed;
import org.beedra_II.property.number.real.RealBeed;
import org.beedra_II.topologicalupdate.AbstractUpdateSourceDependentDelegate;
import org.beedra_II.topologicalupdate.Dependent;
import org.beedra_II.topologicalupdate.UpdateSource;
import org.ppeew.annotations_I.vcs.CvsInfo;
import org.ppeew.smallfries_I.MathUtil;


/**
 * A beed that performs a commutative arithmetic operation on zero or more
 * beeds of type {@link DoubleBeed}.
 *
 * @invar getNbOccurrences(null) == 0;
 * @invar (forall DoubleBeed db; ; getNbOccurrences(db) >= 0);
 * @invar (exists DoubleBeed db; ; getNbOccurrences(db) > 0 && db.getDouble() == null)
 *            ==> getDouble() == null;
 *        If one of the arguments is null, then the value of this beed is null.
 * @invar (forAll DoubleBeed db; ; getNbOccurrences(db) > 0 ==> db.getDouble() != null)
 *            ==> getDouble() != null;
 *        If all arguments are effective, then the value of this beed is also effective.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractDoubleCommutativeOperationBeed
    extends AbstractPropertyBeed<ActualDoubleEvent>
    implements DoubleBeed {

  /**
   * @pre   owner != null;
   * @post  getdouble() == initialValue();
   * @post  (forall DoubleBeed db; ; getNbOccurrences(db) == 0};
   * @post  isEffective();
   */
  protected AbstractDoubleCommutativeOperationBeed(AggregateBeed owner) {
    super(owner);
  }

  public final boolean isEffective() {
    return $effective;
  }

  protected void assignEffective(boolean effective) {
    $effective = effective;
  }

  private boolean $effective = true;

  private final Dependent<RealBeed<?>> $dependent =
    new AbstractUpdateSourceDependentDelegate<RealBeed<?>, ActualDoubleEvent>(this) {

      @Override
      protected ActualDoubleEvent filteredUpdate(Map<RealBeed<?>, Event> events, Edit<?> edit) {
        boolean oldEffective = $effective;
        double oldValue = $value;
        /* optimization is worse than not optimized;
         * especially for multiplication: removing value involves division; recurrent
         * use would loose us precision big time
         */
        recalculate();
        if ((oldEffective != $effective) || !MathUtil.equalPrimitiveValue(oldValue, $value)) {
          return new ActualDoubleEvent(
              AbstractDoubleCommutativeOperationBeed.this,
              oldEffective ? oldValue : null,
              $effective ? $value : null,
              edit);
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
     * TODO This only works if we add no update sources during construction,
     *      so a better solution should be sought.
     */
    return $dependent == null ? 0 : $dependent.getMaximumRootUpdateSourceDistance();
  }

  /**
   * @basic
   */
  public final int getNbOccurrences(RealBeed<?> argument) {
    int count = 0;
    for (RealBeed<?> a : $arguments) {
      if (a.equals(argument)) {
        count++;
      }
    }
    return count;
  }

  public final Collection<RealBeed<?>> getArguments() {
    return Collections.unmodifiableCollection($arguments);
  }

  /**
   * @pre   argument != null;
   * @post  new.getNbOccurrences(argument) == getNbOccurrences(argument) + 1;
   */
  public final void addArgument(RealBeed<?> argument) {
    assert argument != null;
    $dependent.addUpdateSource(argument);
    $arguments.add(argument);
    // recalculate(); optimization
    if ($effective) {
      double oldValue = $value;
      if (! argument.isEffective()) {
        assignEffective(false);
      }
      else {
        assert argument.isEffective();
        if (argument.getdouble() != initialValue()) {
          $value = operation($value, argument.getdouble());
        }
      }
      if ((! $effective) || !MathUtil.equalPrimitiveValue(oldValue, $value)) {
        updateDependents(new ActualDoubleEvent(this, oldValue, $effective ? $value : null, null));
      }
    }
    // otherwise, there is an existing null argument; the new argument cannot change null value
  }

  /**
   * Perform the operation of this commutative beed.
   */
  protected abstract double operation(double arg1, double arg2);

  /**
   * @post  getNbOccurrences() > 0
   *          ? new.getNbOccurrences(argument) == getNbOccurrences(argument) - 1;
   *          : true;
   */
  public final void removeArgument(RealBeed<?> argument) {
    if ($arguments.contains(argument)) {
      $arguments.remove(argument);
      if (! $arguments.contains(argument)) {
        $dependent.removeUpdateSource(argument);
      }
      // recalculate(); optimization
      boolean oldEffective = $effective;
      double oldValue = $value;
      recalculate(); // optimization would loose precision with multiplication
      if ((oldEffective != $effective) || !MathUtil.equalPrimitiveValue(oldValue, $value)) {
        updateDependents(new ActualDoubleEvent(
            this,
            oldEffective ? oldValue : null,
            $effective ? $value : null,
            null));
      }
    }
  }

  private List<RealBeed<?>> $arguments = new ArrayList<RealBeed<?>>();

  /**
   * The value of this beed is recalculated. This is done by iterating over the
   * arguments.
   * When there are no terms, the result is equal to {@link #initialValue()}.
   * When one of the terms is null, the result is null.
   * When all terms are effective, the result is dependent on the specific subclass.
   */
  private final void recalculate() {
    $value = initialValue();
    for(RealBeed<?> argument : $arguments) {
      if (! argument.isEffective()) {
        assignEffective(false);
        return;
      }
      $value = operation($value, argument.getdouble());
    }
    assignEffective(true);
  }

  public final Double get() {
    return getDouble();
  }

  public final Double getDouble() {
    return $effective ? Double.valueOf($value) : null;
  }

  public final BigDecimal getBigDecimal() {
    return castToBigDecimal(getDouble());
  }

  public final double getdouble() {
    return $value;
  }

  private double $value = initialValue();

  /**
   * The initial value of this beed.
   */
  public abstract double initialValue();

  @Override
  protected String otherToStringInformation() {
    return getDouble() + " (# " + $arguments.size() + ")";
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
  public void toString(StringBuffer sb, int level) {
    super.toString(sb, level);
    sb.append(indent(level + 1) + "value: " + getDouble() + "\n");
    sb.append(indent(level + 1) + "number of " + argumentsToString() + ": " + $arguments.size() + "\n");
    for (RealBeed<?> argument : $dependent.getUpdateSources()) {
      argument.toString(sb, level + 2);
      sb.append(indent(level + 2) + "nr of occurences: " + getNbOccurrences(argument) + "\n");
    }
  }

  /**
   * This method is used in toString and returns the correct name of the arguments
   * in a specific subclass: "terms", "factors", ....
   */
  public abstract String argumentsToString();

  public final void toStringDepth(StringBuffer sb, int depth, NumberFormat numberFormat) {
    Iterator<RealBeed<?>> i = $dependent.getUpdateSources().iterator();
    while (i.hasNext()) {
      RealBeed<?> argument = i.next();
      sb.append("(");
      if (depth == 1) {
        sb.append(numberFormat.format(argument.getdouble()));
      }
      else {
        argument.toStringDepth(sb, depth - 1, numberFormat);
      }
      sb.append(")" + getNbOccurrencesOperatorString() + getNbOccurrences(argument));
      if (i.hasNext()) {
        sb.append(getOperatorString());
      }
    }
  }

  /**
   * The operator of this binary expression.
   */
  public abstract String getOperatorString();

  /**
   * The operator that brings the number of occurrences into account.
   * e.g. * for a sum beed, ^ for a product beed
   */
  public abstract String getNbOccurrencesOperatorString();

}
