package com.takaranoao.titankiller.Core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class MCTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {

        if(transformedName.equals("net.minecraft.network.NetHandlerPlayServer")){
            ClassReader reader = new ClassReader(basicClass);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);//自动计算堆栈大小
            ClassVisitor visitor = new ClassVisitor(Opcodes.ASM4,writer) {
                //写Visitor以实现动态改写
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

                    //动态改写部分，以getHealth为例子，getHealth的混淆名是aS，函数签名()F
                    if((name.equals("kickPlayerFromServer") && desc.equals("(Ljava/lang/String;)V"))){

                        System.out.println("Found kickPlayerFromServer");

                        MethodVisitor methodvisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                        methodvisitor.visitCode();//遍历方法
                        Label start = new Label();//定义一个开始标志
                        methodvisitor.visitLabel(start);


                        //然后写一个类来承接这个Class动态改写，把EntityLivingBase的getHealth方法写成EventUtil里的getHealth
                        methodvisitor.visitVarInsn(Opcodes.ALOAD, 0);//分别是参数类型和位置，是修改后的函数的参数
                        methodvisitor.visitVarInsn(Opcodes.ALOAD, 1);//分别是参数类型和位置，是修改后的函数的参数
                        methodvisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/takaranoao/titankiller/Core/Hook", "kickPlayerFromServer"/*把方法修改了 */, "(Lnet/minecraft/network/NetHandlerPlayServer;java/lang/String;)V"/*函数签名一致，并在其中指定参数 */, false);
                        methodvisitor.visitInsn(Opcodes.RETURN);

                        //
                        Label end = new Label();
                        methodvisitor.visitLabel(end);//结束更改
                        methodvisitor.visitLocalVariable("this", "Lnet/minecraft/network/NetHandlerPlayServer;"/*匹配参数 */, null, start, end, 0/*参数位置 */);
                        methodvisitor.visitLocalVariable("this", "Ljava/lang/String;"/*匹配参数 */, null, start, end, 1/*参数位置 */);

                        methodvisitor.visitEnd();//结束遍历
                        System.out.println("Finish Rewrite");
                        return null;//删除原函数
                    }
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }
            };//ClassWritter和ClassVisitor都继承ClassVisitor，前面的是ASM版本，后面是Visitor
            //改写之后重新return
            reader.accept(visitor, Opcodes.ASM4);
            return writer.toByteArray();
        }

        //直接动态改写forge的事件注册，完全可以拦截所有不想要的事件
        if(transformedName.equals("cpw.mods.fml.common.eventhandler.EventBus")){
            ClassReader reader = new ClassReader(basicClass);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);//自动计算堆栈大小
            ClassVisitor visitor = new ClassVisitor(Opcodes.ASM4,writer)
            {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature,String[] exceptions) {
                    if (name.equals("post") && desc.equals("(Lcpw/mods/fml/common/eventhandler/Event;)Z")) {
                        System.err.println("Found EventBus post");
                        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                        mv.visitCode();
                        Label start = new Label();
                        mv.visitLabel(start);
                        mv.visitVarInsn(Opcodes.ALOAD,0);
                        mv.visitVarInsn(Opcodes.ALOAD,1);
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/takaranoao/titankiller/Core/Hook", "post", "(Lcpw/mods/fml/common/eventhandler/EventBus;Lcpw/mods/fml/common/eventhandler/Event;)Z", false);
                        mv.visitInsn(Opcodes.IRETURN);
                        Label end = new Label();
                        mv.visitLabel(end);
                        mv.visitLocalVariable("this", "Lcpw/mods/fml/common/eventhandler/EventBus;" ,null, start, end, 0);
                        mv.visitLocalVariable("this", "Lcpw/mods/fml/common/eventhandler/Event;" ,null, start, end, 1); //这里要对应上面的visitVarInsn
                        mv.visitEnd();
                        return null;//把返回值掐掉
                    }
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }

                @Override
                public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                    if (name.equals("busID")) {
                        access = Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL;                                //遍历字段可以修改访问权限
                    }
                    if(name.equals("exceptionHandler")) {
                        access = Opcodes.ACC_PUBLIC;
                        //遍历字段可以修改访问权限
                    }
                    return super.visitField(access, name, desc, signature, value);
                }



            };
            reader.accept(visitor, Opcodes.ASM4);
            return writer.toByteArray();
        }
        System.out.println("Finish All");
        return basicClass;
    }
}
