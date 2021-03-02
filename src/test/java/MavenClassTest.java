import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Notes
/*
setting the java version in maven
<properties>
<maven.compiler.target>1.8</maven.compiler.target>
<maven.compiler.source>1.8</maven.compiler.source>
</properties>
*/
// BeforeAll and AfterAll methods has to be static, because they are run before the instance of class is created
// Instances are created for every @Test and terminated afterwards
// Annotations: @DisplayName("Name of test") to give names to tests instead default method name
// @Disabled - skips the test
// @EnabledOnOs(OS.LINUX) - test runs only on specific OS ; @EnabledOnJRE(JRE.JAVA_11) specific JRE version
// assumeTrue(value); Hey Junit, dont continue running the test if the assumption is not true (ex: server has to be online)
// Added @ParameterizedTest
// Assertions.assertAll - instead of writing @Test for every case we want to test, we can use assertAll and list all the
// cases inside. Without it, it would run only until first failed test and the rest wouldnt run
// @RepeatedTest(10) will run the test 10 times
// @EnabledIf(name_of_method) vs @DisabledIf(name_of_method)
// @Timeout(1)  .... in seconds, how long should the test maximally take
// @Timetout(value = 1000, unit = TimeUnit.MILLISECONDS)  // Thread.sleep(1500L); in ms //System.currentTimeMilliseconds
// above test class:  @TestMethodOrder(MethodOrderer.DisplaName.class) alphabetical order of display names methods
//                    @TestMethodOrder(MethodOrderer.MethodName.class) alphabetical order of method names
//                    @TestMethodOrder(MethodOrderer.Random.class) random order of tests
// or with every @Test we can use @Order(100)  @Order(200) etc. smaller is first
class MavenClassTest {
    MavenClass mavenClass;

    @BeforeEach
    void init() {
        mavenClass = new MavenClass();
    }
    @Test
    @DisplayName("Testing add class with small values.")
    void testAdd() {
        int expected = 6;
        int actual = mavenClass.add(3, 3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    // different way of testing for exceptions
    void testAdd2() {
        Assertions.assertThrows(ArithmeticException.class, () -> mavenClass.add(Integer.MAX_VALUE, Integer.MAX_VALUE),
                "Overflow should throw arith exception.");
    }

    @Test
    @Disabled
    void testSkipped() {
        Assertions.assertThrows(ArithmeticException.class, () -> mavenClass.add(Integer.MAX_VALUE, Integer.MAX_VALUE),
                "Overflow should throw arith exception.");
    }

    @ParameterizedTest
    @CsvSource({"1,2,3", "1,5,6"})
    void testMoreAddCases(int a, int b, int add) {
        Assertions.assertEquals(add, mavenClass.add(a, b));
    }

    @Test
    void testAdd3() {
        // all tests will run, even if some fail, we can see 2 failed (2 should fail)
        Assertions.assertAll("adding",
                () -> Assertions.assertEquals(5, mavenClass.add(2, 3)),
                () -> Assertions.assertEquals(5, mavenClass.add(1, 3)),
                () -> Assertions.assertEquals(5, mavenClass.add(1, 5)));
    }

    @Test
    void testAdd4() {
        // the last test will not run, because the middle one fails. Only After 1. will show. Shows 1 fail, should show 2
        Assertions.assertEquals(5, mavenClass.add(2, 3));
        System.out.println("After 1.");
        Assertions.assertEquals(5, mavenClass.add(1, 3));
        System.out.println("After 2.");
        Assertions.assertEquals(5, mavenClass.add(1, 5));
        System.out.println("After 3.");
    }
