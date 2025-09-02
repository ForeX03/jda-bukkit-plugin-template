package cc.dreamcode.dsmp.shared.util;

import java.util.Map;

public class StringReplaceUtil {
    public static String replace(String text, Map<String, String> replaceMap) {
        String finalText = text;

        for (Map.Entry<String, String> replacePair : replaceMap.entrySet()) {
            finalText = finalText.replace(replacePair.getKey(), replacePair.getValue());
        }

        return finalText;
    }
}
