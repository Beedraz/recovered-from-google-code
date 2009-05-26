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

package org.beedraz.semantics_II.expression.number.real.double64;

import static org.junit.Assert.assertNull;
import static org.ppwcode.metainfo_I.License.Type.APACHE_V2;

import org.beedraz.semantics_II.expression.number.real.double64.DoubleEBeed;
import org.junit.Test;
import org.ppwcode.metainfo_I.Copyright;
import org.ppwcode.metainfo_I.License;
import org.ppwcode.metainfo_I.vcs.SvnInfo;


@Copyright("2007 - $Date$, Beedraz authors")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision$",
         date     = "$Date$")
public class TestDoubleEBeed
    extends AbstractTestRealArgDoubleUnaryExpressionBeed<DoubleEBeed> {

  @Test
  public void testConstructor() {
    DoubleEBeed beed = new DoubleEBeed();
    assertNull(beed.getOperand());
    assertNull(beed.getDouble());
  }

  @Override
  protected Double expectedValueNotNull(Double operandValue) {
    return Math.pow(Math.E, operandValue);
  }

  @Override
  protected DoubleEBeed createSubject() {
    return new DoubleEBeed();
  }

  @Override
  protected Double valueFromSubject(DoubleEBeed operandBeed) {
    return operandBeed.getDouble();
  }

}
