package com.teamdynasty.test;

import com.teamdynasty.model.Item;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Class for Item.
 * Created by Mat on 12/7/2015.
 */
public class ItemTest {

  protected Item myItem;

  @Before
  public void setUp() throws Exception {
    myItem = new Item("Test Item", 5, 5.00);
  }

  @Test
  public void testAddBidWhenLowerThanMin() throws Exception {
    assertFalse(myItem.addBid("Lower Test Bidder", 3.00));
  }

  @Test
  public void testAddBidWhenHigherThanMin() throws Exception {
    assertTrue(myItem.addBid("Higher Test Bidder", 7.00));
  }

  @Test public void testAddBidWhenEqual() throws Exception {
    assertTrue(myItem.addBid("Equal Test Bidder", 5.00));
  }
}