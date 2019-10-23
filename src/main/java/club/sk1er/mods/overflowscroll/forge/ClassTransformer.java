package club.sk1er.mods.overflowscroll.forge;

import club.sk1er.mods.overflowscroll.transform.FramesTransformer;
import club.sk1er.mods.overflowscroll.transform.FullClassTransformer;
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
import java.util.HashMap;
import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(value = 1000)
public final class ClassTransformer implements IClassTransformer {

    private static final Logger LOGGER = LogManager.getLogger("ASM");

    private final Multimap<String, FramesTransformer> transformerMap = ArrayListMultimap.create();
    private final Map<String, FullClassTransformer> fullClassTransformerMap = new HashMap<>();

    public ClassTransformer() {
        this.registerTransformer(new GuiUtilsTransformer());

    }

    private void registerTransformer(FramesTransformer transformer) {
        for (String cls : transformer.getClassNames()) {
            this.transformerMap.put(cls, transformer);
        }
    }

    private void registerTransformer(FullClassTransformer transformer) {
        this.fullClassTransformerMap.put(transformer.getClassName(), transformer);
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) return null;


        FullClassTransformer fullClassTransformer = this.fullClassTransformerMap.get(transformedName);
        if (fullClassTransformer != null) {
            System.out.println("Overriding class: " + fullClassTransformer.getClassName());
            return fullClassTransformer.getClassData();
        }

        Collection<FramesTransformer> transformers = this.transformerMap.get(transformedName);
        if (transformers.isEmpty()) return bytes;

        LOGGER.info("Found {} transformers for {}", transformers.size(), transformedName);

        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

        transformers.forEach(transformer -> {
            LOGGER.info("Applying transformer {} on {}...",
                    transformer.getClass().getName(), transformedName);
            transformer.transform(classNode, transformedName);
        });

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        try {
            classNode.accept(classWriter);
        } catch (Throwable e) {
            System.out.println("Exception when transforming " + transformedName + " : " + e.getClass().getSimpleName());
            e.printStackTrace();
        }

        return classWriter.toByteArray();
    }

}
