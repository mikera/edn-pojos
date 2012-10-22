package us.bpsm.edn.pojos;

import us.bpsm.edn.parser.Parseable;
import us.bpsm.edn.parser.Scanner;
import us.bpsm.edn.parser.Scanners;

/**
 * Abstract base class for custom parsers.
 * 
 * Can be extended to create a new type of custom parser.
 * 
 * @author Mike
 * 
 * @param <T>
 */
public abstract class AbstractCustomParser<T> implements CustomParser<T> {

    protected final Scanner scanner;

    protected AbstractCustomParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public AbstractCustomParser() {
        this(Scanners.newScanner());
    }

    public T nextValue(Parseable pbr) {
        Object peek = scanner.nextToken(pbr);
        return nextValue(peek, pbr);
    }

    public abstract T nextValue(Object firstToken, Parseable pbr);

}
