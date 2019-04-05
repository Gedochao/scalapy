package me.shadaj.scalapy.py

import org.scalatest.{FunSuite, BeforeAndAfterAll}

class ObjectReaderTest extends FunSuite with BeforeAndAfterAll {
  test("Reading a boolean") {
    local {
      assert(Object.from(false).as[Boolean] == false)
      assert(Object.from(true).as[Boolean] == true)
    }
  }

  test("Reading a byte") {
    local {
      assert(Object.from(5.toByte).as[Byte] == 5.toByte)
    }
  }

  test("Reading an integer") {
    local {
      assert(Object.from(123).as[Int] == 123)
    }
  }

  test("Reading a long") {
    local {
      assert(Object.from(Long.MaxValue).as[Long] == Long.MaxValue)
    }
  }

  test("Reading a float") {
    local {
      assert(Object.from(123.123f).as[Float] == 123.123f)
    }
  }

  test("Reading a double") {
    local {
      assert(Object.from(123.123d).as[Double] == 123.123d)
    }
  }

  test("Reading a string") {
    local {
      assert(Object.from("hello world!").as[String] == "hello world!")
    }
  }

  test("Reading an empty sequence") {
    local {
      assert(Object.from(Seq.empty[Int]).as[Seq[Int]].isEmpty)
    }
  }

  test("Reading a sequence of ints") {
    local {
      assert(Object.from(Seq[Int](1, 2, 3)).as[Seq[Int]] == Seq(1, 2, 3))
    }
  }

  test("Reading a sequence of doubles") {
    local {
      assert(Object.from(Seq[Double](1.1, 2.2, 3.3)).as[Seq[Double]] == Seq(1.1, 2.2, 3.3))
    }
  }

  test("Reading a sequence of strings") {
    local {
      assert(Object.from(Seq[String]("hello", "world")).as[Seq[String]] == Seq("hello", "world"))
    }
  }

  test("Reading a sequence of arrays") {
    local {
      assert(Object.from(Seq[Array[Int]](Array(1), Array(2))).as[Seq[Seq[Int]]] == Seq(Seq(1), Seq(2)))
    }
  }

  test("Reading a sequence of sequences") {
    local {
      assert(Object.from(Seq[Seq[Int]](Seq(1), Seq(2))).as[Seq[Seq[Int]]] == Seq(Seq(1), Seq(2)))
    }
  }

  test("Reading a sequence of objects preserves original object") {
    local {
      val datetimeExpr = module("datetime").moduleName
      val datesSeq = Object(s"[$datetimeExpr.date.today(), $datetimeExpr.date.today().replace(year = 1000)]").as[Seq[Object]]
      assert(datesSeq.head.asInstanceOf[DynamicObject].year.as[Int] > 2000)
      assert(datesSeq.last.asInstanceOf[DynamicObject].year.as[Int] == 1000)
    }
  }

  test("Reading a map of int to int") {
    local {
      val read = Object.from(Map(1 -> 2, 2 -> 3)).as[Map[Int, Int]]
      assert(read(1) == 2)
      assert(read(2) == 3)
    }
  }

  test("Reading a tuple") {
    local {
      assert(Object.from((1, 2)).as[(Int, Int)] == (1, 2))
    }
  }
}
