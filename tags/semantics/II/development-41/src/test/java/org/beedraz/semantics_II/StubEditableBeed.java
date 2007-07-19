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

package org.beedraz.semantics_II;


import static org.ppeew.annotations_I.License.Type.APACHE_V2;

import java.util.Set;

import org.beedraz.semantics_II.AbstractEditableBeed;
import org.beedraz.semantics_II.aggregate.AggregateBeed;
import org.beedraz.semantics_II.topologicalupdate.UpdateSource;
import org.ppeew.annotations_I.Copyright;
import org.ppeew.annotations_I.License;
import org.ppeew.annotations_I.vcs.SvnInfo;


@Copyright("2007 - $Date$, Beedraz authors")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision$",
         date     = "$Date$")
public class StubEditableBeed extends AbstractEditableBeed<StubEvent> {

  public StubEditableBeed(AggregateBeed owner) {
    super(owner);
  }

  public void publicUpdateDependents(StubEvent event) {
    updateDependents(event);
  }

  public int getMaximumRootUpdateSourceDistance() {
    return 0;
  }

  public Set<? extends UpdateSource> getUpdateSources() {
    return null;
  }

  public Set<? extends UpdateSource> getUpdateSourcesTransitiveClosure() {
    return null;
  }

}
