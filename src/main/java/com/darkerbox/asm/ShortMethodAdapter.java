package com.darkerbox.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ShortMethodAdapter extends MethodVisitor implements Opcodes {
    public ShortMethodAdapter(int api, MethodVisitor mv, String methodName) {
        super(api, mv);
    }

    public void visitLineNumber(int line, Label start) {}
}
