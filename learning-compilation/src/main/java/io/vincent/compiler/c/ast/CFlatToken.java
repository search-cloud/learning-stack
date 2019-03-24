package io.vincent.compiler.c.ast;

import io.vincent.compiler.c.parser.Token;
import io.vincent.compiler.c.utils.TextUtils;
import io.vincent.compiler.c.parser.ParserConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Token wrapper.
 */
public class CFlatToken implements Iterable<CFlatToken> {
	// token
	protected Token token;
	// is special token
	protected boolean special;

	public CFlatToken(Token token) {
		this(token, false);
	}

	public CFlatToken(Token token, boolean special) {
		this.token = token;
		this.special = special;
	}

	public String toString() {
		// return the token image.
		return token.image;
	}

	public boolean isSpecial() {
		return this.special;
	}

	public int kindID() {
		return token.kind;
	}

	public String kindName() {
		return ParserConstants.tokenImage[token.kind];
	}

	public int lineno() {
		return token.beginLine;
	}

	public int column() {
		return token.beginColumn;
	}

	public String image() {
		return token.image;
	}

	public String dumpedImage() {
		return TextUtils.dumpString(token.image);
	}

	public Iterator<CFlatToken> iterator() {
		return buildTokenList(token, false).iterator();
	}

	protected List<CFlatToken> tokensWithoutFirstSpecials() {
		return buildTokenList(token, true);
	}

	protected List<CFlatToken> buildTokenList(Token first, boolean rejectFirstSpecials) {
		List<CFlatToken> result = new ArrayList<CFlatToken>();
		boolean rejectSpecials = rejectFirstSpecials;
		for (Token t = first; t != null; t = t.next) {
			if (t.specialToken != null && !rejectSpecials) {
				Token s = specialTokenHead(t.specialToken);
				for (; s != null; s = s.next) {
					result.add(new CFlatToken(s));
				}
			}
			result.add(new CFlatToken(t));
			rejectSpecials = false;
		}
		return result;
	}

	protected Token specialTokenHead(Token firstSpecial) {
		Token s = firstSpecial;
		while (s.specialToken != null) {
			s = s.specialToken;
		}
		return s;
	}

	public String includedLine() {
		StringBuilder buf = new StringBuilder();
		for (CFlatToken t : tokensWithoutFirstSpecials()) {
			int idx = t.image().indexOf("\n");
			if (idx >= 0) {
				buf.append(t.image().substring(0, idx));
				break;
			}
			buf.append(t.image());
		}
		return buf.toString();
	}
}
