package de.upb.sede.util;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

public class RandomArbiter<T> implements Function<List<T>, Optional<T>> {

    private Random random = new Random();

    @Override
    public Optional<T> apply(List<T> ts) {
        if(ts.isEmpty()) {
            return Optional.empty();
        } else {
            int randomIndex = random.nextInt(ts.size());
            return Optional.of(ts.get(randomIndex));
        }
    }
}
