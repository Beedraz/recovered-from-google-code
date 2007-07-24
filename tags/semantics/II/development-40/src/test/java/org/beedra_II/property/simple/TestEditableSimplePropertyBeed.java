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

package org.beedra_II.property.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.beedra_II.StubEvent;
import org.beedra_II.StubListener;
import org.beedra_II.aggregate.AggregateBeed;
import org.beedra_II.aggregate.AggregateEvent;
import org.beedra_II.bean.StubBeanBeed;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestEditableSimplePropertyBeed {

  @Before
  public void setUp() throws Exception {
    // NOP
  }

  @After
  public void tearDown() throws Exception {
    // NOP
  }

  private AggregateBeed $owner = new StubBeanBeed();
  private StubEditableSimplePropertyBeed $editableSimplePropertyBeed = new StubEditableSimplePropertyBeed($owner);
  private StubEvent $event1 = new StubEvent($editableSimplePropertyBeed);
  private StubListener<AggregateEvent> $listener1 = new StubListener<AggregateEvent>();
  private StubListener<AggregateEvent> $listener2 = new StubListener<AggregateEvent>();
  private StubListener<StubEvent> $listener3 = new StubListener<StubEvent>();
  private StubListener<StubEvent> $listener4 = new StubListener<StubEvent>();

  @Test
  public void constructor() {
    assertEquals($editableSimplePropertyBeed.getOwner(), $owner);
    // the abstract property beed should be registered with the owner:
    // add listeners to the property beed
    $owner.addListener($listener1);
    $owner.addListener($listener2);
    assertNull($listener1.$event);
    assertNull($listener2.$event);
    // fire a change on the registered beed
    $editableSimplePropertyBeed.publicUpdateDependents($event1);
    // listeners of the aggregate beed should be notified
    assertNotNull($listener1.$event);
    assertNotNull($listener2.$event);
    assertEquals(1, $listener1.$event.getComponentevents().size());
    assertEquals(1, $listener2.$event.getComponentevents().size());
    assertTrue($listener1.$event.getComponentevents().contains($event1));
    assertTrue($listener2.$event.getComponentevents().contains($event1));
  }

  @Test
  public void assign() {
    Integer newValue = new Integer(5);
    $editableSimplePropertyBeed.assign(newValue);
    assertEquals($editableSimplePropertyBeed.get(), newValue);
  }

  @Test
  public void safeValueCopy() {
    Object newValue = new Object();
    Object copy = $editableSimplePropertyBeed.publicSafeValueCopy(newValue);
    assertEquals(newValue, copy);
  }

  @Test
  public void isAcceptable() {
    boolean isAcceptable = $editableSimplePropertyBeed.isAcceptable(new Integer(5));
    assertEquals(isAcceptable, true);
  }

  @Test
  public void fireEvent() {
    // register listeners
    $editableSimplePropertyBeed.addListener($listener3);
    $editableSimplePropertyBeed.addListener($listener4);
    // fire event
    $editableSimplePropertyBeed.publicUpdateDependents($event1);
    // checks
    assertNotNull($listener3.$event);
    assertNotNull($listener4.$event);
    assertEquals($listener3.$event, $event1);
    assertEquals($listener4.$event, $event1);
  }

}