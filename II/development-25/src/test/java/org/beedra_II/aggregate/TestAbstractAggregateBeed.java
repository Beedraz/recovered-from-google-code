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

package org.beedra_II.aggregate;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.beedra_II.edit.StubEdit;
import org.beedra_II.event.StubEvent;
import org.beedra_II.event.StubListener;
import org.beedra_II.property.simple.StubEditableSimplePropertyBeed;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestAbstractAggregateBeed {

  @Before
  public void setUp() throws Exception {
    $aggregateBeed1 = new StubAggregateBeed();
    $aggregateBeed2 = new StubAggregateBeed();

    $beed1 = new StubEditableSimplePropertyBeed($aggregateBeed1);
//    $beed2 = new MyIntegerSumBeed($subject);
    $beed3 = new StubEditableSimplePropertyBeed($aggregateBeed2);

    $edit1 = new StubEdit($beed1);
    $edit1.perform();
    $event1 = new StubEvent($beed1);
//    $edit2 = new IntegerEdit($beed2);
//    $event2 = new IntegerEvent($beed2, new Integer(2), new Integer(22), $edit2);
    $edit3 = new StubEdit($beed3);
    $edit3.perform();
    $event3 = new StubEvent($beed3);

    $listener1 = new StubListener<PropagatedEvent>();
    $listener2 = new StubListener<PropagatedEvent>();
  }

  @After
  public void tearDown() throws Exception {
    // NOP
  }

  private StubAggregateBeed $aggregateBeed1;
  private StubAggregateBeed $aggregateBeed2;

  private StubEditableSimplePropertyBeed $beed1;
//  private MyIntegerSumBeed $beed2;
  private StubEditableSimplePropertyBeed $beed3;

  private StubEdit $edit1;
  private StubEvent $event1;
//  private IntegerEdit $edit2;
//  private IntegerEvent $event2;
  private StubEdit $edit3;
  private StubEvent $event3;

  private StubListener<PropagatedEvent> $listener1;
  private StubListener<PropagatedEvent> $listener2;

  @Test
  public void isAggregateElement() {
    // basic
  }

  @Test
  public void registerAggregateElement() {
    // add listeners to the aggregate beed
    $aggregateBeed1.addListener($listener1);
    $aggregateBeed1.addListener($listener2);
    assertNull($listener1.$event);
    assertNull($listener2.$event);
    // register a beed with the aggregate beed
    $aggregateBeed1.registerAggregateElement($beed1);
    // fire a change on the registered beed
    $beed1.fire($event1);
    // listeners of the aggregate beed should be notified
    assertNotNull($listener1.$event);
    assertNotNull($listener2.$event);
    assertEquals($event1, $listener1.$event.getCause());
    assertEquals($event1, $listener1.$event.getCause());
    // reset the listeners
    $listener1.reset();
    $listener2.reset();
    // fire a change on a beed that is not registered
    $beed3.fire($event3);
    // listeners of the aggregate beed should not be notified
    assertNull($listener1.$event);
    assertNull($listener2.$event);
    // reset the listeners
    $listener1.reset();
    $listener2.reset();
    // register another beeds with the aggregate beed
    $aggregateBeed1.registerAggregateElement($beed3);
    // fire a change on the registered beeds
    $beed3.fire($event3);
    // listeners of the aggregate beed should be notified
    assertNotNull($listener1.$event);
    assertNotNull($listener2.$event);
    assertEquals($event3, $listener1.$event.getCause());
    assertEquals($event3, $listener1.$event.getCause());
  }
}
