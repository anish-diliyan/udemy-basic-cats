package type_classes.extra

// implicitly will use nearest implicit value on scope

object ExImplicitly extends App {
  trait ByteEncoder[A]{
    def encode(obj: A): Array[Byte]
  }

  object ByteEncoder {
    implicit object StringByteEncoder extends ByteEncoder[String] {
      override def encode(obj: String): Array[Byte] = obj.getBytes
    }
  }

  // use StringByEncoder
  val implicitEncodedString = implicitly[ByteEncoder[String]].encode("hello")
  println(implicitEncodedString.mkString("Array(", ", ", ")"))

  // What if you want to use your Encoder, instead of predefined.
  implicit object Rot3StringByteEncoder extends ByteEncoder[String] {
    override def encode(obj: String): Array[Byte] = {
      obj.getBytes.map(ele => (ele + 3).toByte)
    }
  }

  // use Rot3StringByteEncoder
  val implicitCustomEncodedString = implicitly[ByteEncoder[String]].encode("hello")
  //Array(107, 104, 111, 111, 114)
  println(implicitCustomEncodedString.mkString("Array(", ", ", ")"))
}
