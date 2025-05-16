package core.basesyntax.repository;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getProvider(String key);
}
