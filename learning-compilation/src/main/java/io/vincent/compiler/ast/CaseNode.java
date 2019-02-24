package io.vincent.compiler.ast;

import io.vincent.compiler.asm.Label;
import io.vincent.compiler.context.Dumper;

import java.util.List;

public class CaseNode extends StatementNode {
    protected Label label;
    protected List<ExpressionNode> values;
    protected BlockNode body;

    public CaseNode(Location loc, List<ExpressionNode> values, BlockNode body) {
        super(loc);
        this.values = values;
        this.body = body;
        this.label = new Label();
    }

    public List<ExpressionNode> values() {
        return values;
    }

    public boolean isDefault() {
        return values.isEmpty();
    }

    public BlockNode body() {
        return body;
    }

    public Label label() {
        return label;
    }

    public void _dump(Dumper d) {
        d.printNodeList("values", values);
        d.printMember("body", body);
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
