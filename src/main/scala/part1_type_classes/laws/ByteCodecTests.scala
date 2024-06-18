package type_classes.laws

import org.scalacheck.Arbitrary
import org.scalacheck.Prop.forAll
import org.typelevel.discipline.Laws

trait ByteCodecTests[A] extends Laws {
  def laws: ByteCodecLaws[A]

  def byteCodec(implicit arb: Arbitrary[A]): RuleSet = new DefaultRuleSet(
    "byteCodec",
    parent = None,
    props = "isomorphism" -> forAll(laws.isomorphism _)
  )
}

object ByteCodecTests {
  def apply[A](implicit bc: ByteCodec[A]): ByteCodecTests[A] = new ByteCodecTests[A] {
    override def laws: ByteCodecLaws[A] = new ByteCodecLaws[A] {
      override def codec: ByteCodec[A] = bc
    }
  }
}
