package com.jahndigital.expressive.binding;

import javax.lang.model.type.NullType;
import java.lang.reflect.Type;

/**
 * A failsafe {@link BoundExpression} that's created when the Binder doesn't recognize the incoming {@link com.jahndigital.expressive.syntax.SyntaxNode}.
 */
public class BoundUnknownExpression extends BoundExpression {
    @Override
    public Type getType()
    {
        return NullType.class;
    }

    @Override
    public BoundNodeKind getKind()
    {
        return BoundNodeKind.UnknownExpression;
    }
}
