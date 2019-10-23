package club.sk1er.mods.overflowscroll.transform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public interface FullClassTransformer {

    String getClassName();

    byte[] getClassData();

    default byte[] getClassData(String name) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             InputStream inputStream = FullClassTransformer.class.getClassLoader().getResourceAsStream(name)) {
            byte[] buffer = new byte[1024];

            int read;
            while ((read = inputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, read);
            }

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
