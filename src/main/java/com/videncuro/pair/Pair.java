package com.videncuro.pair;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public final class Pair<Left, Right> implements Serializable,
        Map.Entry<Left, Right> {
    private final Left left;
    private final Right right;

    private Pair(Left left, Right right) {
        this.left = Objects.requireNonNull(left, "Left should not be null");
        this.right = Objects.requireNonNull(right, "Right should not be null");
    }

    public static <Left, Right> Pair<Left, Right> of(Left left, Right right) {
        return new Pair<>(left, right);
    }

    public static <Left, Right> Pair<Left, Right> of(Map.Entry<Left, Right> entry) {
        return new Pair<>(entry.getKey(), entry.getValue());
    }

    public Left left() {
        return left;
    }

    public Right right() {
        return right;
    }

    @Override
    public Left getKey() {
        return left;
    }

    @Override
    public Right getValue() {
        return right;
    }

    @Override
    public Right setValue(Right value) {
        throw new RuntimeException("Pair is immutable");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return String.format("Pair{left=%s, right=%s}", left, right);
    }

    public <NewLeft> Pair<NewLeft, Right> mapLeft(Function<Left, NewLeft> mapper) {
        return Pair.of(mapper.apply(left), right);
    }

    public <NewLeft> Pair<NewLeft, Right> map(Function<Left, NewLeft> mapper) {
        return mapLeft(mapper);
    }
    
    public <NewRight> Pair<Left, NewRight> mapRight(Function<Right, NewRight> mapper) {
        return Pair.of(left, mapper.apply(right));
    }
}
