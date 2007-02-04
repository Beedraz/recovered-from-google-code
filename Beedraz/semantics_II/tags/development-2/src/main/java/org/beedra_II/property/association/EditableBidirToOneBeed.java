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
import org.beedra_II.property.PropertyBeedSelector;
import org.beedra_II.property.simple.EditableSimplePropertyBeed;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * @mudo description
 *
 * @invar getProject() != null ? getProject.getTasks().contains(this);
 *
 * @mudo implement event propagation to ONE
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class EditableBidirToOneBeed<_One_ extends BeanBeed,
                                    _Many_ extends BeanBeed>
    extends EditableSimplePropertyBeed<_One_, BidirToOneEditEvent<_One_, _Many_>> {

  /**
   * @pre bean != null;
   * @pre bidirToManyPBeedSelector != null;
   */
  public EditableBidirToOneBeed(_Many_ bean, PropertyBeedSelector<_One_, BidirToManyBeed<_One_, _Many_>> bidirToManyBeedSelector) {
    super(bean);
    assert bidirToManyBeedSelector != null;
    $bidirToManyBeedSelector = bidirToManyBeedSelector;
  }

  @Override
  @SuppressWarnings("unchecked")
  public final _Many_ getOwner() {
    return (_Many_)super.getOwner();
  }

  public final PropertyBeedSelector<_One_, BidirToManyBeed<_One_, _Many_>> getBidirToManyBeedSelector() {
    return $bidirToManyBeedSelector;
  }

  private final PropertyBeedSelector<_One_, BidirToManyBeed<_One_, _Many_>> $bidirToManyBeedSelector;

  /**
   * @return get() == null ? null : getBidirToManyBeedSelector().getPropertyBeed(get());
   */
  public final BidirToManyBeed<_One_, _Many_> getBidirToManyBeed() {
    return toManyBeedOfOne(get());
  }

  /**
   * @return oneBean == null ? null : getBidirToManyBeedSelector().getPropertyBeed(oneBean);
   */
  final BidirToManyBeed<_One_, _Many_> toManyBeedOfOne(_One_ oneBean) {
    return oneBean == null ? null : getBidirToManyBeedSelector().getPropertyBeed(oneBean);
  }

  @Override
  protected BidirToOneEditEvent<_One_,_Many_> createInitialEvent() {
//    return new FinalBidirToOneEvent<_One_>(this, null, get());
    return new BidirToOneEditEvent<_One_, _Many_>(null);
  }


//  public final void set(_One_ one) {
//    if (get() != one) {
//      if ($toManyRef != null) {
//        $toManyRef.remove(getOwnerBeed());
//      }
//      BidirToManyPBeed<_One_, _Many_> oldManyRef = $toManyRef;
//      _One_ oldValue = get();
//      $toManyRef = one == null ? null : getBidirToManyPBeedSelector().getPropertyBeed(one);
//      if ($toManyRef != null) {
//        $toManyRef.add(getOwnerBeed());
//      }
//      UndoableOldNewBEvent<BidirToOnePDoBeed<_One_, _Many_>, _One_> event =
//        new UndoableOldNewBEvent<BidirToOnePDoBeed<_One_, _Many_>, _One_>(this, oldValue, get());
//      fireChangeEvent(event);
//      // events other side
//      if (oldManyRef != null) {
//        oldManyRef.fireRemovedEvent(getOwnerBeed());
//      }
//      if ($toManyRef != null) {
//        $toManyRef.fireAddedEvent(getOwnerBeed());
//      }
//    }
//  }

//  private BidirToManyPBeed<_One_, _Many_> $toManyRef;

}

