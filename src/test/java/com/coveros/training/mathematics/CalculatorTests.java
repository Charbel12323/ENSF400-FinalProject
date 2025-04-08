package com.coveros.training.mathematics;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CalculatorTests {

    // ----------------------------------------------------------
    //
    // Unit tests not requiring mocks
    //
    // ----------------------------------------------------------

    @Test
    public void testShouldAddTwoIntegers() {
        // Act
        int result = Calculator.add(3, 4);
        
        // Assert
        Assert.assertEquals(7, result);
    }

    @Test
    public void testShouldAddTwoDecimals() {
        // Act
        double result = Calculator.add(3.5, 4.5);
        
        // Assert
        Assert.assertEquals(8.0, result, 0.0001);
    }

    @Test
    public void testShouldGetStringVersionOfResult() {
        // Test all possible input values
        Assert.assertEquals("zero", Calculator.toStringZeroToTen(0));
        Assert.assertEquals("one", Calculator.toStringZeroToTen(1));
        Assert.assertEquals("two", Calculator.toStringZeroToTen(2));
        Assert.assertEquals("three", Calculator.toStringZeroToTen(3));
        Assert.assertEquals("four", Calculator.toStringZeroToTen(4));
        Assert.assertEquals("five", Calculator.toStringZeroToTen(5));
        Assert.assertEquals("six", Calculator.toStringZeroToTen(6));
        Assert.assertEquals("seven", Calculator.toStringZeroToTen(7));
        Assert.assertEquals("eight", Calculator.toStringZeroToTen(8));
        Assert.assertEquals("nine", Calculator.toStringZeroToTen(9));
        Assert.assertEquals("ten", Calculator.toStringZeroToTen(10));
        
        // Test case for default value
        Assert.assertEquals("dunno", Calculator.toStringZeroToTen(11));
        Assert.assertEquals("dunno", Calculator.toStringZeroToTen(-1));
    }

    @Test
    public void testShouldGetPairResult() {
        // Arrange
        Pair<Integer, Integer> pair1 = Pair.of(1, 2);
        Pair<Integer, Integer> pair2 = Pair.of(3, 4);
        
        // Act
        Pair<Integer, Integer> result = Calculator.add(pair1, pair2);
        
        // Assert
        Assert.assertEquals(Integer.valueOf(4), result.getLeft());
        Assert.assertEquals(Integer.valueOf(6), result.getRight());
    }

    // ----------------------------------------------------------
    //
    // Unit tests requiring mocks
    //
    // ----------------------------------------------------------

    /**
     * Tests calculateAndMore
     */
    @Test
    public void testShouldMockOutsideMethods() {
        // Arrange
        Calculator.iFoo mockFoo = Mockito.mock(Calculator.iFoo.class);
        Calculator.iBar mockBar = Mockito.mock(Calculator.iBar.class);
        
        when(mockFoo.doComplexThing(5)).thenReturn(10);
        when(mockBar.doOtherComplexThing(10)).thenReturn(20);
        
        // Act
        int result = Calculator.calculateAndMore(5, 7, mockFoo, mockBar);
        
        // Assert
        Assert.assertEquals(42, result); // 5 + 7 + 10 + 20 = 42
        
        // Verify the mocks were called with the expected arguments
        Mockito.verify(mockFoo).doComplexThing(5);
        Mockito.verify(mockBar).doOtherComplexThing(10);
    }

    /**
     * Tests calculateAndMorePart2
     */
    @Test
    public void testShouldMockOutsideMethodsPart2() {
        // Arrange
        Calculator.Baz mockBaz = Mockito.mock(Calculator.Baz.class);
        when(mockBaz.doThirdPartyThing(5)).thenReturn(15);
        
        Calculator calculator = new Calculator(mockBaz);
        
        // Act
        int result = calculator.calculateAndMorePart2(5);
        
        // Assert
        Assert.assertEquals(20, result); // 5 + 15 = 20
        
        // Verify the mock was called with the expected arguments
        Mockito.verify(mockBaz).doThirdPartyThing(5);
    }
    
    /**
     * Tests calculateAndMorePart3
     */
    @Test
    public void testShouldVerifyMethodCall() {
        // Arrange
        Calculator.Baz mockBaz = Mockito.mock(Calculator.Baz.class);
        Calculator calculator = new Calculator(mockBaz);
        
        // Act
        calculator.calculateAndMorePart3(5);
        
        // Assert - verify the mock was called with the expected arguments
        verify(mockBaz).doThirdPartyThing(5);
    }
    
    /**
     * Tests the default constructor
     */
    @Test
    public void testDefaultConstructor() {
        // Act
        Calculator calculator = new Calculator();
        
        // Assert - Call a method that uses the internal Baz
        int result = calculator.calculateAndMorePart2(3);
        
        // The default Baz implementation returns 42, so result should be 3 + 42 = 45
        Assert.assertEquals(45, result);
    }
    
    /**
     * Tests the Foo implementation
     */
    @Test
    public void testFooImplementation() {
        // Arrange
        Calculator.Foo foo = new Calculator.Foo();
        
        // Act
        int result = foo.doComplexThing(5);
        
        // Assert
        Assert.assertEquals(6, result); // 5 + 1 = 6
    }
    
    /**
     * Tests the Bar implementation
     */
    @Test
    public void testBarImplementation() {
        // Arrange
        Calculator.Bar bar = new Calculator.Bar();
        
        // Act
        int result = bar.doOtherComplexThing(5);
        
        // Assert
        Assert.assertEquals(4, result); // 5 - 1 = 4
    }
}
