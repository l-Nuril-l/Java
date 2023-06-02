import org.junit.*;

import static org.hamcrest.CoreMatchers.equalTo;

public class CalculatorTest {

        @BeforeClass
        public static void globalSetup()
        {

        }

        @Before
        public void setUp()
        {
        }

        @Test
        public void add()
        {
            var res = Calculator.addition(1,4);
            Assert.assertThat(res, equalTo(5));
        }
        @Test
        public void div()
        {
            var res = Calculator.division(8,2);
            Assert.assertThat(res, equalTo(4));
        }
        @Test
        public void min()
        {
            var res = Calculator.subtraction(77,6);
            Assert.assertThat(res, equalTo(71));
        }
        @Test
        public void mul()
        {
            var res = Calculator.multiplication(5,5);
            Assert.assertThat(res, equalTo(25));
        }

    @Test
    public void minimum()
    {
        var res = Calculator.minimum(64,87);
        Assert.assertThat(res, equalTo(Math.min(64,87)));
    }
    @Test
    public void maximum()
    {
        var res = Calculator.maximum(64,87);
        Assert.assertThat(res, equalTo(Math.max(64,87)));
    }
    @Test
    public void pow()
    {
        var res = Calculator.pow(54,5);
        Assert.assertThat(res, equalTo(Math.pow(54,5)));
    }

    @Test
    public void percent()
    {
        var res = Calculator.pow(50,10);
        Assert.assertThat(res, equalTo(5));
    }

        @AfterClass
        public static void tearDown()
        {

        }

        @After
        public void afterMethod()
        {

        }



}
