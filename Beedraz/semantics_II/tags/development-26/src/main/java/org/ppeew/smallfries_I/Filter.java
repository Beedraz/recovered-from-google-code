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

package org.ppeew.smallfries_I;


import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * A filter defines a filter criterion.
 * Using the filter, we can check whether a given element meets this
 * criterion or not.
 *
 * @author  Nele Smeets
 * @author  Peopleware n.v.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface Filter<_Element_> {

  /**
   * Returns true when the given element satisfies the filter criterion.
   * Returns false otherwise.
   */
  boolean filter(_Element_ element);

}

