package kr.moonshine.datapack;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class Datapack {
    private final ImmutableMap<@NotNull String, byte @NotNull []> entries;

    Datapack(ImmutableMap<@NotNull String, byte @NotNull []> entries) {
        this.entries = entries;
    }

    public byte[] toZip() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(out)) {
            for (var e : entries.entrySet()) {
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
        for (var e : entries.entrySet()) {
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
