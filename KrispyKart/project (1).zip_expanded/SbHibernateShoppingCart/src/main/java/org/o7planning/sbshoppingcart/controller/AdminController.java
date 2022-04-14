package org.o7planning.sbshoppingcart.controller;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.o7planning.sbshoppingcart.dao.OrderDAO;
import org.o7planning.sbshoppingcart.dao.ProductDAO;
import org.o7planning.sbshoppingcart.entity.Product;
import org.o7planning.sbshoppingcart.form.ProductForm;
import org.o7planning.sbshoppingcart.model.OrderDetailInfo;
import org.o7planning.sbshoppingcart.model.OrderInfo;
import org.o7planning.sbshoppingcart.pagination.PaginationResult;
import org.o7planning.sbshoppingcart.validator.ProductFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
public class AdminController {

	// allows for Spring to resolve and inject collaborating beans into this bean using OrderDAO orderDAO
   @Autowired
   private OrderDAO orderDAO;

   // allows for Spring to resolve and inject collaborating beans into this bean using ProductDAO productDAO
   @Autowired
   private ProductDAO productDAO;

   // allows for Spring to resolve and inject collaborating beans into this bean using ProductFormValidator productFormValidator
   @Autowired
   private ProductFormValidator productFormValidator;

   //used for populating command and form object arguments of annotated handler methods.
   @InitBinder
   public void myInitBinder(WebDataBinder binder) {
      Object target = binder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);

      if (target.getClass() == ProductForm.class) {
         binder.setValidator(productFormValidator);
      }
   }

   // Display the Login page to the potential admin
   @RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
   public String login(Model model) {
      return "login";
   }

   // Display the account details to the admin upon successful login
   @RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
   public String accountInfo(Model model) {

      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      System.out.println(userDetails.getPassword());
      System.out.println(userDetails.getUsername());
      System.out.println(userDetails.isEnabled());

      model.addAttribute("userDetails", userDetails);
      return "accountInfo";
   }

   // display the list of items ordered to the admin
   @RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
   public String orderList(Model model, //
         @RequestParam(value = "page", defaultValue = "1") String pageStr) {
      int page = 1;
      try {
         page = Integer.parseInt(pageStr);
      } catch (Exception e) {
      }
      final int MAX_RESULT = 5;
      final int MAX_NAVIGATION_PAGE = 10;

      PaginationResult<OrderInfo> paginationResult = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

      model.addAttribute("paginationResult", paginationResult);
      return "orderList";
   }

   // GET method used to display product details
   @RequestMapping(value = { "/admin/product" }, method = RequestMethod.GET)
   public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
      ProductForm productForm = null;

      if (code != null && code.length() > 0) {
         Product product = productDAO.findProduct(code);
         if (product != null) {
            productForm = new ProductForm(product);
         }
      }
      if (productForm == null) {
         productForm = new ProductForm();
         productForm.setNewProduct(true);
      }
      model.addAttribute("productForm", productForm);
      return "product";
   }

   // POST method used to save the products details. 
   // Used when the admin wants to add a new product to the website.
   @RequestMapping(value = { "/admin/product" }, method = RequestMethod.POST)
   public String productSave(Model model,
         @ModelAttribute("productForm") @Validated ProductForm productForm,
         BindingResult result,
         final RedirectAttributes redirectAttributes) {

      if (result.hasErrors()) {
         return "product";
      }
      try {
         productDAO.save(productForm);
      } catch (Exception e) {
         Throwable rootCause = ExceptionUtils.getRootCause(e);
         String message = rootCause.getMessage();
         model.addAttribute("errorMessage", message);
         return "product";
      }

      return "redirect:/productList";
   }

   // GET method used to display order details
   @RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
   public String orderView(Model model, @RequestParam("orderId") String orderId) {
      OrderInfo orderInfo = null;
      if (orderId != null) {
         orderInfo = this.orderDAO.getOrderInfo(orderId);
      }
      if (orderInfo == null) {
         return "redirect:/admin/orderList";
      }
      List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
      orderInfo.setDetails(details);

      model.addAttribute("orderInfo", orderInfo);

      return "order";
   }

}