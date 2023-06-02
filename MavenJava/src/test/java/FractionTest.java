import org.junit.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class FractionTest {
    @BeforeClass
    public static void globalSetup()
    {

    }

    @Before
    public void setUp()
    {
    }

    @Test
    public void mul()
    {
        var res = Fraction.mul(new Fraction(2,4),new Fraction(1,2));
        Assert.assertThat(res, equalTo(new Fraction(1,4)));
    }
    @Test
    public void div()
    {
        var res = Fraction.div(new Fraction(2,4),new Fraction(1,2));
        Assert.assertThat(res, equalTo(new Fraction(1,1)));
    }
    @Test
    public void min()
    {
        var res = Fraction.min(new Fraction(2,4),new Fraction(1,2));
        Assert.assertThat(res, equalTo(new Fraction(0,1)));
    }
    @Test
    public void add()
    {
        var res = Fraction.add(new Fraction(2,4),new Fraction(1,2));
        Assert.assertThat(res, equalTo(new Fraction(1,1)));
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
