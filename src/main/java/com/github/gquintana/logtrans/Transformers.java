package com.github.gquintana.logtrans;

import java.time.Instant;
import java.util.Collections;
import java.util.function.Function;

import static com.github.gquintana.logtrans.Fields.collectionField;


public class Transformers {
    public static ChainTransformer.Builder chain(Transformer.Builder transformerBuilder) {
        return chain(transformerBuilder.build());
    }

    public static ChainTransformer.Builder chain(Transformer transformer) {
        return ChainTransformer.chain(transformer);
    }

    public static FunctionTransformer.Builder<Instant> instant(String source, Parser<Instant> parser) {
        Function<Object, Instant> function = o -> Converters.toInstant(o, parser);
        return FunctionTransformer.field(source, function);
    }


    public static Transformer noop() {
        return Collections::singleton;
    }


    public static SetTransformer.Builder set(String target, Object value) {
        return SetTransformer.set(target, value);
    }

    public static RuleTransformer.Builder rules() {
        return RuleTransformer.rules();

    }

    private static CollectionField<String> TAG_FIELD = collectionField("tag");
    public static SetTransformer addTag(String... tag) {
        return TAG_FIELD.add(tag);
    }
}
