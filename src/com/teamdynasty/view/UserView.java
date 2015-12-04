package com.teamdynasty.view;

import com.teamdynasty.model.AuctionCalendar;
import com.teamdynasty.model.User;

import java.util.Scanner;

/**
 * Created by Mat on 12/3/2015.
 */
public abstract class UserView {

  public abstract void Options(Scanner theScanner, AuctionCalendar theCalendar, User theUser);


}
