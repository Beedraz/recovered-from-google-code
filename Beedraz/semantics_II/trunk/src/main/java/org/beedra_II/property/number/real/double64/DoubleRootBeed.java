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


import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A beed that is the {@link #getConstant()}-root of an
 * {@link #getArgument() argument} {@link DoubleBeed}.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class DoubleRootBeed
    extends AbstractDoubleConstantUnaryExpressionBeed {

  /**
   * @post  getDouble() == null;
   * @post  getArgument() == null;
   * @post  getConstant() == constant;
   */
  public DoubleRootBeed(double constant) {
    super(constant);
  }

  /**
   * @pre argumentValue != null;
   */
  @Override
  protected final double calculateValue(double argumentValue) {
    double root = getConstant();
    if (root == 1) {
      return argumentValue;
    }
    else if (root == 2) {
      return Math.sqrt(argumentValue);
    }
    else if (root == 3) {
      return Math.cbrt(argumentValue);
    }
    else {
      return Math.pow(argumentValue, 1 / getConstant());
    }
  }

  @Override
  public final String getOperatorString() {
    return "^(1/" + getConstant() + ")";
  }

}

