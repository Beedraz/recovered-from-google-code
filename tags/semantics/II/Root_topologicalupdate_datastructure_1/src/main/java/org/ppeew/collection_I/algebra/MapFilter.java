/*<license>
Copyright 2007 - $Date$ by PeopleWare n.v..

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

package org.ppeew.collection_I.algebra;


import static org.ppeew.annotations_I.License.Type.APACHE_V2;

import java.util.Map;

import org.ppeew.annotations_I.Copyright;
import org.ppeew.annotations_I.License;
import org.ppeew.annotations_I.vcs.CvsInfo;
import org.ppeew.smallfries_I.Filter;


/**
 * An {@link Filter} criterion for {@link Map Maps}.
 *
 * @author Jan Dockx
 * @author PeopleWare n.v.
 */
@Copyright("2007 - $Date$, PeopleWare n.v.")
@License(APACHE_V2)
@CvsInfo(revision    = "$Revision$",
         date        = "$Date$",
         state       = "$State$",
         tag         = "$Name$")
public interface MapFilter<K, V> {

  /**
   * @pre key != null;
   * @pre value != null;
   */
  boolean filter(K key, V value);

}
