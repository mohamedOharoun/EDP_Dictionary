public class DummyDictionaryMissing extends Dictionary implements Missing {
    @Override
    public Object missing(Object key) {
        return "Default from Missing";
    }
}
