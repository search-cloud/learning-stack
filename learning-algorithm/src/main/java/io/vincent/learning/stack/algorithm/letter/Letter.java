package io.vincent.learning.stack.algorithm.letter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 10/16/18.
 *
 * @author Vincent
 * @since 1.0, 10/16/18
 */
public class Letter {

	/**
	 * 是否出现相同
	 *
	 * @param letter letter
	 * @param newspaper newspaper
	 * @return true, false
	 */
	static boolean isLoveLetterReproducible(String letter, String newspaper) {
		Map<Integer, Integer> charMap = new HashMap<>();
		int charCount = 0;

		for (int i = 0; i < letter.length(); i++) {
			int charCode = letter.charAt(i);
			Integer o = charMap.get(charCode);
			o = o == null ? 0 : o;
			if (o == 0) {
				charCount++;
			}
			charMap.put(charCode, ++o);
		}

		for (int i = 0; i < newspaper.length(); i++) {
			int charCode = newspaper.charAt(i);
			Integer o = charMap.get(charCode);
			o = o == null ? 0 : o;
			if (o > 0) {
				charMap.put(charCode, --o);
				if (o == 0) {
					charCount--;
				}
			}
			if (charCount == 0) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args) {
		String letter = "iI_love_you!i";
		String newspaper = "i_sove_you!_i_I_l";
		boolean loveLetterReproducible = isLoveLetterReproducible(letter, newspaper);
		System.out.println(loveLetterReproducible);
	}
}
