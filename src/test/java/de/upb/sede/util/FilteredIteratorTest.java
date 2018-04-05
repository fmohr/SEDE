package de.upb.sede.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests Filtered Iterator class in util package.
 * @author aminfaez
 *
 */
public class FilteredIteratorTest {

	@Test
	public void testFilteredIterator() {
		Collection<Integer> someCollection = new ArrayList<>();
		
		// conditions
		Function<Integer, Boolean> condition1 = i -> i < 50; 
		Function<Integer, Boolean> condition2 = i -> i % 2 == 0;  // every second number starting at the first value
		Function<Integer, Boolean> condition3 = i -> i % 3 == 1; // every third starting at the second  value
		Function<Integer, Boolean> condition4 = i -> false; // no value
		Function<Integer, Boolean> condition5 = i -> true;  // every value
		Function<Integer, Boolean> condition6 = i -> i > 50;  // upper half values
		
		// lists from conditions
		List<Integer> condition1List = new ArrayList<>();
		List<Integer> condition2List = new ArrayList<>();
		List<Integer> condition3List = new ArrayList<>();
		List<Integer> condition4List = new ArrayList<>();
		List<Integer> condition5List = new ArrayList<>();
		List<Integer> condition6List = new ArrayList<>();
		
		// fill all the lists
		for(Integer i = 0; i < 100; i++) {
			someCollection.add(i);
			addToListIf(condition1, condition1List, i);
			addToListIf(condition2, condition2List, i);
			addToListIf(condition3, condition3List, i);
			addToListIf(condition4, condition4List, i);
			addToListIf(condition5, condition5List, i);
			addToListIf(condition6, condition6List, i);
		}
		
		// now compare results  of lists with the FileteredIterator functionality
		FilteredIterator<Integer> filteredIterator = new FilteredIterator<>(someCollection.iterator(), condition1);
		assertEqualIterator(filteredIterator, condition1List);
		
		filteredIterator = new FilteredIterator<>(someCollection.iterator(), condition2);
		assertEqualIterator(filteredIterator, condition2List);
		
		filteredIterator = new FilteredIterator<>(someCollection.iterator(), condition3);
		assertEqualIterator(filteredIterator, condition3List);
		
		filteredIterator = new FilteredIterator<>(someCollection.iterator(), condition4);
		assertEqualIterator(filteredIterator, condition4List);
		
		filteredIterator = new FilteredIterator<>(someCollection.iterator(), condition5);
		assertEqualIterator(filteredIterator, condition5List);
		
		filteredIterator = new FilteredIterator<>(someCollection.iterator(), condition6);
		assertEqualIterator(filteredIterator, condition6List);
		
		

		filteredIterator = new FilteredIterator<>(someCollection.iterator(), null);
		assertEqualIterator(filteredIterator, condition5List); // passing null as a filter will default to allow all.
		
		boolean exceptionthrow = false;
		try {
			new FilteredIterator<>(null, condition1); // passing null as the base iterator will throw a nulltpointer exception
		} catch(NullPointerException ex) {
			exceptionthrow = true;
		}
		Assert.assertTrue(exceptionthrow);
	}
	/**
	 * helper function that adds the given value to the given list if the given funciton returns true when the given value is plugged in.
	 */
	private static void addToListIf(Function<Integer, Boolean> condition, List<Integer> list, Integer value) {
		if(condition.apply(value)) {
			// if condition holds add the value to the list
			list.add(value);
		}
	}
	/**
	 * helper function that asserts that the values returned by the given filtered iterator are the same as the values in the given list.
	 */
	private static <C> void assertEqualIterator(FilteredIterator<C> filteredIterator, Collection<C> expectedValues) {
		for(C expectedValue : expectedValues) {
			Assert.assertTrue(filteredIterator.hasNext());
			C actualValue = filteredIterator.next();
			Assert.assertEquals(expectedValue, actualValue);
		}
		Assert.assertFalse(filteredIterator.hasNext());
	}
}
