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

package org.beedraz.semantics_II.expression.number.integer.long64;


import static org.junit.Assert.assertEquals;
import static org.ppwcode.metainfo_I.License.Type.APACHE_V2;

import org.beedraz.semantics_II.EditStateException;
import org.beedraz.semantics_II.IllegalEditException;
import org.beedraz.semantics_II.aggregate.AggregateBeed;
import org.beedraz.semantics_II.aggregate.StubAggregateBeed;
import org.beedraz.semantics_II.expression.number.integer.IntegerEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ppwcode.metainfo_I.Copyright;
import org.ppwcode.metainfo_I.License;
import org.ppwcode.metainfo_I.vcs.SvnInfo;


@Copyright("2007 - $Date$, Beedraz authors")
@License(APACHE_V2)
@SvnInfo(revision = "$Revision$",
         date     = "$Date$")
public class TestLongEvent {

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
    AggregateBeed owner = new StubAggregateBeed();
    LongBeed source = new EditableLongBeed(owner);
    // old and new value
    Long oldValue = 0L;
    Long newValue = 1L;
    // edit
    EditableLongBeed target = new EditableLongBeed(owner);
    LongEdit edit = new LongEdit(target);
    edit.perform();
    // test constructor
    IntegerEvent integerEvent = new ActualLongEvent(source, oldValue, newValue, edit);
    assertEquals(integerEvent.getSource(), source);
    assertEquals(integerEvent.getOldLong(), oldValue);
    assertEquals(integerEvent.getNewLong(), newValue);
    assertEquals(integerEvent.getEdit(), edit);
    assertEquals(integerEvent.getEditState(), edit.getState());
    assertEquals(integerEvent.getLongDelta(), newValue - oldValue);
    // old and new value
    oldValue = null;
    newValue = 4L;
    // test constructor
    integerEvent = new ActualLongEvent(source, oldValue, newValue, edit);
    assertEquals(integerEvent.getSource(), source);
    assertEquals(integerEvent.getOldLong(), null);
    assertEquals(integerEvent.getNewLong(), 4L);
    assertEquals(integerEvent.getEdit(), edit);
    assertEquals(integerEvent.getEditState(), edit.getState());
    assertEquals(integerEvent.getLongDelta(), null);
    // edit is null
    edit = null;
    // test constructor
    integerEvent = new ActualLongEvent(source, oldValue, newValue, edit);
    assertEquals(integerEvent.getSource(), source);
    assertEquals(integerEvent.getOldLong(), null);
    assertEquals(integerEvent.getNewLong(), 4L);
    assertEquals(integerEvent.getEdit(), null);
    assertEquals(integerEvent.getEditState(), null);
  }

}