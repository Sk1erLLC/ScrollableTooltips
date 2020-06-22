package club.sk1er.mods.overflowscroll.forge;

import club.sk1er.mods.overflowscroll.transform.TooltipsTransformer;
import club.sk1er.mods.overflowscroll.transform.impl.GuiUtilsTransformer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collection;

public final class ClassTransformer implements IClassTransformer {

    private static final Logger LOGGER = LogManager.getLogger("Tooltips Transformer");
    private final Multimap<String, TooltipsTransformer> transformerMap = ArrayListMultimap.create();

    public ClassTransformer() {
        this.registerTransformer(new GuiUtilsTransformer());
    }

    private void registerTransformer(TooltipsTransformer transformer) {
        for (String cls : transformer.getClassNames()) {
            this.transformerMap.put(cls, transformer);
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) return null;

        Collection<TooltipsTransformer> transformers = this.transformerMap.get(transformedName);
        if (transformers.isEmpty()) return bytes;

        LOGGER.info("Found {} transformers for {}", transformers.size(), transformedName);

        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

        for (TooltipsTransformer transformer : transformers) {
            LOGGER.info("Applying transformer {} on {}...", transformer.getClass().getName(), transformedName);
            transformer.transform(classNode, transformedName);
        }

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        try {
            classNode.accept(classWriter);
        } catch (Throwable e) {
            LOGGER.error("Exception when transforming {} : {}", transformedName, e.getClass().getSimpleName(), e);
        }

        return classWriter.toByteArray();
    }

}
