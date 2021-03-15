package com.videncuro.pair;

import java.util.Objects;

public class Pair<Left, Right> {
    private final Left left;
    private final Right right;

    private Pair(Left left, Right right) {
        this.left = Objects.requireNonNull(left, "Left should not be null");
        this.right = Objects.requireNonNull(right, "Right should not be null");
    }

    public static <Left, Right> Pair<Left, Right> of(Left left, Right right) {
        return new Pair<>(left, right);
    }

    public Left left() {
        return left;
    }

    public Right right() {
        return right;
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
}
