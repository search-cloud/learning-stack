package io.vincent.learning.stack.algorithm.letter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by Vincent on 10/16/18.
 *
 * @author Vincent
 * @since 1.0, 10/16/18
 */
public class LetterTest {

	@Test
	public void isLoveLetterReproducible() {
		String letter = "iI_love_you!i";
		String newspaper = "i_sove_you!_i_I_l";
		boolean loveLetterReproducible = Letter.isLoveLetterReproducible(letter, newspaper);
		Assertions.assertThat(loveLetterReproducible).isTrue();
	}

	@Test
	public void isLoveLetterReproducible1() {
		String letter = "iI_love_you!i";
		String newspaper = "i_sove_you!_i_I_";
		boolean loveLetterReproducible = Letter.isLoveLetterReproducible(letter, newspaper);
		Assertions.assertThat(loveLetterReproducible).isFalse();
	}
}
