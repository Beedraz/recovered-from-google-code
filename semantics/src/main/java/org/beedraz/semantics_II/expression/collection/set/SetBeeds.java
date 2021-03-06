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

package org.beedraz.semantics_II.expression.collection.set;


import static org.ppwcode.metainfo_I.License.Type.APACHE_V2;

import org.ppwcode.metainfo_I.Copyright;
import org.ppwcode.metainfo_I.License;
import org.ppwcode.metainfo_I.vcs.SvnInfo;


/**
 * <p>Convenience methods for working with {@link SetBeed SetBeeds}.</p>
 *
 * @author  Nele Smeets
 * @author  Peopleware n.v.
 */
@Copyright("2007 - $Date$, Beedraz authors")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision$",
         date     = "$Date$")
public class SetBeeds {

  private SetBeeds() {
    // NOP
  }

  /*<section name="union">*/
  //------------------------------------------------------------------

  public static <_Element_> SetBeed<_Element_, ?> union(SetBeed<? extends _Element_, ?>... sources) {
    UnionSetBeed<_Element_> result = new UnionSetBeed<_Element_>();
    for (int i = 0; i < sources.length; i++) {
      result.addSource(sources[i]);
    }
    return result;
  }

  public static <_Element_> SetBeed<_Element_, ?> union(SetBeed<? extends SetBeed<_Element_, ?>, ?> source) {
    UnionBeed<_Element_> result = new UnionBeed<_Element_>();
    result.setSource(source);
    return result;
  }

  /*</section>*/

}

