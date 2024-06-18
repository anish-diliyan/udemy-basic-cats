package type_classes.extra

import java.nio.ByteBuffer

object ExInstanceDerivation extends App {

  trait ByteEncoder[A] {
    def encode(obj: A): Array[Byte]
  }

  object ByteEncoder {
    def apply[A](implicit instance: ByteEncoder[A]): ByteEncoder[A] = instance
  }

  implicit object IntByteEncoder extends ByteEncoder[Int] {
    override def encode(obj: Int): Array[Byte] =
      ByteBuffer.allocate(4).putInt(obj).array()
  }
  implicit object StringByteEncoder extends ByteEncoder[String] {
    override def encode(obj: String): Array[Byte] = obj.getBytes
  }

  implicit def optionEncoder[A](implicit enc: ByteEncoder[A]): ByteEncoder[Option[A]] = {
    new ByteEncoder[Option[A]] {
      override def encode(obj: Option[A]): Array[Byte] = obj match {
        case Some(value) => enc.encode(value)
        case None => Array[Byte]()
      }
    }
  }

  val optionIntResult = ByteEncoder[Option[Int]].encode(Option(10))
  println(optionIntResult.mkString("Array(", ", ", ")"))

  val optionStringResult = ByteEncoder[Option[String]].encode(Option("hello"))
  println(optionStringResult.mkString("Array(", ", ", ")"))
}
