package org.o7planning.krispykart.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.o7planning.krispykart.dao.OrderDAO;
import org.o7planning.krispykart.dao.ProductDAO;
import org.o7planning.krispykart.entity.Product;
import org.o7planning.krispykart.form.CustomerForm;
import org.o7planning.krispykart.model.CartInfo;
import org.o7planning.krispykart.model.CustomerInfo;
import org.o7planning.krispykart.model.ProductInfo;
import org.o7planning.krispykart.pagination.PaginationResult;
import org.o7planning.krispykart.utils.Utils;
import org.o7planning.krispykart.validator.CustomerFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
public class MainController {
	
   @Autowired
   private ProductDAO productDAO;
   
   @Autowired
   private OrderDAO orderDAO;

   @Autowired
   private CustomerFormValidator customerFormValidator;

   @InitBinder
   public void myInitBinder(WebDataBinder wdb) {
      Object target = wdb.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);

      // Save customer information.
      if (target.getClass() == CustomerForm.class) {
          wdb.setValidator(customerFormValidator);
      }
      
      // Update quantity in cart
      else if (target.getClass() == CartInfo.class) {}      
   }
   
   // Return the home directory
   @RequestMapping("/")
   public String home() {
      return "index";
   }

   // Return 403 error
   @RequestMapping("/403")
   public String accessDenied() {
      return "/403";
   }

   // Returns a Product List
   @RequestMapping({ "/productList" })
   public String productListHandler(Model model,
         @RequestParam(value = "name", defaultValue = "") String likeName,
         @RequestParam(value = "page", defaultValue = "1") int page) {
     
	  final int maxResult = 5;
      final int maxNavigationPage = 10;

      PaginationResult<ProductInfo> result = productDAO.queryProducts(page, maxResult, maxNavigationPage, likeName);

      model.addAttribute("paginationProducts", result);
      return "productList";
   }

   // Buy product from shopping cart
   @RequestMapping({ "/buyProduct" })
   public String productListHandler(HttpServletRequest request, Model model,
         @RequestParam(value = "code", defaultValue = "") String code) {

      Product product = null;
      if (code != null && code.length() > 0) {
         product = productDAO.findProduct(code);
      }
      if (product != null) {

         //
         CartInfo cartInfo = Utils.getCartInSession(request);

         ProductInfo productInfo = new ProductInfo(product);

         cartInfo.addProduct(productInfo, 1);
      }

      return "redirect:/shoppingCart";
   }

   // Remove product from shopping cart
   @RequestMapping({ "/shoppingCartRemoveProduct" })
   public String removeProductHandler(HttpServletRequest request, Model model, 
                                      @RequestParam(value = "code", defaultValue = "") String code) {
      Product product = null;
      if (code != null && code.length() > 0) {
         product = productDAO.findProduct(code);
      }
      if (product != null) {

         CartInfo cartInfo = Utils.getCartInSession(request);
         ProductInfo productInfo = new ProductInfo(product);
         cartInfo.removeProduct(productInfo);

      }
      return "redirect:/shoppingCart";
   }

   // POST method to update the quantity for product in cart
   @PostMapping("/shoppingCart")
   public String updateCartQuantity(HttpServletRequest request, Model model, 
                                            @ModelAttribute("cartForm") CartInfo cartForm) {

      CartInfo cartInfo = Utils.getCartInSession(request);
      cartInfo.updateQuantity(cartForm);

      return "redirect:/shoppingCart";
   }

   // GET method to show shopping cart items
   @GetMapping("/shoppingCart")
   public String shoppingCartHandler(HttpServletRequest request, Model model) {
      CartInfo myCart = Utils.getCartInSession(request);

      model.addAttribute("cartForm", myCart);
      return "shoppingCart";
   }

   // GET method to enter customer information
   @GetMapping("/shoppingCartCustomer")
   public String customerOrderForm(HttpServletRequest request, Model model) {
      CartInfo cartInfo = Utils.getCartInSession(request);

      if (cartInfo.isEmpty()) {
         return "redirect:/shoppingCart";
      }
      
      CustomerInfo customerInfo = cartInfo.getCustomerInfo();
      CustomerForm customerForm = new CustomerForm(customerInfo);
      model.addAttribute("customerForm", customerForm);

      return "shoppingCartCustomer";
   }

   // POST method to save the customer information
   @PostMapping("/shoppingCartCustomer")
   public String saveCustomerOrder(HttpServletRequest request, Model model, 
                                   @ModelAttribute("customerForm") @Validated CustomerForm customerForm, BindingResult result, 
                                   final RedirectAttributes redirectAttributes) {

      if (result.hasErrors()) {
         customerForm.setValid(false);
         // If the result has errors then forward to re-enter customer info
         return "shoppingCartCustomer";
      }

      customerForm.setValid(true);
      CartInfo cartInfo = Utils.getCartInSession(request);
      CustomerInfo customerInfo = new CustomerInfo(customerForm);
      cartInfo.setCustomerInfo(customerInfo);

      return "redirect:/shoppingCartConfirmation";
   }

   // GET method to show the shopping cart order information to confirm
   @GetMapping("/shoppingCartConfirmation")
   public String confirmOrderDetails(HttpServletRequest request, Model model) {
      CartInfo cartInfo = Utils.getCartInSession(request);

      if (cartInfo == null || cartInfo.isEmpty()) {

         return "redirect:/shoppingCart";
      } else if (!cartInfo.isValidCustomer()) {

         return "redirect:/shoppingCartCustomer";
      }
      model.addAttribute("myCart", cartInfo);

      return "shoppingCartConfirmation";
   }

   // POST method to confirm the shopping cart order
   @PostMapping("/shoppingCartConfirmation")
   public String saveOrderDetails(HttpServletRequest request, Model model) {
      CartInfo cartInfo = Utils.getCartInSession(request);

      if (cartInfo.isEmpty()) {

         return "redirect:/shoppingCart";
      } else if (!cartInfo.isValidCustomer()) {

         return "redirect:/shoppingCartCustomer";
      }
      try {
         orderDAO.saveOrder(cartInfo);
      } catch (Exception e) {

         return "shoppingCartConfirmation";
      }

      // Remove cart from the session
      Utils.removeCartInSession(request);

      // Store the last cart
      Utils.storeLastOrderedCartInSession(request, cartInfo);

      return "redirect:/shoppingCartFinalize";
   }

   // Finalizes the shopping cart order
   @GetMapping("/shoppingCartFinalize")
   public String confirmOrder(HttpServletRequest request, Model model) {

      CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

      if (lastOrderedCart == null) {
         return "redirect:/shoppingCart";
      }
      model.addAttribute("lastOrderedCart", lastOrderedCart);
      return "shoppingCartFinalize";
   }

   // Gets the product image along with each product code
   @GetMapping("/productImage")
   public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
         @RequestParam("code") String code) throws IOException {
      Product product = null;
      if (code != null) {
         product = this.productDAO.findProduct(code);
      }
      if (product != null && product.getImage() != null) {
         response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
         response.getOutputStream().write(product.getImage());
      }
      response.getOutputStream().close();
   }
   
   @GetMapping("/registration")
   public String register(Model model) {
      return "registration";
   }
}