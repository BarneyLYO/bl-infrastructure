package cn.blinfra.boot.starter.jackson;

import cn.blinfra.boot.common.util.DateUtils;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalTimeJson {
  public static final LocalTimeSerializer SERIALIZER
    = new LocalTimeSerializer(
    DateTimeFormatter.ofPattern(DateUtils.FORMAT_HOUR_MINUTE_SECOND)
                     .withZone(
                       ZoneId.systemDefault()));

  public static final LocalTimeDeserializer DESERIALIZER
    = new LocalTimeDeserializer(
    DateTimeFormatter.ofPattern(DateUtils.FORMAT_HOUR_MINUTE_SECOND)
                     .withZone(ZoneId.systemDefault()));
}
