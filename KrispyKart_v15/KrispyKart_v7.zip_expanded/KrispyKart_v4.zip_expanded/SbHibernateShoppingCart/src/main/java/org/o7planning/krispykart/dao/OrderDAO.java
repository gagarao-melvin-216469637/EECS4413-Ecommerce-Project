package org.o7planning.krispykart.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.o7planning.krispykart.entity.Order;
import org.o7planning.krispykart.entity.OrderDetail;
import org.o7planning.krispykart.entity.Product;
import org.o7planning.krispykart.model.CartInfo;
import org.o7planning.krispykart.model.CartLineInfo;
import org.o7planning.krispykart.model.CustomerInfo;
import org.o7planning.krispykart.model.OrderDetailInfo;
import org.o7planning.krispykart.model.OrderInfo;
import org.o7planning.krispykart.pagination.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class OrderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ProductDAO productDAO;

	
	//find the maximum order number from orders. return that number.
	private int getMaxOrderNum() {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "Select max(o.orderNum) from " + Order.class.getName() + " o "; //sql query needed to get the max order.

		Query<Integer> query = session.createQuery(sql, Integer.class);
		Integer value = (Integer) query.getSingleResult();
		if (value == null) {
			return 0;
		}
		return value; 
	}
	
	
	//this function saves the information of the order from the shopping cart into the order class.
	@Transactional(rollbackFor = Exception.class)
	public void saveOrder(CartInfo cartInfo) {
		Session session = this.sessionFactory.getCurrentSession();

		Order order = new Order();
		int orderNum = this.getMaxOrderNum() + 1; // add new order to the order.

		order.setOrderDate(new Date());
		order.setAmount(cartInfo.getAmountTotal());
		order.setId(UUID.randomUUID().toString());
		order.setOrderNum(orderNum); 

		CustomerInfo customerInfo = cartInfo.getCustomerInfo(); 
		order.setCustomerPhone(customerInfo.getPhone());
		order.setCustomerAddress(customerInfo.getAddress());
		order.setCustomerName(customerInfo.getName());
		order.setCustomerEmail(customerInfo.getEmail());

		session.persist(order); //make sure the order information stays after the current session ends.

		List<CartLineInfo> lines = cartInfo.getCartLines();  

		//move all info into order detail.
		int i = 0;
		while(i < lines.size()) {
			OrderDetail od = new OrderDetail();
			od.setPrice(lines.get(i).getProductInfo().getPrice());
			od.setQuanity(lines.get(i).getQuantity());
			od.setId(UUID.randomUUID().toString());
			od.setOrder(order);
			od.setAmount(lines.get(i).getAmount());

			String code = lines.get(i).getProductInfo().getCode();
			Product product = this.productDAO.findProduct(code);
			od.setProduct(product);

			session.persist(od);
			
			i++;
		}

		// remember to set the order number to the new order number
		cartInfo.setOrderNum(orderNum);
		// Flush
		session.flush();
	}

	// @page = 1, 2, ...
	
	//show all information about orders
	public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {
		
		//sql string to find orders
		String sql = "Select new " + OrderInfo.class.getName()
				+ "(ord.id, ord.orderDate, ord.orderNum, ord.amount, "
				+ " ord.customerName, ord.customerAddress, ord.customerEmail, ord.customerPhone) " + " from "
				+ Order.class.getName() + " ord "//
				+ " order by ord.orderNum desc";

		Session session = this.sessionFactory.getCurrentSession();
		Query<OrderInfo> query = session.createQuery(sql, OrderInfo.class);
		return new PaginationResult<OrderInfo>(query, page, maxResult, maxNavigationPage);
	}
	
	
	//used to get the info on an order based of the orders id.
	public OrderInfo getOrderInfo(String orderId) {
		Order order = this.findOrder(orderId);
		if (order == null) {
			return null;
		}
		return new OrderInfo(order.getId(), order.getOrderDate(), order.getOrderNum(), order.getAmount(), order.getCustomerName(), order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());
	}
	
	
	//used to find an order placed by the order_id
	public Order findOrder(String orderId) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.find(Order.class, orderId);
	}

	//list all detailed info
	public List<OrderDetailInfo> listOrderDetails(String orderId) {
		String sql = "Select new " + OrderDetailInfo.class.getName() //
				+ "(d.id, d.product.code, d.product.name , d.quanity,d.price,d.amount) "//
				+ " from " + OrderDetail.class.getName() + " d "//
				+ " where d.order.id = :orderId ";

		Session session = this.sessionFactory.getCurrentSession();
		Query<OrderDetailInfo> query = session.createQuery(sql, OrderDetailInfo.class);
		query.setParameter("orderId", orderId);

		return query.getResultList(); 
	}

}