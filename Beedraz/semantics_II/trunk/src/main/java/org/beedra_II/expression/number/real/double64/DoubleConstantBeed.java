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

package org.beedra_II.expression.number.real.double64;


import static org.ppeew.smallfries_I.MathUtil.castToBigDecimal;
import static org.ppeew.smallfries_I.MultiLineToStringUtil.indent;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Set;

import org.beedra_II.AbstractBeed;
import org.beedra_II.topologicalupdate.UpdateSource;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A beed containing a constant {@link Double} value.
 *
 * @author  Nele Smeets
 * @author  Peopleware n.v.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class DoubleConstantBeed
    extends AbstractBeed<ActualDoubleEvent>
    implements DoubleBeed {

  /**
   * @post  getdouble() == constant;
   */
  public DoubleConstantBeed(double constant) {
    $constant = constant;
  }

  /**
   * @return get();
   */
  public final Double getDouble() {
    return $constant;
  }

  public final BigDecimal getBigDecimal() {
    return castToBigDecimal($constant);
  }

  public double getdouble() {
    return $constant;
  }

  public boolean isEffective() {
    return true;
  }

  private double $constant;

  public int getMaximumRootUpdateSourceDistance() {
    return 0;
  }

  public Set<? extends UpdateSource> getUpdateSources() {
    return null;
  }

  public Set<? extends UpdateSource> getUpdateSourcesTransitiveClosure() {
    return null;
  }

  public final void toStringDepth(StringBuffer sb, int depth, NumberFormat numberFormat) {
    sb.append(numberFormat.format(getdouble()));
  }

  @Override
  public final void toString(StringBuffer sb, int level) {
    super.toString(sb, level);
    sb.append(indent(level + 1) + "value:" + getDouble() + "\n");
  }

}
