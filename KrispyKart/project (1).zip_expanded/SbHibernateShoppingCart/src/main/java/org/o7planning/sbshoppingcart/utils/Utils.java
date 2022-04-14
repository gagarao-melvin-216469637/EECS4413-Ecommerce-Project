package org.o7planning.sbshoppingcart.utils;

import javax.servlet.http.HttpServletRequest;

import org.o7planning.sbshoppingcart.model.CartInfo;

public class Utils {

   // set the value of whatver is in the cart from the session
   public static CartInfo getCartInSession(HttpServletRequest request) {

      CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart"); //get the value of myCart from the session

      //if there is not information about the cart in the session, create a new cart and set the myCart attribute to the cart 
      if (cartInfo == null) {
    	  System.out.println("the cart info was null");
      }
      else {
    	  cartInfo = new CartInfo();
          
          request.getSession().setAttribute("myCart", cartInfo); //set the session attribute for the cart info
      }

      return cartInfo; //returns the information stored in the attribute myCart
   }

  
   //sets the lastOrderedCart attribute to be the latest cart information
   public static void storeLastOrderedCartInSession(HttpServletRequest request, CartInfo cartInfo) {
      request.getSession().setAttribute("lastOrderedCart", cartInfo);
   }
   
   public static CartInfo getLastOrderedCartInSession(HttpServletRequest request) {
      return (CartInfo) request.getSession().getAttribute("lastOrderedCart");
   }
   	
   //removes myCart attribute from session
   public static void removeCartInSession(HttpServletRequest request) {
      request.getSession().removeAttribute("myCart");  
   }
   
}