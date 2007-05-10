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

package org.beedra.util_I;


import org.toryt.util_I.annotations.vcs.CvsInfo;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class Comparison {

  /**
   * @post a == b ? true
   * @post a != b && a == null ? false
   * @post a != b && b == null ? false
   * @post a != b && a != null && b != null ? a.equals(b);
   */
  public static <_Value_> boolean equalsWithNull(_Value_ a, _Value_ b) {
    return (a == b) || ((a != null) && a.equals(b));
    /* this is also true when
     * # a == null and b == null: should evaluate to true, first condition does
     * # a != null and b == null: should evaluate to false, and equals does return false
     * # a == null and b != null: should evaluate to false, and a != null does return false
     * # a != null and b != null: we need to compare with equals, and we do
     */
  }

}
