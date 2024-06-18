package io.kotest.matchers.collections

import io.kotest.assertions.ErrorCollectionMode
import io.kotest.assertions.errorCollector
import io.kotest.assertions.failure
import io.kotest.assertions.print.print
import io.kotest.assertions.runWithMode
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.MatcherResultWithError
import io.kotest.matchers.neverNullMatcher

fun <T> existInOrder(vararg ps: (T) -> Boolean): Matcher<Collection<T>?> = existInOrder(ps.asList())

/**
 * Assert that a collections contains a subsequence that matches the given subsequence of predicates, possibly with
 * values in between.
 */
fun <T> existInOrder(predicates: List<(T) -> Boolean>): Matcher<Collection<T>?> = neverNullMatcher { actual ->
   require(predicates.isNotEmpty()) { "predicates must not be empty" }

   var subsequenceIndex = 0
   val actualIterator = actual.iterator()

   while (actualIterator.hasNext() && subsequenceIndex < predicates.size) {
      if (predicates[subsequenceIndex](actualIterator.next())) subsequenceIndex += 1
   }

   MatcherResult(
      subsequenceIndex == predicates.size,
      { "${actual.print().value} did not match the predicates in order. Predicate at index $subsequenceIndex did not match." },
      { "${actual.print().value} should not match the predicates in order" }
   )
}

fun <T> haveSize(size: Int): Matcher<Collection<T>> = haveSizeMatcher(size)


fun <T : Comparable<T>> beSorted(): Matcher<List<T>> = sorted()
fun <T : Comparable<T>> sorted(): Matcher<List<T>> = sortedBy { it }

fun <T, E : Comparable<E>> beSortedBy(transform: (T) -> E): Matcher<List<T>> = sortedBy(transform)
fun <T, E : Comparable<E>> sortedBy(transform: (T) -> E): Matcher<List<T>> = object : Matcher<List<T>> {
   override fun test(value: List<T>): MatcherResult {
      val failure =
         value.withIndex().firstOrNull { (i, it) -> i != value.lastIndex && transform(it) > transform(value[i + 1]) }
      val elementMessage = when (failure) {
         null -> ""
         else -> ". Element ${failure.value} at index ${failure.index} was greater than element ${value[failure.index + 1]}"
      }
      return MatcherResult(
         failure == null,
         { "List ${value.print().value} should be sorted$elementMessage" },
         { "List ${value.print().value} should not be sorted" }
      )
   }
}

fun <T> matchEach(vararg fns: (T) -> Unit): Matcher<Collection<T>?> = matchEach(fns.asList())
fun <T> matchInOrder(vararg fns: (T) -> Unit): Matcher<Collection<T>?> = matchInOrder(fns.asList(), allowGaps = false)
fun <T> matchInOrderSubset(vararg fns: (T) -> Unit): Matcher<Collection<T>?> =
   matchInOrder(fns.asList(), allowGaps = true)

/**
 * Assert that a [Collection] contains a subsequence that matches the given assertions. Failing elements may occur
 * between passing ones, if [allowGaps] is set to true
 */
fun <T> matchInOrder(assertions: List<(T) -> Unit>, allowGaps: Boolean): Matcher<Collection<T>?> =
   neverNullMatcher { actual ->
      val originalMode = errorCollector.getCollectionMode()
      try {
         data class MatchInOrderSubsetProblem(
            val atIndex: Int,
            val problem: String,
         )

         data class MatchInOrderSubsetResult(
            val startIndex: Int,
            val elementsPassed: Int,
            val problems: List<MatchInOrderSubsetProblem>
         )

         val actualAsList = actual.toList()

         var allPassed = false
         var bestResult: MatchInOrderSubsetResult? = null

         for (startIndex in 0..(actual.size - assertions.size)) {
            var elementsPassed = 0
            var elementsTested = 0
            val currentProblems = ArrayList<MatchInOrderSubsetProblem>()

            while (startIndex + elementsTested < actual.size) {
               if (bestResult == null || elementsPassed > bestResult.elementsPassed) {
                  bestResult = MatchInOrderSubsetResult(startIndex, elementsPassed, currentProblems)
               }

               if (!allowGaps && elementsTested > elementsPassed) break

               val elementResult = runCatching {
                  assertions[elementsPassed](actualAsList[startIndex + elementsTested])
               }

               if (elementResult.isSuccess) {
                  elementsPassed++
                  currentProblems.clear()
                  if (elementsPassed == assertions.size) {
                     allPassed = true
                     break
                  }
               } else {
                  currentProblems.add(
                     MatchInOrderSubsetProblem(
                        startIndex + elementsTested,
                        elementResult.exceptionOrNull()!!.message!!
                     )
                  )
               }

               elementsTested++
            }

            if (allPassed) break
         }

         MatcherResult(
            allPassed,
            {
               """
            |Expected a sequence of elements to pass the assertions, ${if (allowGaps) "possibly with gaps between " else ""}but failed to match all assertions
            |
            |Best result when comparing from index [${bestResult?.startIndex}], where ${bestResult?.elementsPassed} elements passed, but the following elements failed:
            |
            ${
                  bestResult?.problems?.joinToString("\n") { problem ->
                     "|${problem.atIndex} => ${problem.problem}"
                  }
               }
            """.trimMargin()
            },
            { "Expected some assertion to fail but all passed" }
         )
      } finally {
         errorCollector.setCollectionMode(originalMode)
      }
   }

/**
 * Asserts that each element of the collection matches with the element of [expected].
 * Elements will be compared sequentially by passing the actual / expected pairs to the
 * [asserter] in the order given by the iterators of the collections.
 */
fun <T> matchEach(expected: List<T>, asserter: (T, T) -> Unit): Matcher<Collection<T>?> =
   matchEach(expected.map { expectedElement ->
      { actualElement: T ->
         asserter(actualElement, expectedElement)
      }
   })

private sealed interface MatchEachProblem {
   val atIndex: Int
   /** A short message without a stack trace. */
   val shortMessage: String
   /** A long message including a stack trace if available. */
   val longMessage: String

   data class SizeMismatch(override val atIndex: Int, val problem: String) : MatchEachProblem {
      override val shortMessage: String
         get() = problem
      override val longMessage: String
         get() = problem
   }

   data class Thrown(override val atIndex: Int, val problem: Throwable) : MatchEachProblem {
      override val shortMessage: String
         get() = problem.message ?: "Exception $problem thrown"
      override val longMessage: String
         get() = problem.stackTraceToString()
   }
}

/**
 * Asserts that each element in the collection matches its corresponding matcher in [assertions].
 * Elements will be compared sequentially in the order given by the iterators of the collections.
 */
fun <T> matchEach(assertions: List<(T) -> Unit>): Matcher<Collection<T>?> = neverNullMatcher { actual ->
   val problems = errorCollector.runWithMode(ErrorCollectionMode.Hard) {
      actual.mapIndexedNotNull { index, element ->
         if (index !in assertions.indices) {
            MatchEachProblem.SizeMismatch(
               index,
               "Element has no corresponding assertion. Only ${assertions.size} assertions provided"
            )
         } else {
            runCatching {
               assertions[index](element)
            }.exceptionOrNull()?.let { exception ->
               MatchEachProblem.Thrown(index, exception)
            }
         }
      }
   } + (actual.size until assertions.size).map {
      MatchEachProblem.SizeMismatch(
         it,
         "No actual element for assertion at index $it"
      )
   }

   when (problems.size) {
      0 -> MatcherResult(
         true,
         { "" },
         { "Expected some element to fail its assertion, but all passed." },
      )

      1 -> {
         val problem = problems.first()
         val failMsg = "Expected each element to pass its assertion, but found issues at indexes: [${problem.atIndex}]\n\n${problem.atIndex} => ${problem.shortMessage}"
         MatcherResultWithError(
            (problem as? MatchEachProblem.Thrown)?.problem?.let { cause ->
               failure(failMsg, cause)
            },
            false,
            { failMsg },
            { "" },
         )
      }

      else -> MatcherResult(
         false,
         {
            "Expected each element to pass its assertion, but found issues at indexes: [${problems.joinToString { it.atIndex.toString() }}]\n\n" +
               problems.joinToString(separator = "\n") { "${it.atIndex} => ${it.longMessage}" }
         },
         { "" },
      )
   }
}
