package com.darkerbox.hacktools.Encrypt;

public class FineReport {
    public static String decrypt(String Key) {//密文
        final int[] PassWordArray = { 19, 78, 10, 15, 100, 213, 43, 23};
        if (Key != null && Key.startsWith("___")) {
            Key = Key.substring(3);
            final StringBuilder stringBuilder = new StringBuilder();
            byte Step = 0;
            for (byte i = 0; i <= Key.length() - 4; i += 4) {
                if (Step == PassWordArray.length) {
                    Step = 0;
                }
                final String str = Key.substring(i, i + 4);
                final int num = Integer.parseInt(str, 16) ^ PassWordArray[Step];
                stringBuilder.append((char)num);
                Step++;
            }
            Key = stringBuilder.toString();
        }
        return Key;
    }
}
