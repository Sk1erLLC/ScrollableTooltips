package club.sk1er.mods.overflowscroll.transform.impl;

import club.sk1er.mods.overflowscroll.transform.FramesTransformer;
import jdk.internal.org.objectweb.asm.Opcodes;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class GuiUtilsTransformer implements FramesTransformer {

    @Override
    public String[] getClassNames() {
        return new String[]{"net.minecraftforge.fml.client.config.GuiUtils"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {

        for (MethodNode method : classNode.methods) {
            String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, method.name, method.desc);
            if (methodName.equals("drawHoveringText")) {
                method.instructions.clear();
                method.localVariables.clear();
                method.instructions.add(getMod());
                System.out.println("Overrode drawHoveringText");
            }
        }
    }

    private InsnList getMod() {
        InsnList insnList = new InsnList();
        //(Ljava/util/List;IIIIILnet/minecraft/client/gui/FontRenderer;)V

        insnList.add(new VarInsnNode(Opcodes.ALOAD, 0));
        insnList.add(new VarInsnNode(Opcodes.ILOAD, 1));
        insnList.add(new VarInsnNode(Opcodes.ILOAD, 2));
        insnList.add(new VarInsnNode(Opcodes.ILOAD, 3));
        insnList.add(new VarInsnNode(Opcodes.ILOAD, 4));
        insnList.add(new VarInsnNode(Opcodes.ILOAD, 5));
        insnList.add(new VarInsnNode(Opcodes.ALOAD, 6));
        insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "club/sk1er/mods/overflowscroll/GuiUtilsOverride", "drawHoveringText", "(Ljava/util/List;IIIIILnet/minecraft/client/gui/FontRenderer;)V", false));
        insnList.add(new InsnNode(Opcodes.RETURN));
        return insnList;
    }


}
