import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.*;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    private final Burger burger = new Burger();
    @Mock
    Ingredient ingredient;
    @Mock
    Ingredient ingredientSecond;
    @Mock
    Database database;
    private final List<Bun> buns = Arrays.asList(new Bun("grey bun",100.50F));

    private final String bunName = "grey bun";
    @Before
    public void setDefaultBun() {
        Mockito.when(database.availableBuns()).thenReturn(buns);
    }

    @Test
    public void checkSetBuns() {
        burger.setBuns(database.availableBuns().get(0));
        assertEquals(bunName, burger.bun.getName());
    }

    @Test
    public void checkGetPrice() {
        Mockito.when(ingredient.getPrice()).thenReturn(125.5F);
        Mockito.when(ingredientSecond.getPrice()).thenReturn(250F);
        burger.setBuns(database.availableBuns().get(0));
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredientSecond);
        float expectedBurgerPrice = 576.5F;
        assertEquals("Некорректная цена бургера с двумя добавленными ингредиентами", expectedBurgerPrice, burger.getPrice(), 0);
    }

    @Test
    public void checkGetReceipt() {
        Mockito.when(ingredient.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(ingredient.getName()).thenReturn("hot sauce");
        Mockito.when(ingredient.getPrice()).thenReturn(100F);
        Mockito.when(ingredientSecond.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(ingredientSecond.getName()).thenReturn("sausage");
        Mockito.when(ingredientSecond.getPrice()).thenReturn(300F);
        burger.setBuns(database.availableBuns().get(0));
        burger.addIngredient(ingredient);
        burger.addIngredient(ingredientSecond);

        StringBuilder expected = new StringBuilder();
        expected.append("(==== grey bun ====)").append("\r\n");

        for (Ingredient ingredient : burger.ingredients) {
            expected.append(String.format("= %s %s =%n", ingredient.getType().toString().toLowerCase(),
                    ingredient.getName()));
        }

        expected.append(String.format("(==== grey bun ====)%n"));
        expected.append(String.format("%nPrice: %f%n", burger.getPrice()));

        assertEquals(expected.toString(), burger.getReceipt());
    }
}