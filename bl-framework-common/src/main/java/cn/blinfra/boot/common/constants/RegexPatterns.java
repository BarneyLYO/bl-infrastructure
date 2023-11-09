package cn.blinfra.boot.common.constants;

import java.util.regex.Pattern;

public interface RegexPatterns {
  Pattern PATTERN_MOBILE = Pattern.compile(
    "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-79])|(?:5[0-35-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[189]))\\d{8}$");

  Pattern PATTERN_URL = Pattern.compile(
    "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

  Pattern PATTERN_XML_NCNAME = Pattern.compile("[a-zA-Z_][\\-_.0-9_a-zA-Z$]*");
}
