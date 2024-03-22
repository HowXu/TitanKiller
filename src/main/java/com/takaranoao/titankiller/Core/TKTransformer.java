 package com.takaranoao.titankiller.Core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

 public class TKTransformer implements IClassTransformer
 {
   public byte[] transform(String name, String transformedName, byte[] basicClass) {

if ("net.minecraft.entity.titan.EntityWitherzilla".equals(transformedName)) {
       ClassNode craft = getClassNode(basicClass);
       label94: for (MethodNode methodNode : craft.methods) {
         System.out.println(methodNode.name + methodNode.desc);
         if ("func_70619_bc".equals(methodNode.name)) {
           for (AbstractInsnNode node : methodNode.instructions.toArray()) {
             if (node.getOpcode() == 180 && ("loadedEntityList".equals(((FieldInsnNode)node).name) || "field_72996_f".equals(((FieldInsnNode)node).name))) {
               methodNode.instructions.insert(node, (AbstractInsnNode)new MethodInsnNode(184, "com/takaranoao/titankiller/Core/Hook", "hook", "(Ljava/util/List;)Ljava/util/List;", false));
               break label94;
             }
           }
         }
       }
     return getBytecode(craft);
     }  if ("net.minecraft.entity.titan.EntityTitanSpirit".equals(transformedName)) {
       ClassNode craft = getClassNode(basicClass);
       label91: for (MethodNode methodNode : craft.methods) {
         System.out.println(methodNode.name + methodNode.desc);
         if ("func_70106_y".equals(methodNode.name)) {
           for (AbstractInsnNode node : methodNode.instructions.toArray()) {
             if (node.getOpcode() == 182) {
               methodNode.instructions.insert(node, (AbstractInsnNode)new InsnNode(2));
               methodNode.instructions.insert(node, (AbstractInsnNode)new InsnNode(87));
               break label91;
             }
           }
         }
       }
       return getBytecode(craft);
     }



if ("net.minecraft.entity.titan.EntityTitan".equals(transformedName)) {
    //改写Titan生物类让秒杀成为可能
       ClassNode craft = getClassNode(basicClass);
       label92: for (MethodNode methodNode : craft.methods) {
         System.out.println(methodNode.name + methodNode.desc);
         if ("func_70624_b".equals(methodNode.name)) {
           for (AbstractInsnNode node : methodNode.instructions.toArray()) {
             if (node.getOpcode() == 182) {
               methodNode.instructions.insert(node, (AbstractInsnNode)new InsnNode(2));
               methodNode.instructions.insert(node, (AbstractInsnNode)new InsnNode(87));
               break label92;
             }
           }
         }
       }
            return getBytecode(craft);

    }


if ("net.minecraft.network.NetHandlerPlayServer".equals(transformedName)) {
    //重写NetHandlerPlayServer来防止泰坦生物清空背包
      ClassNode craft = getClassNode(basicClass);
      label93: for (MethodNode methodNode : craft.methods) {
        System.out.println(methodNode.name + methodNode.desc);
        if ("dropAllItems".equals(methodNode.name)) {
          for (AbstractInsnNode node : methodNode.instructions.toArray()) {
            if (node.getOpcode() == 182) {
              methodNode.instructions.insert(node, (AbstractInsnNode)new InsnNode(2));
              methodNode.instructions.insert(node, (AbstractInsnNode)new InsnNode(87));
              break label93;
            }
          }
        }
      }
      return getBytecode(craft);
    }

       return basicClass;
  }

protected ClassNode getClassNode(byte[] bytes) {
    ClassNode classNode = new ClassNode();
    ClassReader classReader = new ClassReader(bytes);
    classReader.accept((ClassVisitor)classNode, 0);
    return classNode;
  }

protected static byte[] getBytecode(ClassNode classNode) {
    ClassWriter classWriter = new ClassWriter(327680);
    classNode.accept((ClassVisitor)classWriter);
    return classWriter.toByteArray();
  }
}