package com.teamdynasty.model;

import java.io.Serializable;

/**Superclass for the different types of users.
 * Created by Mat on 11/28/2015.
 */
public class User implements Serializable {

  //public static final int IACEMPLOYEE = 1;
  public static final int INPO = 2;
  //public static final int IBIDDER = 3;

  private String myUsername;
  private int myType; //1 = ACE, 2 = NPO, 3 = Bidder

  public User(final String theUsername, final int userType) {
    myUsername = theUsername;
    myType = userType;
  }

  public String getUsername() {
    return myUsername;
  }

  public int getUserType() {
    return myType;
  }

  @Override
  public String toString() {
    return myUsername;
  }

}
