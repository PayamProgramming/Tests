import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class testTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp(TestInfo info) {
        System.out.println("Testing " + info.getDisplayName());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    /* Parameterized tests make it possible to run a test multiple times
      With different arguments.*/
    // ParameterizedTest With one Argument
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("What")
    void testWithValueSource(int argument) {
        assertTrue(argument > 0 && argument < 4);
    }

    // ParameterizedTest With more than one Arguments
    // With name attribute we can put a name for every tests inside method
    @ParameterizedTest(name = "{0} length is {1}")
    @CsvSource(value = {"as , 2" , "bahs , 4" , "a , 1"})
    void csv(String str , int length){
        Assertions.assertEquals(length , str.length());
    }

    /* JUnit Jupiter provides the ability to repeat a test a specified
      Number of times by annotating a method with
      @RepeatedTest and specifying the total number of repetitions desired.*/
    @RepeatedTest(5)
    void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
        assertEquals(5, repetitionInfo.getTotalRepetitions());
    }

    /* Entire test classes or individual test methods may be disabled via
     the @Disabled annotation*/
    @Disabled("Disabled until bug #42 has been resolved")
    @Test
    void testWillBeSkipped() {
    }

    /* Used to fail a test, test factory, test template,
       or lifecycle method if its execution exceeds a given duration. */
    @Test
    @Timeout(22)
    void timeout() {
    }

    // Timeout in JUnit5 using assertTimeout()
    @Test
    void timeOut(){
        assertTimeout(Duration.ofSeconds(5),() -> {
            for(int i = 0 ; i < 3000 ; i++) {
                System.out.println(i);
            }
        });
    }

    /* In order to allow individual test methods to be executed in isolation
       and to avoid unexpected side effects due to mutable test instance state,
       JUnit creates a new instance of each test class before executing each
       test method (see Test Classes and Methods). This "per-method"
       test instance lifecycle is the default behavior in JUnit Jupiter
       and is analogous to all previous versions of JUnit.*/
    @TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
    class OrderedNestedTestClassesDemo {

        /* @Nested tests give the test writer more capabilities to express
           the relationship among several groups of tests.
           Such nested tests make use of Java’s nested classes and facilitate
           hierarchical thinking about the test structure. */
        @Nested
        class PrimaryTests {

            @Test
            void test1() {
            }
        }
    }


    /* A @TestTemplate method is not a regular test case but rather a template
       for test cases. As such, it is designed to be invoked multiple times
       depending on the number of invocation contexts returned by the registered providers.*/
    // Repeated Tests and Parameterized Tests are built-in specializations of test templates.
    @TestTemplate
    @Disabled
    void testTemplate() {
    }

    /* @TestFactory method is not itself a test case but rather a factory
       for test cases. Thus, a dynamic test is the product of a factory.
       Technically speaking, a @TestFactory method must return a single
       DynamicNode or a Stream, Collection, Iterable, Iterator, or array of DynamicNode instances.*/
    /* allows us to declare and run test cases generated at run-time.
       Contrary to Static Tests, which define a fixed number of test cases
       at the compile time, Dynamic Tests allow us to define the test cases
       dynamically in the runtime.*/
    @TestFactory
    List dynamicTestsWithInvalidReturnType() {
        return (List) new ArrayList();
    }

    /* Tags are a JUnit Platform concept for marking and filtering tests.
       The programming model for adding tags to containers and tests is defined
       by the testing framework.*/
    @Tag("fast")
    @Tag("model")
    class TaggingDemo {

        @Test
        @Tag("taxes")
        void testingTaxCalculation() {
        }

    }

    /* Developers can register one or more extensions declarative by annotating
       a test interface, test class, test method, or custom composed annotation
       with @ExtendWith() and supplying class references for the extensions to
       register. As of JUnit Jupiter 5.8, @ExtendWith may also be declared on
       fields or on parameters in test class constructors, in test methods,
       and in @BeforeAll, @AfterAll, @BeforeEach, and @AfterEach lifecycle methods.*/
    @Test
    @ExtendWith({})
    @Disabled
    void getProductList(String serverUrl) {
    }

    /* Assumptions are used to run tests only if certain conditions are met.
       This is typically used for external conditions that are required for the
       test to run properly, but which aren't directly related to whatever is
       being tested.We can declare an assumption with
       assumeTrue(), assumeFalse() and assumingThat()  */
    @Test
    void trueAssumption() {
        assumeTrue(5 > 1);
        assertEquals(5 + 2, 7);
    }

    // assumeFalse()
    @Test
    void falseAssumption() {
        assumeFalse(5 < 1);
        assertEquals(5 + 2, 7);
    }

    // assumingThat()
    @Test
    void assumptionThat() {
        String someString = "Just a string";
        assumingThat(
                someString.equals("Just a string"),
                () -> assertEquals(2 + 2, 4)
        );
    }

    /* There are two ways of exception testing in JUnit 5,
       both of which we can implement using the assertThrows() method:*/

    //   The first example verifies the details of the thrown exception
    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });
        assertEquals("Not supported", exception.getMessage());
    }

    // the second one validates the type of exception.
    @Test
    void assertThrowsException() {
        String str = null;
        assertThrows(IllegalArgumentException.class, () -> {
            Integer.valueOf(str);
        });
    }

    /* @Order is an annotation that is used to configure the order in which
       the annotated element (i.e., field, method, or class) should be
       evaluated or executed relative to other elements of the same category. */
    @Test
    @Order(value = 1)
    void order() {
    }

    /* Exceptions : assertThrows() Assert that execution of the supplied executable throws an exception
       of the expectedType and return the exception. if no exception is thrown,
       or if an exception of a different type is thrown, this method will fail.
       If you do not want to perform additional checks on the exception instance,
       ignore the return value.*/
    @Test
    void exception(){
        String str = null;
        assertThrows(NullPointerException.class,()-> str.length());
    }

}