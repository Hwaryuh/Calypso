package kr.moonshine.datapack;

import com.google.gson.JsonElement;

public interface DatapackWriter {
    void write(String path, JsonElement json);
}
