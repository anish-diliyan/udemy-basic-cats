package type_classes.laws

import java.nio.ByteBuffer

trait ByteEncoder[A] {
  def encode(o:A): Array[Byte]
}

trait ByteDecoder[A]{
  def decode(bytes:Array[Byte]): A
}

trait ByteCodec[A] extends ByteDecoder[A] with ByteEncoder[A]

object ByteCodec {
  implicit object IntByteCodec extends ByteCodec[Int] {
    override def decode(bytes: Array[Byte]): Int =
      ByteBuffer.allocate(4).put(bytes).flip().getInt

    override def encode(o: Int): Array[Byte] =
      ByteBuffer.allocate(4).putInt(o).array()
  }
  implicit object StringByteCodec extends ByteCodec[String] {
    override def decode(bytes: Array[Byte]): String =
      new String(bytes)

    override def encode(o: String): Array[Byte] =
      o.getBytes
  }
}