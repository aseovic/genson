package com.owlike.genson.convert;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.Genson;
import com.owlike.genson.JsonBindingException;
import com.owlike.genson.Wrapper;
import com.owlike.genson.reflect.TypeUtil;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

/**
 * This converter will use the runtime type of objects during serialization.
 *
 * @param <T> the type this converter is handling.
 * @author eugen
 */
public class RuntimeTypeConverter<T> extends Wrapper<Converter<T>> implements Converter<T> {

  private static final String CYCLE_KEY = "cycle-detection";

    public static class RuntimeTypeConverterFactory extends ChainedFactory {
        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        protected Converter<?> create(Type type, Genson genson, Converter<?> nextConverter) {
            if (nextConverter == null)
                throw new IllegalArgumentException(
                        "RuntimeTypeConverter can not be last Converter in the chain.");
            return (Converter<?>) new RuntimeTypeConverter(TypeUtil.getRawClass(type),
                    nextConverter);
        }
    }

    private final Class<T> tClass;

    @SuppressWarnings("unchecked")
    public RuntimeTypeConverter(Class<T> tClass, Converter<T> next) {
        super(next);
        this.tClass = tClass.isPrimitive()
                ? (Class<T>) TypeUtil.wrap(tClass)
                : tClass;
    }

    public void serialize(T obj, ObjectWriter writer, Context ctx) throws Exception {
        if (obj != null
            && !tClass.equals(obj.getClass())
            && !isContainer(obj)
            && !isSimpleType(obj)) {
          ensureNoCircularRefs(obj, ctx);
          ctx.genson.serialize(obj, obj.getClass(), writer, ctx);
          clearCircularCheckRefs(ctx);
        } else {
          wrapped.serialize(obj, writer, ctx);
        }
    }

    public T deserialize(ObjectReader reader, Context ctx) throws Exception {
        return wrapped.deserialize(reader, ctx);
    }

    private boolean isContainer(T obj) {
        return obj.getClass().isArray() ||
                obj instanceof Collection ||
                obj instanceof Map;
    }

    private boolean isSimpleType(T obj) {
      return obj instanceof Boolean ||
          obj instanceof Number ||
          obj instanceof String;
    }

    private void ensureNoCircularRefs(T obj, Context ctx) {
      if (!isContainer(obj) && !isSimpleType(obj))
      {
        Set<Object> seen = ctx.get(CYCLE_KEY, Set.class);
        if (seen == null)
        {
          seen = new HashSet<>();
          ctx.store(CYCLE_KEY, seen);
        }
        if (!seen.add(obj))
        {
          throw new JsonBindingException("Cyclic object graphs are not supported.");
        }
      }
    }

    private void clearCircularCheckRefs(Context ctx) {
      ctx.remove(CYCLE_KEY, Set.class);
    }


}
