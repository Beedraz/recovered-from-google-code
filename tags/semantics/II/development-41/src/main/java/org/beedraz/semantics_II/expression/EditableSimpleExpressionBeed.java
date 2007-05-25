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


import static org.ppeew.annotations_I.License.Type.APACHE_V2;
import static org.ppeew.smallfries_I.MultiLineToStringUtil.indent;

import java.util.Collections;
import java.util.Set;

import org.beedraz.semantics_II.AbstractEditableBeed;
import org.beedraz.semantics_II.Event;
import org.beedraz.semantics_II.aggregate.AggregateBeed;
import org.beedraz.semantics_II.topologicalupdate.UpdateSource;
import org.ppeew.annotations_I.Copyright;
import org.ppeew.annotations_I.License;
import org.ppeew.annotations_I.vcs.SvnInfo;


/**
 * {@link SimplePB} whose value can be changed directly
 * by the user.
 *
 * @author Jan Dockx
 */
@Copyright("2007 - $Date$, Beedraz authors")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision$",
         date     = "$Date$")
public abstract class EditableSimpleExpressionBeed<_Type_,
                                                 _Event_ extends Event>
    extends AbstractEditableBeed<_Event_>
    implements SimpleExpressionBeed<_Type_, _Event_> {

  /**
   * @pre  owner != null;
   * @post getOwner() == owner;
   */
  public EditableSimpleExpressionBeed(AggregateBeed owner) {
    super(owner);
  }

  /**
   * @basic
   */
  public final _Type_ get() {
    return safeValueCopy($t);
  }

  /**
   * @post value != null ? get().equals(value) : get() == null;
   * @post ; all registred ap change listeners are warned
   */
  void assign(_Type_ t) {
    $t = t;
  }


  private _Type_ $t;

  /**
   * Returns a safe copy of {@code original}.
   * If {@code _Value_} is an immutable type, you can return original.
   * The default implementation is to return {@code original}.
   *
   * @result equalsWithNull(result, original);
   * @protected-result original;
   */
  protected _Type_ safeValueCopy(_Type_ original) {
    return original;
  }

  public boolean isAcceptable(_Type_ goal) {
    return true;
  }

  public final int getMaximumRootUpdateSourceDistance() {
    return 0;
  }

  void packageUpdateDependents(_Event_ event) {
    updateDependents(event);
  }

  private final static String NULL_STRING = "null";

  public final Set<UpdateSource> getUpdateSources() {
    return Collections.emptySet();
  }

  public final Set<UpdateSource> getUpdateSourcesTransitiveClosure() {
    return Collections.emptySet();
  }

  @Override
  protected String otherToStringInformation() {
    return get() == null ? NULL_STRING : get().toString();
  }

  @Override
  public void toString(StringBuffer sb, int level) {
    super.toString(sb, level);
    sb.append(indent(level + 1) + "value: \"" + otherToStringInformation() + "\"\n");
  }

}
