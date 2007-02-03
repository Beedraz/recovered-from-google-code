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

package org.beedra.example.z_beedra;


import static org.beedra.util_I.MultiLineToStringUtil.indent;

import org.beedra_II.bean.AbstractBeanBeed;
import org.beedra_II.property.association.EditableBidirToOneBeed;
import org.beedra_II.property.string.EditableStringBeed;


/**
 * To be added
 *
 * validation
 * civilization
 * propagation
 */
public class Task extends AbstractBeanBeed {

  public final EditableStringBeed name = new EditableStringBeed(this);

  {
    registerProperty(name);
  }

  public final EditableBidirToOneBeed<Project, Task> project =
      new EditableBidirToOneBeed<Project, Task>(this, Project.tasksSelector);

  {
    registerProperty(project);
  }

  @Override
  protected String otherToStringInformation() {
    return ", name: " + name.get();
  }

  @Override
  public void toString(StringBuffer sb, int level) {
    super.toString(sb, level);
    sb.append(indent(level + 1) + "name: " + name.get() + "\n");
  }

}

