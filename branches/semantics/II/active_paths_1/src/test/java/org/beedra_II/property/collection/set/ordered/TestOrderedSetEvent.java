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

package org.beedra_II.property.collection.set.ordered;


import static org.junit.Assert.assertEquals;

import org.beedra_II.bean.AbstractBeanBeed;
import org.beedra_II.edit.EditStateException;
import org.beedra_II.edit.IllegalEditException;
import org.beedra_II.property.association.set.ordered.OrderedBidirToManyBeed;
import org.beedra_II.property.number.integer.long64.EditableLongBeed;
import org.beedra_II.property.number.integer.long64.LongEdit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ppeew.collection_I.LinkedListOrderedSet;
import org.ppeew.collection_I.OrderedSet;

public class TestOrderedSetEvent {

  public class MyBeanBeed extends AbstractBeanBeed {
    // NOP
  }

  public class OneBeanBeed extends AbstractBeanBeed {
    // NOP
  }

  public class ManyBeanBeed extends AbstractBeanBeed {
    // NOP
  }

  @Before
  public void setUp() throws Exception {
    // NOP
  }

  @After
  public void tearDown() throws Exception {
    // NOP
  }

  @Test
  public void constructor() throws EditStateException, IllegalEditException {
    // source
    OneBeanBeed owner = new OneBeanBeed();
    OrderedSetBeed<ManyBeanBeed, ?> source =
      new OrderedBidirToManyBeed<OneBeanBeed, ManyBeanBeed>(owner);
    // old and new value
    OrderedSet<ManyBeanBeed> oldValue = new LinkedListOrderedSet<ManyBeanBeed>();
    oldValue.add(new ManyBeanBeed());
    OrderedSet<ManyBeanBeed> newValue = new LinkedListOrderedSet<ManyBeanBeed>();
    newValue.add(new ManyBeanBeed());
    newValue.add(new ManyBeanBeed());
    // edit
    EditableLongBeed target = new EditableLongBeed(owner);
    LongEdit edit = new LongEdit(target);
    edit.perform();
    // test constructor
    OrderedSetEvent<ManyBeanBeed> listEvent =
      new ActualOrderedSetEvent<ManyBeanBeed>(source, oldValue, newValue, edit);
    assertEquals(listEvent.getSource(), source);
    assertEquals(listEvent.getOldValue(), oldValue);
    assertEquals(listEvent.getNewValue(), newValue);
    assertEquals(listEvent.getEdit(), edit);
    assertEquals(listEvent.getEditState(), edit.getState());
    // old and new value
    oldValue = null;
    newValue = new LinkedListOrderedSet<ManyBeanBeed>();
    newValue.add(new ManyBeanBeed());
    newValue.add(new ManyBeanBeed());
    // test constructor
    listEvent = new ActualOrderedSetEvent<ManyBeanBeed>(source, oldValue, newValue, edit);
    assertEquals(listEvent.getSource(), source);
    assertEquals(listEvent.getOldValue(), null);
    assertEquals(listEvent.getNewValue(), newValue);
    assertEquals(listEvent.getEdit(), edit);
    assertEquals(listEvent.getEditState(), edit.getState());
    // edit is null
    edit = null;
    // test constructor
    listEvent = new ActualOrderedSetEvent<ManyBeanBeed>(source, oldValue, newValue, edit);
    assertEquals(listEvent.getSource(), source);
    assertEquals(listEvent.getOldValue(), null);
    assertEquals(listEvent.getNewValue(), newValue);
    assertEquals(listEvent.getEdit(), null);
    assertEquals(listEvent.getEditState(), null);
  }

}