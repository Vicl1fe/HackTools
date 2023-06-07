package com.darkerbox.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class ShortClassVisitor extends ClassVisitor {
    private final int api;

    public ShortClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
        this.api = api;
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals("transform"))
            return null;
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new ShortMethodAdapter(this.api, mv, name);
    }
}
