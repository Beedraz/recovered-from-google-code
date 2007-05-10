package org.beedraz.semantics_II.bean;

import org.beedraz.semantics_II.bean.AbstractBeanBeed;
import org.beedraz.semantics_II.expression.association.set.EditableBidirToOneBeed;
import org.beedraz.semantics_II.expression.number.integer.long64.EditableLongBeed;

public class WellBeanBeed extends AbstractBeanBeed {

  /**
   * The run in which the well is contained.
   */
  public final EditableBidirToOneBeed<RunBeanBeed, WellBeanBeed> run =
    new EditableBidirToOneBeed<RunBeanBeed, WellBeanBeed>(this);

  /**
   * The Cq value of the well.
   */
  public final EditableLongBeed cq = new EditableLongBeed(this);

}