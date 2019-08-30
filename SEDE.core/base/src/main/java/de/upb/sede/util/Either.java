package de.upb.sede.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Either<L,R>
{
    public static <L,R> Either<L,R> left(L value) {
        return new Either<>(OptionalField.of(value), OptionalField.empty());
    }
    public static <L,R> Either<L,R> right(R value) {
        return new Either<>(OptionalField.empty(), OptionalField.of(value));
    }
    private final OptionalField<L> left;
    private final OptionalField<R> right;
    private Either(OptionalField<L> l, OptionalField<R> r) {
        left=l;
        right=r;
    }
    public <T> T map(
        Function<? super L, ? extends T> lFunc,
        Function<? super R, ? extends T> rFunc)
    {
        return left.<T>map(lFunc).orElseGet(()->right.map(rFunc).get());
    }
    public <T> Either<T,R> mapLeft(Function<? super L, ? extends T> lFunc)
    {
        return new Either<>(left.map(lFunc),right);
    }
    public <T> Either<L,T> mapRight(Function<? super R, ? extends T> rFunc)
    {
        return new Either<>(left, right.map(rFunc));
    }
    public void apply(Consumer<? super L> lFunc, Consumer<? super R> rFunc)
    {
        left.ifPresent(lFunc);
        right.ifPresent(rFunc);
    }

    public boolean hasLeft() {
        return left.isPresent();
    }

    public boolean hasRight() {
        return right.isPresent();
    }

    public L getLeft() {
        return left.orElse(null);
    }

    public R getRight() {
        return right.orElse(null);
    }
}
