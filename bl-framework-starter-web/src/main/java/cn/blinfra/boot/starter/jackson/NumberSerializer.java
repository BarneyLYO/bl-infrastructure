package cn.blinfra.boot.starter.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import java.io.IOException;

@JacksonStdImpl
public class NumberSerializer
  extends com.fasterxml.jackson.databind.ser.std.NumberSerializer {
  private static final long MAX_SAFE_INTEGER = 900_719_925_474_099_1L;
  private static final long MIN_SAFE_INTEGER = -900_719_925_474_099_1L;

  public static final NumberSerializer INSTANCE = new NumberSerializer(Number.class);

  public NumberSerializer(Class<? extends Number> rawType) {
    super(rawType);
  }

  @Override
  public void serialize(Number value, JsonGenerator g, SerializerProvider provider)
  throws IOException {
    if (value.longValue() > MIN_SAFE_INTEGER &&
        value.longValue() < MAX_SAFE_INTEGER) {
      super.serialize(value, g, provider);
      return;
    }
    g.writeString(value.toString());
  }
}
