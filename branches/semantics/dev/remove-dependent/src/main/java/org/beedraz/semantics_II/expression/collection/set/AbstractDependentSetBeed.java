/*<license>
Copyright 2007 - $Date: 2007-05-24 16:46:42 +0200 (Thu, 24 May 2007) $ by the authors mentioned below.

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

package org.beedraz.semantics_II.expression.collection.set;


import static org.ppwcode.metainfo_I.License.Type.APACHE_V2;

import org.beedraz.semantics_II.AbstractDependentBeed;
import org.beedraz.semantics_II.aggregate.AggregateBeed;
import org.beedraz.semantics_II.expression.collection.CollectionBeed;
import org.beedraz.semantics_II.expression.number.integer.IntegerBeed;
import org.beedraz.semantics_II.expression.number.integer.long64.ActualLongEvent;
import org.ppwcode.metainfo_I.Copyright;
import org.ppwcode.metainfo_I.License;
import org.ppwcode.metainfo_I.vcs.SvnInfo;


/**
 * Support for implementations of {@link SetBeed} for dependent beeds.
 * Non-dependent beeds should use {@link AbstractSetBeed}.
 *
 * @author Nele Smeets
 * @author Jan Dockx
 * @author  Peopleware n.v.
 */
@Copyright("2007 - $Date: 2007-05-24 16:46:42 +0200 (Thu, 24 May 2007) $, Beedraz authors")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision: 913 $",
         date     = "$Date: 2007-05-24 16:46:42 +0200 (Thu, 24 May 2007) $")
public abstract class AbstractDependentSetBeed<_Element_, _SetEvent_ extends SetEvent<_Element_>>
    extends AbstractDependentBeed<_SetEvent_>
    implements SetBeed<_Element_, _SetEvent_> {

  /**
   * @post owner != null ? owner.registerAggregateElement(this);
   */
  protected AbstractDependentSetBeed(AggregateBeed owner) {
    if (owner != null) {
      owner.registerAggregateElement(this);
    }
  }

  /**
   * See {@link CollectionBeed#getSize()}.
   */
  public final IntegerBeed<ActualLongEvent> getSize() {
    return $sizeBeed;
  }

  /**
   * See {@link CollectionBeed#getCardinality()}.
   */
  public final IntegerBeed<ActualLongEvent> getCardinality() {
    return $sizeBeed;
  }

  protected SizeBeed $sizeBeed =  new SizeBeed(this);

}
