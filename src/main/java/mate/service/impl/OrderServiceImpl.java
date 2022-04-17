package mate.service.impl;

import java.util.List;
import mate.dao.OrderDao;
import mate.lib.Dao;
import mate.lib.Inject;
import mate.model.Order;
import mate.model.ShoppingCart;
import mate.model.Ticket;
import mate.model.User;
import mate.service.OrderService;
import mate.service.ShoppingCartService;

@Dao
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        if (shoppingCart.getTickets().isEmpty()) {
            throw new RuntimeException("Sorry, this shopping cart hasn't got any tickets");
        }
        Order order = new Order();
        User user = shoppingCart.getUser();
        List<Ticket> tickets = shoppingCart.getTickets();
        order.setUser(user);
        order.setTicketList(tickets);
        shoppingCartService.clearShoppingCart(shoppingCart);
        return orderDao.add(order);
    }

    @Override
    public List<Order> getOrdersHistory(User user) {
        return orderDao.getHistoryByUser(user);
    }
}
