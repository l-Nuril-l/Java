import org.junit.*;

import static org.hamcrest.CoreMatchers.equalTo;

public class BadlistTest {
    BadList bl = null;
    @BeforeClass
    public static void globalSetup()
    {
        System.out.println("Init");
    }

    @Before
    public void setUp()
    {
        bl = new BadList(new int[] {5,10,15,20});
    }

    @Test
    public void avg()
    {
        Assert.assertThat(bl.avg(), equalTo(50/4));
    }

    @Test
    public void sum()
    {
        Assert.assertThat(bl.sum(), equalTo(50));
    }

    @Test
    public void min()
    {
        Assert.assertThat(bl.min(), equalTo(5));
    }
    @Test
    public void max()
    {
        Assert.assertThat(bl.max(), equalTo(20));
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
