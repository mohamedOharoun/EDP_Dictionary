public class DummyDictionaryMissing extends Dictionary implements Missing<String> {
    @Override
    public String missing(Object key) {
        return "Default from Missing";
    }
}
