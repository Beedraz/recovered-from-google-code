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

package org.beedra_II.property.association;


import org.beedra_II.bean.BeanBeed;
import org.beedra_II.property.simple.EditableSimplePropertyBeed;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * @mudo description
 *
 * @invar  getOwner() instanceof _Many_;
 *
 * @mudo implement event propagation to ONE
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class EditableBidirToOneBeed<_One_ extends BeanBeed,
                                    _Many_ extends BeanBeed>
    extends EditableSimplePropertyBeed<BidirToManyBeed<_One_, _Many_>, BidirToOneEvent<_One_, _Many_>> {

  /**
   * @pre bean != null;
   */
  public EditableBidirToOneBeed(_Many_ bean) {
    super(bean);
  }

  @Override
  @SuppressWarnings("unchecked")
  public final _Many_ getOwner() {
    return (_Many_)super.getOwner();
  }

  public final _One_ getOne() {
    return get() == null ? null : get().getOwner();
  }

  /**
   * @post  result != null;
   * @post  result.getSource() == this;
   * @post  result.getOldValue() == null;
   * @post  result.getNewValue() == get();
   * @post  result.getEdit() == null;
   * @post  result.getEditState() == null;
   */
  @Override
  protected BidirToOneEvent<_One_, _Many_> createInitialEvent() {
    return new BidirToOneEvent<_One_, _Many_>(this, null, get(), null);
  }

}

