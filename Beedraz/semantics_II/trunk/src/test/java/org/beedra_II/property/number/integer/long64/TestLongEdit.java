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

package org.beedra_II.property.number.integer.long64;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.beedra_II.bean.BeanBeed;
import org.beedra_II.bean.StubBeanBeed;
import org.beedra_II.edit.EditStateException;
import org.beedra_II.edit.IllegalEditException;
import org.beedra_II.edit.StubValidityListener;
import org.beedra_II.edit.Edit.State;
import org.beedra_II.event.StubListener;
import org.beedra_II.property.number.integer.long64.LongEdit;
import org.beedra_II.property.number.integer.long64.LongEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLongEdit {

  @Before
  public void setUp() throws Exception {
    // NOP
  }

  @After
  public void tearDown() throws Exception {
    // NOP
  }

  BeanBeed $beanBeed = new StubBeanBeed();
  StubEditableLongBeed $target = new StubEditableLongBeed($beanBeed);
  private LongEdit $longEdit = new LongEdit($target);
  StubValidityListener $listener1 = new StubValidityListener();
  StubValidityListener $listener2 = new StubValidityListener();
  StubListener<LongEvent> $listener3 = new StubListener<LongEvent>();

  @Test
  public void constructor() {
    assertEquals($longEdit.getTarget(), $target);
  }

  @Test
  // incorrect begin-state
  public void perform1() {
    try {
      $longEdit.perform();
      $longEdit.perform();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      assertEquals(e.getEdit(), $longEdit);
      assertEquals(e.getCurrentState(), $longEdit.getState());
      assertEquals(e.getExpectedState(), State.NOT_YET_PERFORMED);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // incorrect begin-state
  public void perform2() {
    try {
      $longEdit.perform();
      $longEdit.undo();
      $longEdit.perform();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      assertEquals(e.getEdit(), $longEdit);
      assertEquals(e.getCurrentState(), $longEdit.getState());
      assertEquals(e.getExpectedState(), State.NOT_YET_PERFORMED);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // incorrect begin-state
  public void perform3() {
    try {
      $longEdit.kill();
      $longEdit.perform();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      assertEquals(e.getEdit(), $longEdit);
      assertEquals(e.getCurrentState(), $longEdit.getState());
      assertEquals(e.getExpectedState(), State.NOT_YET_PERFORMED);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // correct begin-state, check end-state
  public void perform4() {
    try {
      $longEdit.perform();
      assertEquals($longEdit.getState(), State.DONE);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // correct begin-state, validity listeners should be removed, listeners of the beed are notified
  public void perform5() {
    try {
      // add listener to beed
      $target.addListener($listener3);
      assertTrue($target.isListener($listener3));
      assertNull($listener3.$event);
      // add validity listeners to edit
      $longEdit.addValidityListener($listener1);
      $longEdit.addValidityListener($listener2);
      assertTrue($longEdit.isValidityListener($listener1));
      assertTrue($longEdit.isValidityListener($listener2));
      // perform
      Integer goal = 1;
      $longEdit.setGoal(goal);
      $longEdit.perform();
      // listeners should be removed
      assertFalse($longEdit.isValidityListener($listener1));
      assertFalse($longEdit.isValidityListener($listener2));
      // listeners of the beed are notified
      assertNotNull($listener3.$event);
      assertEquals($listener3.$event.getEdit(), $longEdit);
      assertEquals($listener3.$event.getOldInteger(), null);
      assertEquals($listener3.$event.getNewInteger(), goal);
      assertEquals($listener3.$event.getSource(), $target);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // correct begin-state, edit is no change, so validity listeners are removed, listeners of the beed are not notified
  public void perform6() {
    try {
      // add listener to beed
      $target.addListener($listener3);
      assertTrue($target.isListener($listener3));
      assertNull($listener3.$event);
      // add validity listeners to edit
      $longEdit.addValidityListener($listener1);
      $longEdit.addValidityListener($listener2);
      assertTrue($longEdit.isValidityListener($listener1));
      assertTrue($longEdit.isValidityListener($listener2));
      // perform
      $longEdit.setGoal(null);
      $longEdit.perform();
      // listeners are removed
      assertFalse($longEdit.isValidityListener($listener1));
      assertFalse($longEdit.isValidityListener($listener2));
      // listeners of the beed are not notified
      assertNull($listener3.$event);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // when the edit is not valid, an exception should be thrown
  public void perform7() {
    try {
      LongEdit edit = new LongEdit($target);
      Integer goal = 1;
      edit.setGoal(goal);
      edit.perform();
      // perform
      $longEdit.setGoal(null);
      $longEdit.perform();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      assertEquals(e.getEdit(), $longEdit);
      assertEquals(e.getMessage(), null);
    }
  }

  @Test
  // check whether the initial state is stored
  public void perform8() {
    try {
      // perform
      LongEdit edit1 = new LongEdit($target);
      assertNull(edit1.getInitial());
      Integer goal1 = 1;
      edit1.setGoal(goal1);
      edit1.perform();
      assertNull(edit1.getInitial());
      // perform
      LongEdit edit2 = new LongEdit($target);
      assertNull(edit2.getInitial());
      Integer goal2 = 2;
      edit2.setGoal(goal2);
      edit2.perform();
      assertEquals(edit2.getInitial(), goal1);
      // perform - no change
      LongEdit edit3 = new LongEdit($target);
      assertNull(edit3.getInitial());
      edit3.setGoal(goal2);
      edit3.perform();
      assertEquals(edit3.getInitial(), goal2);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // check whether the goal is stored in the beed
  public void perform9() {
    try {
      // perform
      Integer goal = 1;
      $longEdit.setGoal(goal);
      $longEdit.perform();
      assertEquals($target.get(), goal);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // incorrect begin-state
  public void undo1() {
    try {
      $longEdit.undo();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      assertEquals(e.getEdit(), $longEdit);
      assertEquals(e.getCurrentState(), $longEdit.getState());
      assertEquals(e.getExpectedState(), State.DONE);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // incorrect begin-state
  public void undo2() {
    try {
      $longEdit.perform();
      $longEdit.undo();
      $longEdit.undo();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      assertEquals(e.getEdit(), $longEdit);
      assertEquals(e.getCurrentState(), $longEdit.getState());
      assertEquals(e.getExpectedState(), State.DONE);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // incorrect begin-state
  public void undo3() {
    try {
      $longEdit.kill();
      $longEdit.undo();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      assertEquals(e.getEdit(), $longEdit);
      assertEquals(e.getCurrentState(), $longEdit.getState());
      assertEquals(e.getExpectedState(), State.DONE);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // correct begin-state, check end-state
  public void undo4() {
    try {
      Integer goal = 1;
      $longEdit.setGoal(goal);
      $longEdit.perform();
      $longEdit.undo();
      assertEquals($longEdit.getState(), State.UNDONE);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // correct begin-state, so there are no validity listeners, listeners of the beed are notified
  public void undo5() {
    try {
      // add listener to beed
      $target.addListener($listener3);
      assertTrue($target.isListener($listener3));
      assertNull($listener3.$event);
      // add validity listeners to edit
      $longEdit.addValidityListener($listener1);
      $longEdit.addValidityListener($listener2);
      assertTrue($longEdit.isValidityListener($listener1));
      assertTrue($longEdit.isValidityListener($listener2));
      // perform
      Integer goal = 1;
      $longEdit.setGoal(goal);
      $longEdit.perform();
      assertFalse($longEdit.isValidityListener($listener1));
      assertFalse($longEdit.isValidityListener($listener2));
      $longEdit.undo();
      // there are no listeners
      assertFalse($longEdit.isValidityListener($listener1));
      assertFalse($longEdit.isValidityListener($listener2));
      // listeners of the beed are notified
      assertNotNull($listener3.$event);
      assertEquals($listener3.$event.getSource(), $target);
      assertEquals($listener3.$event.getOldInteger(), goal);
      assertEquals($listener3.$event.getNewInteger(), null);
      assertEquals($listener3.$event.getEdit(), $longEdit);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // when the goal state does not match the current state, an exception should be thrown
  public void undo7() {
    LongEdit edit1 = null;
    try {
      // edit1
      edit1 = new LongEdit($target);
      Integer goal1 = 1;
      edit1.setGoal(goal1);
      edit1.perform();
      // edit2
      LongEdit edit2 = new LongEdit($target);
      Integer goal2 = 2;
      edit2.setGoal(goal2);
      edit2.perform();
      // undo edit1
      edit1.undo();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      assertEquals(e.getEdit(), edit1);
      assertEquals(e.getMessage(), null);
    }
  }

  @Test
  // is the value of the beed set to the original value?
  public void undo8() {
    try {
      // edit1
      LongEdit edit1 = new LongEdit($target);
      Integer goal1 = 1;
      edit1.setGoal(goal1);
      edit1.perform();
      // $simpleEdit
      Integer goal2 = 2;
      $longEdit.setGoal(goal2);
      $longEdit.perform();
      $longEdit.undo();
      assertEquals($target.get(), goal1);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // incorrect begin-state
  public void redo1() {
    try {
      $longEdit.redo();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      assertEquals(e.getEdit(), $longEdit);
      assertEquals(e.getCurrentState(), $longEdit.getState());
      assertEquals(e.getExpectedState(), State.UNDONE);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // incorrect begin-state
  public void redo2() {
    try {
      $longEdit.perform();
      $longEdit.redo();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      assertEquals(e.getEdit(), $longEdit);
      assertEquals(e.getCurrentState(), $longEdit.getState());
      assertEquals(e.getExpectedState(), State.UNDONE);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // incorrect begin-state
  public void redo3() {
    try {
      $longEdit.kill();
      $longEdit.redo();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      assertEquals(e.getEdit(), $longEdit);
      assertEquals(e.getCurrentState(), $longEdit.getState());
      assertEquals(e.getExpectedState(), State.UNDONE);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // correct begin-state, check end-state
  public void redo4() {
    try {
      $longEdit.perform();
      $longEdit.undo();
      $longEdit.redo();
      assertEquals($longEdit.getState(), State.DONE);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // correct begin-state, so there are no validity listeners, listeners of the beed are notified
  public void redo5() {
    try {
      // add listener to beed
      $target.addListener($listener3);
      assertTrue($target.isListener($listener3));
      assertNull($listener3.$event);
      // add validity listeners to edit
      $longEdit.addValidityListener($listener1);
      $longEdit.addValidityListener($listener2);
      assertTrue($longEdit.isValidityListener($listener1));
      assertTrue($longEdit.isValidityListener($listener2));
      // perform
      Integer goal = 1;
      $longEdit.setGoal(goal);
      $longEdit.perform();
      assertFalse($longEdit.isValidityListener($listener1));
      assertFalse($longEdit.isValidityListener($listener2));
      $longEdit.undo();
      $longEdit.redo();
      // there are no listeners
      assertFalse($longEdit.isValidityListener($listener1));
      assertFalse($longEdit.isValidityListener($listener2));
      // listeners of the beed are notified
      assertNotNull($listener3.$event);
      assertEquals($listener3.$event.getEdit(), $longEdit);
      assertEquals($listener3.$event.getOldInteger(), null);
      assertEquals($listener3.$event.getNewInteger(), goal);
      assertEquals($listener3.$event.getSource(), $target);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  // when the goal state does not match the current state, an exception should be thrown
  public void redo7() {
    LongEdit edit1 = null;
    try {
      // edit1
      edit1 = new LongEdit($target);
      Integer goal1 = 1;
      edit1.setGoal(goal1);
      edit1.perform();
      edit1.undo();
      // edit2
      LongEdit edit2 = new LongEdit($target);
      Integer goal2 = 2;
      edit2.setGoal(goal2);
      edit2.perform();
      // redo edit1
      edit1.redo();
      // should not be reached
      assertTrue(false);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      assertEquals(e.getEdit(), edit1);
      assertEquals(e.getMessage(), null);
    }
  }

  @Test
  // is the value of the beed set to the goal value?
  public void redo8() {
    try {
      // edit1
      LongEdit edit1 = new LongEdit($target);
      Integer goal1 = 1;
      edit1.setGoal(goal1);
      edit1.perform();
      // $simpleEdit
      Integer goal2 = 2;
      $longEdit.setGoal(goal2);
      $longEdit.perform();
      $longEdit.undo();
      $longEdit.redo();
      assertEquals($target.get(), goal2);
    }
    catch (EditStateException e) {
      // should not be reached
      assertTrue(false);
    }
    catch (IllegalEditException e) {
      // should not be reached
      assertTrue(false);
    }
  }

  @Test
  public void createEvent() throws EditStateException, IllegalEditException {
    assertEquals(State.NOT_YET_PERFORMED, $longEdit.getState());
//    LongEvent createdEvent = $longEdit.createEvent();
//    assertEquals(createdEvent.getEdit(), $longEdit);
//    assertEquals(createdEvent.getOldValue(), null);
//    assertEquals(createdEvent.getNewValue(), null);
//    assertEquals(createdEvent.getSource(), $target);
    // perform
    Integer goal = 1;
    $longEdit.setGoal(goal);
    $longEdit.perform();
    // create event
    LongEvent createdEvent = $longEdit.createEvent();
    assertEquals(createdEvent.getEdit(), $longEdit);
    assertEquals(createdEvent.getOldInteger(), null);
    assertEquals(createdEvent.getNewInteger(), goal);
    assertEquals(createdEvent.getSource(), $target);
    // undo
    $longEdit.undo();
    // create event
    createdEvent = $longEdit.createEvent();
    assertEquals(createdEvent.getEdit(), $longEdit);
    assertEquals(createdEvent.getOldInteger(), goal);
    assertEquals(createdEvent.getNewInteger(), null);
    assertEquals(createdEvent.getSource(), $target);
  }

}