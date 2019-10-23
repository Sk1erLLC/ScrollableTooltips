package club.sk1er.mods.overflowscroll.transform;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public interface FramesTransformer {

    String[] getClassNames();

    void transform(ClassNode classNode, String name);

    default String mapMethodName(ClassNode classNode, MethodNode methodNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, methodNode.name, methodNode.desc);
    }

    default String mapMethodDesc(MethodNode methodNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(methodNode.desc);
    }

    default String mapFieldName(ClassNode classNode, FieldNode fieldNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(classNode.name, fieldNode.name, fieldNode.desc);
    }

}
