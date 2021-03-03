package DaoTests;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.my.restaurant.dao.DbUtil;
import com.my.restaurant.dao.UsersDao;
import com.my.restaurant.models.Users;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest {
    DbUtil dbManager;
    private UsersDao usersDao = new UsersDao();

    @Before
    public void setTestTrue() {
        dbManager= DbUtil.getInstance(true);
        dbManager.getTest();
    }

    @Test
    public void testRegister() {
        Users user = new Users();
        user.setLogin("j");
        user.setPassword(BCrypt.withDefaults().hashToString(12, "j".toCharArray()));
        Integer rowsAffected = usersDao.insertUser(user);
        Integer expected = 1;
        Assert.assertEquals(expected, rowsAffected);
    }

    @Test
    public void testSValidation() {
        Users user = usersDao.validate("j", "j");
        Assert.assertNotNull(user);
    }

    @Test
    public void testUserDelete() {
        Users user = new Users();
        user.setLogin("j");
        Integer rowsAffected = usersDao.deleteUser(user);
        Integer expected = 1;
        Assert.assertEquals(expected, rowsAffected);
    }



}
