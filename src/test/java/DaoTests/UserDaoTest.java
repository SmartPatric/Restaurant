package DaoTests;

import com.my.restaurant.dao.DbUtil;
import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Users;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class UserDaoTest {

    DbUtil dbManager;

    @BeforeEach
    public void setTestTrue() {
        dbManager= DbUtil.getInstance(true);
        dbManager.getTest();
    }

    @Test
    public void testRegister() {
        Users user = new Users();
        user.setLogin("j");
        user.setPassword("j");

        UsersDao usersDao = new UsersDao();

        Integer rowsAffected = usersDao.insertUser(user);
        Integer expected = 1;
        Assert.assertEquals(expected, rowsAffected);
    }

    @AfterEach
    public void setTestFalse() {
    }
}
