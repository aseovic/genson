package com.owlike.genson.convert;

import com.owlike.genson.convert.ChainedFactory;

/**
 * @author Aleksandar Seovic  2018.05.30
 */
public interface ChainedFactoryVisitor {
    void visit(ChainedFactory factory);
}
