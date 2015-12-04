package com.teamdynasty.view;

import com.teamdynasty.model.AuctionCalendar;
import com.teamdynasty.model.User;

import java.util.Scanner;

/**Interface in order to use polymorphism in userMenu() method of AuctionCentralMainView.
 * Created by Mat on 12/3/2015.
 */
public interface IUserView {

  void Options(Scanner theScanner, AuctionCalendar theCalendar, User theUser);

}
