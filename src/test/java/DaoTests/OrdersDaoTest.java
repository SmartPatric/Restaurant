package DaoTests;

import com.my.restaurant.dao.DbUtil;
import com.my.restaurant.dao.OrdersDao;
import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Orders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdersDaoTest {
    DbUtil dbManager;
    private final Integer USER_ID = 172;
    private OrdersDao ordersDao = new OrdersDao();
    private UsersDao usersDao = new UsersDao();
    private static Orders order;

    @Before
    public void setTestTrue() {
        dbManager = DbUtil.getInstance(true);
        dbManager.getTest();
    }

    @Test
    public void AcreateOrder() {
        Orders order = ordersDao.createNewOrder(USER_ID);
        Assert.assertNotNull(order);
    }

    @Test
    public void BfindOrderByUserId() {
        order = ordersDao.findOrderByUserId(USER_ID);
        Integer userId = USER_ID;
        Assert.assertEquals(userId, order.getUserId());

        ordersDao.payOrder(order.getId());
        order = ordersDao.findOrderByUserId(USER_ID);
        Assert.assertEquals("APPROVING", order.getStatus());

        ordersDao.nextStatus(USER_ID);
        order = ordersDao.findOrderByUserId(USER_ID);
        Assert.assertEquals("COOKING", order.getStatus());
    }

    @Test
    public void CchangePrice() {
        ordersDao.changePrice(true, 10.0, order.getId());
        order = ordersDao.findOrderByUserId(USER_ID);
        Double price = 10.0;
        Assert.assertEquals(price, order.getTotal());

        ordersDao.changePrice(false, 10.0, order.getId());
        order = ordersDao.findOrderByUserId(USER_ID);
        price = 0d;
        Assert.assertEquals(price, order.getTotal());
    }

    @Test
    public void DcancelOrder() {
        ordersDao.cancelOrder(order.getId());
        order = ordersDao.findOrderByUserId(USER_ID);
        Assert.assertNull(order);
    }

    @Test
    public void EdeleteOrder() {
        Integer rowsAffected = ordersDao.deleteOrder(USER_ID);
        Integer notExpected = 0;
        Assert.assertNotEquals(notExpected, rowsAffected);
    }

    @Test
    public void findOrders() {
        List<Orders> ordersList = ordersDao.findAllOrders("CLOSED");
        Assert.assertNotNull(ordersList);
    }
}
