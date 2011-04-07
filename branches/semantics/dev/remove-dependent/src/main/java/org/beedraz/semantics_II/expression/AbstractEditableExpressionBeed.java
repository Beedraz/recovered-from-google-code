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

package org.beedraz.semantics_II.expression;


import static org.ppwcode.metainfo_I.License.Type.APACHE_V2;

import org.beedraz.semantics_II.AbstractBeed;
import org.beedraz.semantics_II.Event;
import org.beedraz.semantics_II.aggregate.AggregateBeed;
import org.ppwcode.metainfo_I.Copyright;
import org.ppwcode.metainfo_I.License;
import org.ppwcode.metainfo_I.vcs.SvnInfo;


/**
 * <p>This class offers a constructor that implements the registration of the {@link ExpressionBeed}
 *   with an {@link AggregateBeed}, as described in the documentation of {@link ExpressionBeed}.</p>
 *
 * @author Jan Dockx
 */
@Copyright("2007 - $Date$, Beedraz authors")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision$",
         date     = "$Date$")
public abstract class AbstractEditableExpressionBeed<_Event_ extends Event>
    extends AbstractBeed<_Event_>
    implements ExpressionBeed<_Event_> {

  /**
   * @pre owner != null
   * @post owner.registerAggregateElement(this);
   */
  protected AbstractEditableExpressionBeed(AggregateBeed owner) {
    assert owner != null;
    owner.registerAggregateElement(this);
  }

}