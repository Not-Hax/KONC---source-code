package meow.konc.hack.command.syntax.parsers;

import meow.konc.hack.command.syntax.SyntaxChunk;
import meow.konc.hack.module.Module;
import meow.konc.hack.module.ModuleManager;
import meow.konc.hack.setting.Setting;

import java.util.HashMap;
import java.util.TreeMap;

public class ValueParser extends AbstractParser {

    int moduleIndex;

    public ValueParser(int moduleIndex) {
        this.moduleIndex = moduleIndex;
    }

    public String getChunk(SyntaxChunk[] chunks, SyntaxChunk thisChunk, String[] values, String chunkValue) {
        if (moduleIndex > values.length - 1 || chunkValue == null) return getDefaultChunk(thisChunk);
        String module = values[moduleIndex];
        Module m = ModuleManager.getModuleByName(module);
        if (m == null) return "";

        HashMap<String, Setting> possibilities = new HashMap<>();

        for (Setting v : m.settingList) {
            if (v.getName().toLowerCase().startsWith(chunkValue.toLowerCase()))
                possibilities.put(v.getName(), v);
        }

        if (possibilities.isEmpty()) return "";

        TreeMap<String, Setting> p = new TreeMap<>(possibilities);
        Setting aV = p.firstEntry().getValue();
        return aV.getName().substring(chunkValue.length());
    }
}
