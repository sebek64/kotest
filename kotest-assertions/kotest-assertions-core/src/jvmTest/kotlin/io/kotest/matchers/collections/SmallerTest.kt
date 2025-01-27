package io.kotest.matchers.collections

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.throwable.shouldHaveMessage

class SmallerTest : FunSpec({

   context("Should Be Smaller Than") {
      context("Iterables") {
         test("Iterable of size 2 should be smaller than iterable of size 3") {
            val col1 = listOf(1, 2)
            val col2 = listOf(1, 2, 3)
            col1 shouldBeSmallerThan col2
         }

         test("Iterable of size 3 should fail to be smaller than iterable of size 2") {
            val col1 = listOf(1, 2, 3)
            val col2 = listOf(1, 2)
            shouldThrow<AssertionError> {
               col1 shouldBeSmallerThan col2
            } shouldHaveMessage "Collection of size 3 should be smaller than collection of size 2"
         }
      }

      context("Arrays") {
         test("Array of size 2 should be smaller than array of size 3") {
            val arr1 = arrayOf(1, 2)
            val arr2 = arrayOf(1, 2, 3)
            arr1 shouldBeSmallerThan arr2
         }

         test("Array of size 3 should fail to be smaller than array of size 2") {
            val arr1 = arrayOf(1, 2, 3)
            val arr2 = arrayOf(1, 2)
            shouldThrow<AssertionError> {
               arr1 shouldBeSmallerThan arr2
            } shouldHaveMessage "Collection of size 3 should be smaller than collection of size 2"
         }
      }

      context("Primitive Arrays") {
         context("ByteArray") {
            test("ByteArray of size 2 should be smaller than ByteArray of size 3") {
               val arr1 = byteArrayOf(1, 2)
               val arr2 = byteArrayOf(1, 2, 3)
               arr1 shouldBeSmallerThan arr2
            }

            test("ByteArray of size 3 should fail to be smaller than ByteArray of size 2") {
               val arr1 = byteArrayOf(1, 2, 3)
               val arr2 = byteArrayOf(1, 2)
               shouldThrow<AssertionError> {
                  arr1 shouldBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 3 should be smaller than collection of size 2"
            }
         }

         context("IntArray") {
            test("IntArray of size 2 should be smaller than IntArray of size 3") {
               val arr1 = intArrayOf(1, 2)
               val arr2 = intArrayOf(1, 2, 3)
               arr1 shouldBeSmallerThan arr2
            }

            test("IntArray of size 3 should fail to be smaller than IntArray of size 2") {
               val arr1 = intArrayOf(1, 2, 3)
               val arr2 = intArrayOf(1, 2)
               shouldThrow<AssertionError> {
                  arr1 shouldBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 3 should be smaller than collection of size 2"
            }
         }

         context("ShortArray") {
            test("ShortArray of size 2 should be smaller than ShortArray of size 3") {
               val arr1 = shortArrayOf(1, 2)
               val arr2 = shortArrayOf(1, 2, 3)
               arr1 shouldBeSmallerThan arr2
            }

            test("ShortArray of size 3 should fail to be smaller than ShortArray of size 2") {
               val arr1 = shortArrayOf(1, 2, 3)
               val arr2 = shortArrayOf(1, 2)
               shouldThrow<AssertionError> {
                  arr1 shouldBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 3 should be smaller than collection of size 2"
            }
         }

         context("LongArray") {
            test("LongArray of size 2 should be smaller than LongArray of size 3") {
               val arr1 = longArrayOf(1L, 2L)
               val arr2 = longArrayOf(1L, 2L, 3L)
               arr1 shouldBeSmallerThan arr2
            }

            test("LongArray of size 3 should fail to be smaller than LongArray of size 2") {
               val arr1 = longArrayOf(1L, 2L, 3L)
               val arr2 = longArrayOf(1L, 2L)
               shouldThrow<AssertionError> {
                  arr1 shouldBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 3 should be smaller than collection of size 2"
            }
         }

         context("FloatArray") {
            test("FloatArray of size 2 should be smaller than FloatArray of size 3") {
               val arr1 = floatArrayOf(1.1f, 2.2f)
               val arr2 = floatArrayOf(1.1f, 2.2f, 3.3f)
               arr1 shouldBeSmallerThan arr2
            }

            test("FloatArray of size 3 should fail to be smaller than FloatArray of size 2") {
               val arr1 = floatArrayOf(1.1f, 2.2f, 3.3f)
               val arr2 = floatArrayOf(1.1f, 2.2f)
               shouldThrow<AssertionError> {
                  arr1 shouldBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 3 should be smaller than collection of size 2"
            }
         }

         context("DoubleArray") {
            test("DoubleArray of size 2 should be smaller than DoubleArray of size 3") {
               val arr1 = doubleArrayOf(1.1, 2.2)
               val arr2 = doubleArrayOf(1.1, 2.2, 3.3)
               arr1 shouldBeSmallerThan arr2
            }

            test("DoubleArray of size 3 should fail to be smaller than DoubleArray of size 2") {
               val arr1 = doubleArrayOf(1.1, 2.2, 3.3)
               val arr2 = doubleArrayOf(1.1, 2.2)
               shouldThrow<AssertionError> {
                  arr1 shouldBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 3 should be smaller than collection of size 2"
            }
         }

         context("CharArray") {
            test("CharArray of size 2 should be smaller than CharArray of size 3") {
               val arr1 = charArrayOf('a', 'b')
               val arr2 = charArrayOf('a', 'b', 'c')
               arr1 shouldBeSmallerThan arr2
            }

            test("CharArray of size 3 should fail to be smaller than CharArray of size 2") {
               val arr1 = charArrayOf('a', 'b', 'c')
               val arr2 = charArrayOf('a', 'b')
               shouldThrow<AssertionError> {
                  arr1 shouldBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 3 should be smaller than collection of size 2"
            }
         }

         context("BooleanArray") {
            test("BooleanArray of size 2 should be smaller than BooleanArray of size 3") {
               val arr1 = booleanArrayOf(true, false)
               val arr2 = booleanArrayOf(true, false, true)
               arr1 shouldBeSmallerThan arr2
            }

            test("BooleanArray of size 3 should fail to be smaller than BooleanArray of size 2") {
               val arr1 = booleanArrayOf(true, false, true)
               val arr2 = booleanArrayOf(true, false)
               shouldThrow<AssertionError> {
                  arr1 shouldBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 3 should be smaller than collection of size 2"
            }
         }
      }
   }

   context("Should NOT Be Smaller Than") {

      context("Iterables") {
         test("Iterable of size 3 should not be smaller than iterable of size 2") {
            val col1 = listOf(1, 2, 3)
            val col2 = listOf(1, 2)
            col1 shouldNotBeSmallerThan col2
         }

         test("Iterable of size 2 should fail to not be smaller than iterable of size 3") {
            val col1 = listOf(1, 2)
            val col2 = listOf(1, 2, 3)
            shouldThrow<AssertionError> {
               col1 shouldNotBeSmallerThan col2
            } shouldHaveMessage "Collection of size 2 should not be smaller than collection of size 3"
         }
      }

      context("Arrays") {
         test("Array of size 3 should not be smaller than array of size 2") {
            val arr1 = arrayOf(1, 2, 3)
            val arr2 = arrayOf(1, 2)
            arr1 shouldNotBeSmallerThan arr2
         }

         test("Array of size 2 should fail to not be smaller than array of size 3") {
            val arr1 = arrayOf(1, 2)
            val arr2 = arrayOf(1, 2, 3)
            shouldThrow<AssertionError> {
               arr1 shouldNotBeSmallerThan arr2
            } shouldHaveMessage "Collection of size 2 should not be smaller than collection of size 3"
         }
      }

      context("Primitive Arrays") {
         context("IntArray") {
            test("IntArray of size 3 should not be smaller than IntArray of size 2") {
               val arr1 = intArrayOf(1, 2, 3)
               val arr2 = intArrayOf(1, 2)
               arr1 shouldNotBeSmallerThan arr2
            }

            test("IntArray of size 2 should fail to not be smaller than IntArray of size 3") {
               val arr1 = intArrayOf(1, 2)
               val arr2 = intArrayOf(1, 2, 3)
               shouldThrow<AssertionError> {
                  arr1 shouldNotBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 2 should not be smaller than collection of size 3"
            }
         }

         context("ShortArray") {
            test("ShortArray of size 3 should not be smaller than ShortArray of size 2") {
               val arr1 = shortArrayOf(1, 2, 3)
               val arr2 = shortArrayOf(1, 2)
               arr1 shouldNotBeSmallerThan arr2
            }

            test("ShortArray of size 2 should fail to not be smaller than ShortArray of size 3") {
               val arr1 = shortArrayOf(1, 2)
               val arr2 = shortArrayOf(1, 2, 3)
               shouldThrow<AssertionError> {
                  arr1 shouldNotBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 2 should not be smaller than collection of size 3"
            }
         }

         context("LongArray") {
            test("LongArray of size 3 should not be smaller than LongArray of size 2") {
               val arr1 = longArrayOf(1L, 2L, 3L)
               val arr2 = longArrayOf(1L, 2L)
               arr1 shouldNotBeSmallerThan arr2
            }

            test("LongArray of size 2 should fail to not be smaller than LongArray of size 3") {
               val arr1 = longArrayOf(1L, 2L)
               val arr2 = longArrayOf(1L, 2L, 3L)
               shouldThrow<AssertionError> {
                  arr1 shouldNotBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 2 should not be smaller than collection of size 3"
            }
         }

         context("FloatArray") {
            test("FloatArray of size 3 should not be smaller than FloatArray of size 2") {
               val arr1 = floatArrayOf(1.1f, 2.2f, 3.3f)
               val arr2 = floatArrayOf(1.1f, 2.2f)
               arr1 shouldNotBeSmallerThan arr2
            }

            test("FloatArray of size 2 should fail to not be smaller than FloatArray of size 3") {
               val arr1 = floatArrayOf(1.1f, 2.2f)
               val arr2 = floatArrayOf(1.1f, 2.2f, 3.3f)
               shouldThrow<AssertionError> {
                  arr1 shouldNotBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 2 should not be smaller than collection of size 3"
            }
         }

         context("DoubleArray") {
            test("DoubleArray of size 3 should not be smaller than DoubleArray of size 2") {
               val arr1 = doubleArrayOf(1.1, 2.2, 3.3)
               val arr2 = doubleArrayOf(1.1, 2.2)
               arr1 shouldNotBeSmallerThan arr2
            }

            test("DoubleArray of size 2 should fail to not be smaller than DoubleArray of size 3") {
               val arr1 = doubleArrayOf(1.1, 2.2)
               val arr2 = doubleArrayOf(1.1, 2.2, 3.3)
               shouldThrow<AssertionError> {
                  arr1 shouldNotBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 2 should not be smaller than collection of size 3"
            }
         }

         context("CharArray") {
            test("CharArray of size 3 should not be smaller than CharArray of size 2") {
               val arr1 = charArrayOf('a', 'b', 'c')
               val arr2 = charArrayOf('a', 'b')
               arr1 shouldNotBeSmallerThan arr2
            }

            test("CharArray of size 2 should fail to not be smaller than CharArray of size 3") {
               val arr1 = charArrayOf('a', 'b')
               val arr2 = charArrayOf('a', 'b', 'c')
               shouldThrow<AssertionError> {
                  arr1 shouldNotBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 2 should not be smaller than collection of size 3"
            }
         }

         context("BooleanArray") {
            test("BooleanArray of size 3 should not be smaller than BooleanArray of size 2") {
               val arr1 = booleanArrayOf(true, false, true)
               val arr2 = booleanArrayOf(true, false)
               arr1 shouldNotBeSmallerThan arr2
            }

            test("BooleanArray of size 2 should fail to not be smaller than BooleanArray of size 3") {
               val arr1 = booleanArrayOf(true, false)
               val arr2 = booleanArrayOf(true, false, true)
               shouldThrow<AssertionError> {
                  arr1 shouldNotBeSmallerThan arr2
               } shouldHaveMessage "Collection of size 2 should not be smaller than collection of size 3"
            }
         }
      }
   }

})
