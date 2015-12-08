package com.teamdynasty.test;

import com.teamdynasty.model.Auction;
import com.teamdynasty.model.Item;
import com.teamdynasty.model.NonProfit;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * NonProfit JUnit test.
 * Created by Mat on 12/7/2015.
 */
public class NonProfitTest {

  protected NonProfit npo;

  @Before
  public void setUp() throws Exception {
    npo = new NonProfit("NPO Test", "Test Org");
  }

  @Test
  public void testScheduleAuctionWhenNoAuction() throws Exception {
    assertNull(npo.scheduleAuction());
  }

  @Test
  public void testScheduleAuctionWhenHasAuction() throws Exception {
    npo.setMyAuction(new Auction(npo, LocalDateTime.now().plusDays(2)));
    assertNotNull(npo.scheduleAuction());
  }

  @Test
  public void testAddItemToInventory() throws Exception {
    Item newItem = new Item("Test Item", 15, 5.00);
    npo.addItemToInventory(newItem);
    assertTrue(npo.getMyInventory().contains(newItem));
  }


}