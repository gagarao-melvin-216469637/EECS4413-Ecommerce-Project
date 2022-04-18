package org.o7planning.krispykart.controller;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.o7planning.krispykart.dao.OrderDAO;
import org.o7planning.krispykart.dao.ProductDAO;
import org.o7planning.krispykart.dao.RegistrationDAO;
import org.o7planning.krispykart.entity.Product;
import org.o7planning.krispykart.entity.Registration;
import org.o7planning.krispykart.form.ProductForm;
import org.o7planning.krispykart.form.RegistrationForm;
import org.o7planning.krispykart.model.OrderDetailInfo;
import org.o7planning.krispykart.model.OrderInfo;
import org.o7planning.krispykart.pagination.PaginationResult;
import org.o7planning.krispykart.validator.ProductFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
   
   // allows for Spring to resolve and inject collaborating beans into this bean using RegistrationDAO registrationDAO
   @Autowired
   private RegistrationDAO registrationDAO;

   // allows for Spring to resolve and inject collaborating beans into this bean using ProductFormValidator productFormValidator
   @Autowired
   private ProductFormValidator productFormValidator;

   //used for populating command and form object arguments of annotated handler methods.
   @InitBinder
   public void myInitBinder(WebDataBinder wdb) {
      Object target = wdb.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);

      if (target.getClass() == ProductForm.class) {
         wdb.setValidator(productFormValidator);
      }
   }

   // Display the Login page to the potential admin
   @GetMapping("/admin/login")
   public String login(Model model) {
      return "login";
   }
   
   @GetMapping("/admin/registration")
   public String register(Model model) {
      return "registration";
   }

   // Display the account details to the admin upon successful login
   @GetMapping("/admin/accountInfo")
   public String accountDetails(Model m) {

      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      System.out.println(userDetails.getPassword());
      System.out.println(userDetails.getUsername());
      System.out.println(userDetails.isEnabled());

      m.addAttribute("userDetails", userDetails);
      return "accountInfo";
   }

   // display the list of items ordered to the admin
   @GetMapping("/admin/orderList")
   public String orderList(Model m, 
      @RequestParam(value = "page", defaultValue = "1") String pages) {
      int page = 1;
      try {
         page = Integer.parseInt(pages);
      } catch (Exception e) {
      }
      final int MAX_RESULT = 5;
      final int MAX_NAVIGATION_PAGE = 10;

      PaginationResult<OrderInfo> paginationResult = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

      m.addAttribute("paginationResult", paginationResult);
      return "orderList";
   }

   // GET method used to display product details
   @GetMapping("/admin/product")
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
   
   @GetMapping("/admin/registrationForm")
   public String registration(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
      RegistrationForm regForm = null;

      if (code != null && code.length() > 0) {
         Registration registration = registrationDAO.findRegistration(code);
         if (registration != null) {
            regForm = new RegistrationForm(registration);
         }
      }
      if (regForm == null) {
         regForm = new RegistrationForm();
         regForm.setNewAccount(true);
      }
      model.addAttribute("registrationForm", regForm);
      return "registration";
   }

   // POST method used to save the products details. 
   // Used when the admin wants to add a new product to the website.
   @PostMapping("/admin/product")
   public String productIsSaved(Model m,
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
         m.addAttribute("errorMessage", message);
         return "product";
      }

      return "redirect:/productList";
   }

   // GET method used to display order details
   @GetMapping("/admin/order")
   public String orderDetails(Model model, @RequestParam("orderId") String orderId) {
      OrderInfo orderInfo = null;
      if (orderId != null) {
         orderInfo = this.orderDAO.getOrderInfo(orderId);
      }
      if (orderInfo == null) {
         return "redirect:/admin/orderList";
      }
      List<OrderDetailInfo> details = this.orderDAO.listOrderDetails(orderId);
      orderInfo.setDetails(details);

      model.addAttribute("orderInfo", orderInfo);

      return "order";
   }

}