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

package org.beedraz.semantics_II.aggregate;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.ppeew.annotations_I.License.Type.APACHE_V2;

import org.beedraz.semantics_II.Event;
import org.beedraz.semantics_II.aggregate.AggregateBeed;
import org.beedraz.semantics_II.aggregate.AggregateEvent;
import org.beedraz.semantics_II.aggregate.AggregateEventStateException;
import org.beedraz.semantics_II.edit.EditStateException;
import org.beedraz.semantics_II.edit.IllegalEditException;
import org.beedraz.semantics_II.expression.number.integer.long64.EditableLongBeed;
import org.beedraz.semantics_II.expression.number.integer.long64.LongEdit;
import org.beedraz.semantics_II.expression.string.EditableStringBeed;
import org.beedraz.semantics_II.expression.string.StringBeed;
import org.beedraz.semantics_II.expression.string.StringEdit;
import org.beedraz.semantics_II.expression.string.StringEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ppeew.annotations_I.Copyright;
import org.ppeew.annotations_I.License;
import org.ppeew.annotations_I.vcs.SvnInfo;


@Copyright("2007 - $Date$, Beedraz authors")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision$",
         date     = "$Date$")
public class TestAggregateEvent {

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
    // event source
    AggregateBeed owner = new StubAggregateBeed();
//    LongBeed eventSource = new EditableLongBeed(owner);
//    // old and new value
//    Long oldValue = 0L;
//    Long newValue = 1L;
    // edit
    EditableLongBeed target = new EditableLongBeed(owner);
    LongEdit edit = new LongEdit(target);
    edit.perform();
    // cause
//    Event cause = new ActualLongEvent(eventSource, oldValue, newValue, edit);
    // propagated event source
    AggregateBeed source = new StubAggregateBeed();
    // test constructor
    AggregateEvent aggregateEvent = new AggregateEvent(source, edit);
    assertEquals(aggregateEvent.getSource(), source);
    assertEquals(aggregateEvent.getEdit(), edit);
    assertEquals(aggregateEvent.getEditState(), edit.getState());
    assertEquals(false, aggregateEvent.isClosed());
    assertTrue(aggregateEvent.getComponentevents().isEmpty());
  }

  @Test
  public void constructor2() throws EditStateException, IllegalEditException {
    // event source
    AggregateBeed owner = new StubAggregateBeed();
//    StringBeed eventSource = new EditableStringBeed(owner);
//    // old and new value
//    String oldValue = "0";
//    String newValue = "1";
    // edit
    EditableStringBeed target = new EditableStringBeed(owner);
    StringEdit edit = new StringEdit(target);
    edit.perform();
    // cause
//    Event cause = new StringEvent(eventSource, oldValue, newValue, edit);
    // propagated event source
    AggregateBeed source = new StubAggregateBeed();
    // test constructor
    AggregateEvent aggregateEvent = new AggregateEvent(source, edit);
    assertEquals(aggregateEvent.getSource(), source);
    assertEquals(aggregateEvent.getEdit(), edit);
    assertEquals(aggregateEvent.getEditState(), edit.getState());
    assertTrue(aggregateEvent.getComponentevents().isEmpty());
  }

  @Test
  public void close() throws EditStateException, IllegalEditException {
    // event source
    AggregateBeed owner = new StubAggregateBeed();
//    StringBeed eventSource = new EditableStringBeed(owner);
//    // old and new value
//    String oldValue = "0";
//    String newValue = "1";
    // edit
    EditableStringBeed target = new EditableStringBeed(owner);
    StringEdit edit = new StringEdit(target);
    edit.perform();
    // cause
//    Event cause = new StringEvent(eventSource, oldValue, newValue, edit);
    // propagated event source
    AggregateBeed source = new StubAggregateBeed();
    AggregateEvent aggregateEvent = new AggregateEvent(source, edit);
    aggregateEvent.close();
    assertEquals(true, aggregateEvent.isClosed());
  }

  @Test
  public void addComponentEvent1() throws EditStateException, IllegalEditException {
    // event source
    AggregateBeed owner = new StubAggregateBeed();
    StringBeed eventSource = new EditableStringBeed(owner);
    // edit
    EditableStringBeed target = new EditableStringBeed(owner);
    StringEdit edit = new StringEdit(target);
    edit.perform();
    // propagated event source
    AggregateBeed source = new StubAggregateBeed();
    AggregateEvent aggregateEvent = new AggregateEvent(source, edit);
    aggregateEvent.close();
    assertEquals(true, aggregateEvent.isClosed());
    String oldValue = "0";
    String newValue = "1";
    Event cause1 = new StringEvent(eventSource, oldValue, newValue, edit);
    try {
      aggregateEvent.addComponentEvent(cause1);
      fail();
    }
    catch (AggregateEventStateException exc) {
      assertEquals(aggregateEvent.getSource(), source);
      assertEquals(aggregateEvent.getEdit(), edit);
      assertEquals(aggregateEvent.getEditState(), edit.getState());
      assertTrue(aggregateEvent.getComponentevents().isEmpty());
      assertEquals(true, aggregateEvent.isClosed());
    }
  }

  @Test
  public void addComponentEvent2() throws EditStateException, IllegalEditException {
    // event source
    AggregateBeed owner = new StubAggregateBeed();
    StringBeed eventSource = new EditableStringBeed(owner);
    // edit
    EditableStringBeed target = new EditableStringBeed(owner);
    StringEdit edit = new StringEdit(target);
    edit.perform();
    // propagated event source
    AggregateBeed source = new StubAggregateBeed();
    AggregateEvent aggregateEvent = new AggregateEvent(source, edit);
    String oldValue = "0";
    String newValue = "1";
    Event cause1 = new StringEvent(eventSource, oldValue, newValue, edit);
    try {
      aggregateEvent.addComponentEvent(cause1);
      assertEquals(aggregateEvent.getSource(), source);
      assertEquals(aggregateEvent.getEdit(), edit);
      assertEquals(aggregateEvent.getEditState(), edit.getState());
      assertEquals(1, aggregateEvent.getComponentevents().size());
      assertTrue(aggregateEvent.getComponentevents().contains(cause1));
    }
    catch (AggregateEventStateException exc) {
      fail();
    }
    Event cause2 = new StringEvent(eventSource, oldValue, newValue, edit);
    try {
      aggregateEvent.addComponentEvent(cause2);
      assertEquals(aggregateEvent.getSource(), source);
      assertEquals(aggregateEvent.getEdit(), edit);
      assertEquals(aggregateEvent.getEditState(), edit.getState());
      assertEquals(2, aggregateEvent.getComponentevents().size());
      assertTrue(aggregateEvent.getComponentevents().contains(cause1));
      assertTrue(aggregateEvent.getComponentevents().contains(cause2));
    }
    catch (AggregateEventStateException exc) {
      fail();
    }
    Event cause3 = new StringEvent(eventSource, oldValue, newValue, edit);
    try {
      aggregateEvent.addComponentEvent(cause3);
      assertEquals(aggregateEvent.getSource(), source);
      assertEquals(aggregateEvent.getEdit(), edit);
      assertEquals(aggregateEvent.getEditState(), edit.getState());
      assertEquals(3, aggregateEvent.getComponentevents().size());
      assertTrue(aggregateEvent.getComponentevents().contains(cause1));
      assertTrue(aggregateEvent.getComponentevents().contains(cause2));
      assertTrue(aggregateEvent.getComponentevents().contains(cause3));
    }
    catch (AggregateEventStateException exc) {
      fail();
    }
    aggregateEvent.close();
    Event cause4 = new StringEvent(eventSource, oldValue, newValue, edit);
    try {
      aggregateEvent.addComponentEvent(cause4);
      fail();
    }
    catch (AggregateEventStateException exc) {
      assertEquals(aggregateEvent.getSource(), source);
      assertEquals(aggregateEvent.getEdit(), edit);
      assertEquals(aggregateEvent.getEditState(), edit.getState());
      assertEquals(3, aggregateEvent.getComponentevents().size());
      assertTrue(aggregateEvent.getComponentevents().contains(cause1));
      assertTrue(aggregateEvent.getComponentevents().contains(cause2));
      assertTrue(aggregateEvent.getComponentevents().contains(cause3));
      assertFalse(aggregateEvent.getComponentevents().contains(cause4));
    }

  }

}