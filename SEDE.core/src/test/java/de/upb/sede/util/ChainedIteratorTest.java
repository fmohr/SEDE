package de.upb.sede.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;



public class ChainedIteratorTest {

	@Test
	public void testChainedIterator() {
		Collection<Integer> someCollection = new ArrayList<>();

		// iterator generator
		Function<Integer, Iterator<Integer>> generatorUpToi = i -> new Iterator<Integer>() {
			int current = 0;
			int max = i;

			@Override
			public boolean hasNext() {
				return current < max;
			}

			@Override
			public Integer next() {
				return current++;
			}
		};
		Function<Integer, Iterator<Integer>> generatorUpTo0 = i -> new Iterator<Integer>() {
			int current = i;
			int min = 0;

			@Override
			public boolean hasNext() {
				return current >= 0;
			}

			@Override
			public Integer next() {
				return current--;
			}
		};
		Function<Integer, Iterator<String>> generatorString = i -> new Iterator<String>() {
			String basedOnText = i.toString();
			int charAt = 0;

			@Override
			public boolean hasNext() {
				return charAt < basedOnText.length();
			}

			@Override
			public String next() {
				return String.valueOf(basedOnText.charAt(charAt++));
			}
		};
		Function<Integer, Iterator<Integer>> generatorEmpty = i -> new Iterator<Integer>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public Integer next() {
				return 0;
			}
		};
		Function<Integer, Iterator<Integer>> generatorOne = i -> new Iterator<Integer>() {
			boolean generated = false;

			@Override
			public boolean hasNext() {
				return !generated;
			}

			@Override
			public Integer next() {
				generated = true;
				return i;
			}
		};

		// lists from generators
		List<Integer> generator1List = new ArrayList<>();
		List<Integer> generator2List = new ArrayList<>();
		List<String> generator3List = new ArrayList<>();
		List<Integer> generator4List = new ArrayList<>();
		List<Integer> generator5List = new ArrayList<>();

		// fill all the lists
		for (Integer i = 0; i < 100; i++) {
			someCollection.add(i);
			addItToList(generatorUpToi, generator1List, i);
			addItToList(generatorUpTo0, generator2List, i);
			addItToList(generatorString, generator3List, i);
			addItToList(generatorEmpty, generator4List, i);
			addItToList(generatorOne, generator5List, i);
		}

		// now compare results of lists with the FileteredIterator functionality
		ChainedIterator<Integer, Integer> chainedIterator = new ChainedIterator<>(someCollection.iterator(),
				generatorUpToi);
		assertEqualIterator(chainedIterator, generator1List);

		chainedIterator = new ChainedIterator<>(someCollection.iterator(), generatorUpTo0);
		assertEqualIterator(chainedIterator, generator2List);

		ChainedIterator<Integer, String> chainedIteratorStr = new ChainedIterator<>(someCollection.iterator(),
				generatorString);
		assertEqualIterator(chainedIteratorStr, generator3List);

		chainedIterator = new ChainedIterator<>(someCollection.iterator(), generatorEmpty);
		assertEqualIterator(chainedIterator, generator4List);

		chainedIterator = new ChainedIterator<>(someCollection.iterator(), generatorOne);
		assertEqualIterator(chainedIterator, generator5List);

		boolean exceptionthrow = false;
		try {
			new ChainedIterator<>(someCollection.iterator(), null); // passing null as the generator will throw a
																	// nulltpointer exception
		} catch (NullPointerException ex) {
			exceptionthrow = true;
		}
		Assert.assertTrue(exceptionthrow);

		exceptionthrow = false;
		try {
			new ChainedIterator<>(null, generatorEmpty); // passing null as the base iterator will throw a nulltpointer
															// exception
		} catch (NullPointerException ex) {
			exceptionthrow = true;
		}
		Assert.assertTrue(exceptionthrow);
	}

	/**
	 * helper function that adds the given value to the given list if the given
	 * funciton returns true when the given value is plugged in.
	 */
	private static <I> void addItToList(Function<Integer, Iterator<I>> generator, List<I> list, Integer value) {
		for (Iterator<I> it = generator.apply(value); it.hasNext();) {
			// if generator holds add the value to the list
			list.add(it.next());
		}
	}

	/**
	 * helper function that asserts that the values returned by the given chained
	 * iterator are the same as the values in the given list.
	 */
	private static <I, C> void assertEqualIterator(ChainedIterator<I, C> chainedIterator,
			Collection<C> expectedValues) {
		for (C expectedValue : expectedValues) {
			Assert.assertTrue(chainedIterator.hasNext());
			C actualValue = chainedIterator.next();
			Assert.assertEquals(expectedValue, actualValue);
		}
		Assert.assertFalse(chainedIterator.hasNext());
	}
}
