package DaoTests;

import com.my.restaurant.dao.DbUtil;
import com.my.restaurant.dao.DishesDao;
import com.my.restaurant.models.Dishes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DishesDaoTest {
    DbUtil dbManager;
    private DishesDao dishesDao = new DishesDao();

    @Before
    public void setTestTrue() {
        dbManager= DbUtil.getInstance(true);
        dbManager.getTest();
    }

    @Test
    public void findAllDishes(){
        List<Dishes> dishesList = dishesDao.findAllDishes();
        Assert.assertNotNull(dishesList);
    }

    @Test
    public void findDishById(){
        Dishes dish = dishesDao.findDishByDishId(4);
        Integer expected = 4;
        Assert.assertEquals(expected, dish.getId());
    }
}
