package kr.moonshine.datapack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class Datapack {
    private final Map<String, byte[]> entries;

    Datapack(Map<String, byte[]> entries) {
        this.entries = entries;
    }

    public byte[] toZip() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(out)) {
            for (Map.Entry<String, byte[]> e : entries.entrySet()) {
                zip.putNextEntry(new ZipEntry(e.getKey()));
                zip.write(e.getValue());
                zip.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create datapack ZIP", e);
        }
        return out.toByteArray();
    }

    public void toFiles(Path outputDir) {
        for (Map.Entry<String, byte[]> e : entries.entrySet()) {
            Path target = outputDir.resolve(e.getKey());
            try {
                Files.createDirectories(target.getParent());
                Files.write(target, e.getValue());
            } catch (IOException ex) {
                throw new RuntimeException("Failed to write datapack file: " + target, ex);
            }
        }
    }
}
