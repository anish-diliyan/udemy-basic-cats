package type_classes.laws

trait ByteCodecLaws[A]{
  def codec: ByteCodec[A]

  def isomorphism(obj:A): Boolean =
    codec.decode(codec.encode(obj)) == obj
}
