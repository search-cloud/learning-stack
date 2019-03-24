package io.vincent.compiler.c.type;

import io.vincent.compiler.c.ast.Location;
import io.vincent.compiler.c.ast.Slot;
import io.vincent.compiler.c.utils.AsmUtils;

import java.util.List;

public class StructType extends CompositeType {
	public StructType(String name, List<Slot> membs, Location loc) {
		super(name, membs, loc);
	}

	public boolean isStruct() {
		return true;
	}

	public boolean isSameType(Type other) {
		if (!other.isStruct()) return false;
		return equals(other.getStructType());
	}

	protected void computeOffsets() {
		long offset = 0;
		long maxAlign = 1;
		for (Slot s : members()) {
			offset = AsmUtils.align(offset, s.allocSize());
			s.setOffset(offset);
			offset += s.allocSize();
			maxAlign = Math.max(maxAlign, s.alignment());
		}
		cachedSize = AsmUtils.align(offset, maxAlign);
		cachedAlign = maxAlign;
	}

	public String toString() {
		return "struct " + name;
	}
}
