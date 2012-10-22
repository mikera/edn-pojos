package mikera.edn.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.bpsm.edn.EdnException;
import us.bpsm.edn.parser.Parseable;
import us.bpsm.edn.parser.Token;

public class CustomParsers {

    /**
     * VectorParser parses a vector of identical sub-items into a java.util.List
     * 
     * @author Mike
     * 
     * @param <T>
     */
    public static class VectorParser<T> extends AbstractCustomParser<List<T>> {
        private AbstractCustomParser<T> elementParser;

        public VectorParser(AbstractCustomParser<T> elementParser) {
            this.elementParser = elementParser;
        }

        @Override
        public List<T> nextValue(Object token, Parseable pbr) {
            if (token != Token.BEGIN_VECTOR) {
                throw new EdnException("Not the start of a vector: " + token);
            }
            token = scanner.nextToken(pbr);

            ArrayList<T> al = new ArrayList<T>();
            while (token != Token.END_VECTOR) {
                T value = elementParser.nextValue(token, pbr);
                al.add(value);
                token = scanner.nextToken(pbr);
            }

            return al;
        }
    }

    /**
     * MapParser parses a map of Value -> Specific object types
     * 
     * @author Mike
     * 
     * @param <T>
     * @param <K>
     */
    public static abstract class MapParser<T> extends AbstractCustomParser<T> {
        private Map<?, CustomParser<?>> parsers;

        @SuppressWarnings("unchecked")
        public <K, V> MapParser(Map<K, V> mapParsers) {
            this.parsers = (Map<?, CustomParser<?>>) mapParsers;
        }

        private Map<Object, Object> parseMap(Object token, Parseable pbr) {
            if (token != Token.BEGIN_MAP) {
                throw new EdnException("Not the start of a vector: " + token);
            }
            token = scanner.nextToken(pbr);

            HashMap<Object, Object> hm = new HashMap<Object, Object>();

            while (token != Token.END_MAP_OR_SET) {
                Object key = token;
                CustomParser<?> parser = parsers.get(key);
                if (parser == null)
                    throw new EdnException("Parser not found for key: " + key);
                token = scanner.nextToken(pbr);
                if (token == Token.END_MAP_OR_SET)
                    throw new EdnException("Odd number of forms in map");
                Object value = parser.nextValue(token, pbr);
                hm.put(key, value);
                token = scanner.nextToken(pbr);
            }

            return hm;
        }

        @Override
        public T nextValue(Object token, Parseable pbr) {
            return construct(parseMap(token, pbr));
        }

        public abstract T construct(Map<?, ?> data);
    }

    /**
     * A custom parser that creates an array of objects.
     * 
     * Must override the createArray method to construct the array.
     * 
     * @author Mike
     * 
     * @param <T>
     */
    public static abstract class ArrayParser<T> extends
        AbstractCustomParser<T[]> {
        private AbstractCustomParser<?> elementParser;

        public <K> ArrayParser(AbstractCustomParser<K> elementParser) {
            this.elementParser = elementParser;
        }

        public T[] nextValue(Object token, Parseable pbr) {
            if (token != Token.BEGIN_VECTOR) {
                throw new EdnException("Not the start of a vector: " + token);
            }
            token = scanner.nextToken(pbr);

            ArrayList<Object> al = new ArrayList<Object>();
            while (token != Token.END_VECTOR) {
                Object value = elementParser.nextValue(token, pbr);
                al.add(value);
                token = scanner.nextToken(pbr);
            }
            return al.toArray(createArray(al.size()));
        }

        public abstract T[] createArray(int size);
    }

    /**
     * Custom parser for Double primitives, or numbers that can become doubles
     * 
     * @author Mike
     */
    public static class DoubleParser extends AbstractCustomParser<Double> {
        @Override
        public Double nextValue(Object firstToken, Parseable pbr) {
            if (firstToken instanceof Double) {
                return (Double) firstToken;
            } else if (firstToken instanceof Number) {
                return ((Number) firstToken).doubleValue();
            }
            throw new EdnException("Not parseable as Double: " + firstToken);
        }
    }

    public static final DoubleParser DOUBLE_PARSER = new DoubleParser();

    /**
     * Custom parser for Long primitives, or integers that can become longs
     * 
     * @author Mike
     */
    public static class LongParser extends AbstractCustomParser<Long> {
        @Override
        public Long nextValue(Object firstToken, Parseable pbr) {
            if (firstToken instanceof Long) {
                return (Long) firstToken;
            } else if (firstToken instanceof Integer) {
                return (long) ((Integer) firstToken);
            }
            throw new EdnException("Not parseable as Long: " + firstToken);
        }
    }

    public static final LongParser LONG_PARSER = new LongParser();

}
