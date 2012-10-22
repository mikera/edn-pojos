package mikera.edn.pojos;

import us.bpsm.edn.parser.Parseable;
import us.bpsm.edn.parser.Parser;

public interface CustomParser<T> extends Parser {

    public T nextValue(Object firstToken, Parseable pbr);

    public T nextValue(Parseable pbr);

}
