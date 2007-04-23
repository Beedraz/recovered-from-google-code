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

package org.beedra_II.expression.bool;

import org.beedra_II.expression.bool.AbstractBooleanBinaryLogicalExpressionBeed;
import org.beedra_II.expression.bool.BooleanBeed;
import org.beedra_II.path.Path;


public abstract class AbstractTestBooleanBinaryLogicalExpressionBeed<
                      _UEB_ extends AbstractBooleanBinaryLogicalExpressionBeed> extends
    AbstractTestBooleanArgBooleanBinaryExpressionBeed<_UEB_> {

  @Override
  protected Boolean valueFromSubject(_UEB_ operandBeed) {
    return operandBeed.get();
  }

  @Override
  protected BooleanBeed getLeftOperand() {
    return $subject.getLeftOperand();
  }

  @Override
  protected BooleanBeed getRightOperand() {
    return $subject.getRightOperand();
  }

  @Override
  protected void setLeftOperandPath(Path<? extends BooleanBeed> leftOperandPath) {
    $subject.setLeftOperandPath(leftOperandPath);
  }

  @Override
  protected void setRightOperandPath(Path<? extends BooleanBeed> rightOperandPath) {
    $subject.setRightOperandPath(rightOperandPath);
  }

}