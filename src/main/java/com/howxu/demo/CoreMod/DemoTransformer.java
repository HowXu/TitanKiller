package com.howxu.demo.CoreMod;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import scala.tools.asm.Opcodes;

import net.minecraft.launchwrapper.IClassTransformer;

//核心类，用于动态改写class
public class DemoTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass){
 
        //通过混淆名找到EntityLivingBase类
        if(transformedName.equals("net.minecraft.entity.EntityLivingBase") || name.equals("sv")){

            ClassReader reader = new ClassReader(basicClass);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);//自动计算堆栈大小
            ClassVisitor visitor = new ClassVisitor(Opcodes.ASM4,writer) {
            //写Visitor以实现动态改写
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature,String[] exceptions) {

                //动态改写部分，以getHealth为例子，getHealth的混淆名是aS，函数签名()F
                if((name.equals("getHealth") || name.equals("aS")) && desc.equals("()F")){

                    MethodVisitor methodvisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                    methodvisitor.visitCode();//遍历方法
                    Label start = new Label();//定义一个开始标志
                    methodvisitor.visitLabel(start);


                    //然后写一个类来承接这个Class动态改写，把EntityLivingBase的getHealth方法写成EventUtil里的getHealth
                    methodvisitor.visitVarInsn(Opcodes.ALOAD, 0);//分别是参数类型和位置，是修改后的函数的参数
                    methodvisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/howxu/demo/Event/EventUtil", "getHealth"/*把方法修改了 */, "(Lnet/minecraft/entity/EntityLivingBase;)F"/*函数签名一致，并在其中指定参数 */, false);
                    methodvisitor.visitInsn(Opcodes.FRETURN);//指定返回值为float

                    //
                    Label end = new Label();
                    methodvisitor.visitLabel(end);//结束更改
                    methodvisitor.visitLocalVariable("this", "Lnet/minecraft/entity/EntityLivingBase;"/*匹配参数 */, null, start, end, 0/*参数位置 */);
                    methodvisitor.visitEnd();//结束遍历
                    return null;//删除原函数


                }
                return cv.visitMethod(access, name, desc, signature, exceptions);
            }
        

            };//ClassWritter和ClassVisitor都继承ClassVisitor，前面的是ASM版本，后面是Visitor

            //改写之后重新return
            reader.accept(visitor, Opcodes.ASM4);
            return writer.toByteArray();

            }//上一个if结束位置

            //同样可以读取其他类 不过很危险
            if(transformedName.equals("net.minecraft.client.Minecraft") || name.equals("bao")){
                ClassReader reader = new ClassReader(basicClass);
                ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);//自动计算堆栈大小
                ClassVisitor visitor = new ClassVisitor(Opcodes.ASM4,writer) {

                    @Override
                    public FieldVisitor visitField(int access, String name, String desc, String signature,Object value) {
                        if(name.equals("isGamePaused") || name.equals("T")){
                            access = Opcodes.ACC_PUBLIC;
                        }
                        return super.visitField(access, name, desc, signature, value);
                    }




                    @Override
                    public MethodVisitor visitMethod(int access, String name, String desc, String signature,String[] exceptions) {
                        //通过这个方法给rungameLoop开头加上一串
                        if(name.equals("runGameLoop") || name.equals("ak") && desc.equals("()V")){
                            return new MethodVisitor(Opcodes.ASM4,cv.visitMethod(access, name, desc, signature, exceptions)) {
                                public void visitCode() {
                                    super.visitCode();
                                    mv.visitVarInsn(Opcodes.ALOAD,0);
                                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/howxu/demo/Event/EventUtil", "runGameLoop", "(Lnet/minecraft/client/Minecraft;)V", false);
                                    //mv.visitInsn(Opcodes.RETURN);加了这行会让方法死循环
                                }
                            };
                        

                        }


                            /*MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
                            mv.visitCode();
                            Label start = new Label();
                            mv.visitLabel(start);
                            mv.visitVarInsn(Opcodes.ALOAD, 0);//0 = this
                            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/howxu/demo/Event/EventUtil", "runGameLoop", "(Lnet/minecraft/client/Minecraft;)V", false);
                            mv.visitInsn(Opcodes.RETURN);//这样设置无返回值
                            Label end = new Label();
                            mv.visitLabel(end);
                            mv.visitLocalVariable("this", "Lnet/minecraft/client/Minecraft;", null, start, end, 0);
                            mv.visitEnd(); */


                        return super.visitMethod(access, name, desc, signature, exceptions);
                    }
                };
                    //定义Visitor的位置，不用删除原函数，只需要先调用
                    reader.accept(visitor, Opcodes.ASM4);
                    return writer.toByteArray();
            }//上一个if结束位置



            


            //直接动态改写forge的事件注册，完全可以拦截所有不想要的事件
            if(transformedName.equals("cpw.mods.fml.common.eventhandler.EventBus")){
                ClassReader reader = new ClassReader(basicClass);
                ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);//自动计算堆栈大小
                ClassVisitor visitor = new ClassVisitor(Opcodes.ASM4,writer)
            {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature,String[] exceptions) {
                    if (name.equals("post") && desc.equals("(Lcpw/mods/fml/common/eventhandler/Event;)Z")) {

                        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                        mv.visitCode();
                        Label start = new Label();
                        mv.visitLabel(start);
                        mv.visitVarInsn(Opcodes.ALOAD,0);
                        mv.visitVarInsn(Opcodes.ALOAD,1);
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/howxu/demo/Event/EventUtil", "post", "(Lcpw/mods/fml/common/eventhandler/EventBus;Lcpw/mods/fml/common/eventhandler/Event;)Z", false);
                        mv.visitInsn(Opcodes.IRETURN);
                        Label end = new Label();
                        mv.visitLabel(end);
                        mv.visitLocalVariable("this", "Lcpw/mods/fml/common/eventhandler/EventBus;" ,null, start, end, 0);
<<<<<<< HEAD
                        mv.visitLocalVariable("this", "Lcpw/mods/fml/common/eventhandler/Event;" ,null, start, end, 1); //这里要对应上面的visitVarInsn
=======
                        mv.visitLocalVariable("this", "Lcpw/mods/fml/common/eventhandler/Event;" ,null, start, end, 1);
                        //这里的参数位置也要对应上面的0,1
>>>>>>> 139bd9039f51705139cd974dfe5cad8e97ab34f6
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

            //改写MinecraftServer的isGamePused，实现最完全时停
            if (transformedName.equals("net.minecraft.server.Integrated.IntegratedServer") || transformedName.equals("")) {
                ClassReader reader = new ClassReader(basicClass);
                ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);//自动计算堆栈大小
                ClassVisitor visitor = new ClassVisitor(Opcodes.ASM4,writer)
                {
                    @Override
                    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                            if(name.equals("isGamePaused") || name.equals("l")){
                                access = Opcodes.ACC_PUBLIC;
                            }
                        return super.visitField(access, name, desc, signature, value);
                    }
                    @Override
                    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                        if(name.equals("tick") || name.equals("u") && desc.equals("()V")){
                            return new MethodVisitor(Opcodes.ASM4,cv.visitMethod(access, name, desc, signature, exceptions)) {
                                public void visitCode() {
                                    super.visitCode();
                                    mv.visitVarInsn(Opcodes.ALOAD,0);
                                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/howxu/demo/Event/EventUtil", "tick", "(Lnet/minecraft/server/Integrated/IntegratedServer;)V", false);
                                    //mv.visitInsn(Opcodes.RETURN);加了这行会让方法死循环
                                }
                            };
                        
                        }
                        return super.visitMethod(access, name, desc, signature, exceptions);
                    }

                };
                reader.accept(visitor, Opcodes.ASM4);
                return writer.toByteArray();
            }

            if (transformedName.equals("net.minecraft.server.MinecraftServer")) {
                ClassReader reader = new ClassReader(basicClass);
                ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);//自动计算堆栈大小
                ClassVisitor visitor = new ClassVisitor(Opcodes.ASM4,writer)
                {
                    @Override
                    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                        if((name.equals("tick") || name.equals("u")) && desc.equals("()V")){

                            MethodVisitor methodvisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                            methodvisitor.visitCode();//遍历方法
                            Label start = new Label();//定义一个开始标志
                            methodvisitor.visitLabel(start);
        
        
                            //然后写一个类来承接这个Class动态改写，把EntityLivingBase的getHealth方法写成EventUtil里的getHealth
                            methodvisitor.visitVarInsn(Opcodes.ALOAD, 0);//分别是参数类型和位置，是修改后的函数的参数
                            methodvisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/howxu/demo/Event/EventUtil", "tick2"/*把方法修改了 */, "(Lnet/minecraft/server/MinecraftServer;)V"/*函数签名一致，并在其中指定参数 */, false);
                            methodvisitor.visitInsn(Opcodes.RETURN);//指定返回值为float
        
                            //
                            Label end = new Label();
                            methodvisitor.visitLabel(end);//结束更改
                            methodvisitor.visitLocalVariable("this", "Lnet/minecraft/server/MinecraftServer;"/*匹配参数 */, null, start, end, 0/*参数位置 */);
                            methodvisitor.visitEnd();//结束遍历
                            return null;//删除原函数
        
        
                        }
                        if (name.equals("saveAllWorlds") || name.equals("a") && desc.equals("(Z)V")) {
                            access = Opcodes.ACC_PUBLIC;//方法不能直接private改成public，但是protected可以
                        }
                        return super.visitMethod(access, name, desc, signature, exceptions);
                    }
                    @Override
                    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                        //static和final字段不能随便改
                        if(name.equals("tickCounter") || name.equals("x")||
                           name.equals("startProfiling") || name.equals("R")||
                           name.equals("field_147142_T") || name.equals("V")||
                           name.equals("serverConfigManager") || name.equals("u")){
                            access = Opcodes.ACC_PUBLIC;
                           }
                           if(name.equals("field_147147_p") || name.equals("q")||
                              name.equals("field_147146_q") || name.equals("r")||
                              name.equals("usageSnooper") || name.equals("x")){
                            access = Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL;
                           }
                        return super.visitField(access, name, desc, signature, value);
                    }
                };
                reader.accept(visitor, Opcodes.ASM4);
                return writer.toByteArray();
            }
        return basicClass;//这个return实现未更改返回原来的类
    }

}
