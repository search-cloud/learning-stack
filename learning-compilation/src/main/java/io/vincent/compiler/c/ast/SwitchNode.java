package io.vincent.compiler.c.ast;
import io.vincent.compiler.c.context.Dumper;

import java.util.List;

public class SwitchNode extends StatementNode {
    protected ExpressionNode cond;
    protected List<CaseNode> cases;

    public SwitchNode(Location loc, ExpressionNode cond, List<CaseNode> cases) {
        super(loc);
        this.cond = cond;
        this.cases = cases;
    }

    public ExpressionNode cond() {
        return cond;
    }

    public List<CaseNode> cases() {
        return cases;
    }

    public void _dump(Dumper d) {
        d.printMember("condition", cond);
        d.printNodeList("cases", cases);
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
