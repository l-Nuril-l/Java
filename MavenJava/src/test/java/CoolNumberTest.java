import org.hamcrest.CoreMatchers;
import org.junit.*;

import javax.xml.validation.Validator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class CoolNumberTest {
    CoolNumber cn = null;
    @BeforeClass
    public static void globalSetup()
    {
        System.out.println("Init");
    }

    @Before
    public void setUp()
    {
        cn = new CoolNumber(10);
    }

    @Test
    public void set()
    {

    }

    @Test
    public void get()
    {
        Assert.assertThat(cn.getVeryCoolNum(), equalTo(10));
    }

    @Test
    public void to8()
    {
        Assert.assertThat(cn.to8(), equalTo("12"));
    }
    @Test
    public void to16()
    {
        Assert.assertThat(cn.to16(), equalTo("a"));
    }
    @Test
    public void to2()
    {
        Assert.assertThat(cn.to2(), equalTo("1010"));
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
