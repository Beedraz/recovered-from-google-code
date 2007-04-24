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

package org.beedraz.semantics_II.path;


import org.beedraz.semantics_II.AbstractDependentBeed;
import org.beedraz.semantics_II.Beed;
import org.ppeew.annotations_I.vcs.CvsInfo;


/**
 * <p>Common implementation for methods of {@link Path}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractDependentPath<_SelectedBeed_ extends Beed<?>>
    extends AbstractDependentBeed<PathEvent<_SelectedBeed_>>
    implements Path<_SelectedBeed_> {

  @Override
  protected String otherToStringInformation() {
    _SelectedBeed_ selected = get();
    return selected == null ? "null" : selected.toString();
  }

  @Override
  public final void toString(StringBuffer sb, int i) {
    sb.append(otherToStringInformation());
  }

}
