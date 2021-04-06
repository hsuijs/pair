package com.videncuro.pair;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Pair<Left, Right> implements Serializable,
        Map.Entry<Left, Right> {
    private final Left left;
    private final Right right;

    private Pair(Left left, Right right) {
        this.left = Objects.requireNonNull(left, "Left should not be null");
        this.right = Objects.requireNonNull(right, "Right should not be null");
    }

    public static <T, Left, Right> Function<T, Pair<Left, Right>> pairOf(Function<T, Left> leftCreator, Function<T, Right> rightCreator) {
        Objects.requireNonNull(leftCreator, "LeftCreator should not be null");
        Objects.requireNonNull(rightCreator, "RightCreator should not be null");
        return t -> Pair.of(leftCreator.apply(t), rightCreator.apply(t));
    }

    public static <Left, Right> Pair<Left, Right> of(Left left, Right right) {
        return new Pair<>(left, right);
    }

    public static <Left, Right> Pair<Left, Right> of(Map.Entry<Left, Right> entry) {
        Objects.requireNonNull(entry, "Entry should not be null");
        return new Pair<>(entry.getKey(), entry.getValue());
    }

    public static <Left, Right> Optional<Pair<Left, Right>> optionalPair(Left left, Right right) {
        return Optional.ofNullable(left).flatMap(l -> Optional.ofNullable(right).map(r -> Pair.of(l, r)));
    }

    public static <Left, Right, NewLeft> Function<Pair<Left, Right>, Pair<NewLeft, Right>> mapPairLeft(Function<Left, NewLeft> mapper) {
        Objects.requireNonNull(mapper, "Mapper should not be null");
        return pair -> pair.mapLeft(mapper);
    }

    public static <Left, Right, NewRight> Function<Pair<Left, Right>, Pair<Left, NewRight>> mapPairRight(Function<Right, NewRight> mapper) {
        Objects.requireNonNull(mapper, "Mapper should not be null");
        return pair -> pair.mapRight(mapper);
    }

    public static <Left, Right, NewLeft, NewRight> Function<Pair<Left, Right>, Pair<NewLeft, NewRight>> flatMapPair(BiFunction<Left, Right, Pair<NewLeft, NewRight>> mapper) {
        Objects.requireNonNull(mapper, "Mapper should not be null");
        return pair -> pair.flatMap(mapper);
    }

    public static <Left, Right, NewLeft, NewRight> Function<Pair<Left, Right>, Pair<NewLeft, NewRight>> mapPair(Function<Left, NewLeft> leftMapper, Function<Right, NewRight> rightMapper) {
        Objects.requireNonNull(leftMapper, "LeftMapper should not be null");
        Objects.requireNonNull(rightMapper, "RightMapper should not be null");
        return pair -> pair.map(leftMapper, rightMapper);
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
        Objects.requireNonNull(mapper, "Mapper should not be null");
        return Pair.of(mapper.apply(left), right);
    }

    public <NewLeft, NewRight> Pair<NewLeft, NewRight> map(Function<Left, NewLeft> leftMapper, Function<Right, NewRight> rightMapper) {
        Objects.requireNonNull(leftMapper, "LeftMapper should not be null");
        Objects.requireNonNull(rightMapper, "RightMapper should not be null");
        return Pair.of(leftMapper.apply(left), rightMapper.apply(right));
    }

    public <NewRight> Pair<Left, NewRight> mapRight(Function<Right, NewRight> mapper) {
        Objects.requireNonNull(mapper, "Mapper should not be null");
        return Pair.of(left, mapper.apply(right));
    }

    public <T> T fold(BiFunction<Left, Right, T> foldFunction) {
        Objects.requireNonNull(foldFunction, "FoldFunction should not be null");
        return foldFunction.apply(left, right);
    }

    public Pair<Right, Left> swap() {
        return Pair.of(right, left);
    }

    public <NewLeft, NewRight> Pair<NewLeft, NewRight> flatMap(BiFunction<Left, Right, Pair<NewLeft, NewRight>> mapper) {
        Objects.requireNonNull(mapper, "Mapper should not be null");
        return mapper.apply(left, right);
    }

    public boolean and(Predicate<Left> leftPredicate, Predicate<Right> rightPredicate) {
        return leftPredicate.test(left) && rightPredicate.test(right);
    }

    public boolean or(Predicate<Left> leftPredicate, Predicate<Right> rightPredicate) {
        return leftPredicate.test(left) || rightPredicate.test(right);
    }
}
