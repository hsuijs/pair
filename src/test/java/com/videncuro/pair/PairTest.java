package com.videncuro.pair;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PairTest {

    @Test
    void leftNonNull() {
        Assertions.assertThrows(NullPointerException.class, () -> Pair.of(null, "right"));
    }

    @Test
    void rightNonNull() {
        Assertions.assertThrows(NullPointerException.class, () -> Pair.of("left", null));
    }

    @Test
    void createPairLeftAndRight() {
        Pair<String, String> pair = Pair.of("hello", "world");
        Assertions.assertAll("match pair",
                () -> assertThat(pair, left(is("hello"))),
                () -> assertThat(pair, right(is("world")))
        );
    }

    @Test
    void mapEntryCannotBeNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> Pair.of(null)
        );
    }

    @Test
    void createPairFromMapEntry() {
        Pair<String, String> pair = Pair.of(new Map.Entry<String, String>() {
            @Override
            public String getKey() {
                return "key";
            }

            @Override
            public String getValue() {
                return "value";
            }

            @Override
            public String setValue(String value) {
                return null;
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        });
        Assertions.assertAll(
                () -> assertThat(pair, left(is("key"))),
                () -> assertThat(pair, right(is("value")))
        );
    }

    @Test
    void optionalPair() {
        Optional<Pair<String, String>> optionalPair = Pair.optionalPair("1", "2");
        assertThat(optionalPair, is(Optional.of(Pair.of("1", "2"))));
    }

    @Test
    void optionalLeftEmpty() {
        Optional<Pair<String, String>> optionalPair = Pair.optionalPair(null, "2");
        assertThat(optionalPair, is(Optional.empty()));
    }

    @Test
    void optionalRightEmpty() {
        Optional<Pair<String, String>> optionalPair = Pair.optionalPair("1", null);
        assertThat(optionalPair, is(Optional.empty()));
    }

    @Test
    void createFromObject() {
        Pair<String, String> pair = Pair.<String, String, String>pairOf(s -> s.substring(0, 1), s1 -> s1.substring(1))
                .apply("Hello");
        Assertions.assertAll(
                () -> assertThat(pair, left(is("H"))),
                () -> assertThat(pair, right(is("ello")))
        );
    }

    @Test
    void pairReturnsKeyAndValue() {
        Pair<String, String> pair = Pair.of("key", "value");
        Assertions.assertAll(
                () -> assertThat(pair, left(is("key"))),
                () -> assertThat(pair, right(is("value")))
        );
    }

    @Test
    void mapAsmapLeft() {
        Pair<String, String> pair = Pair.of("hello", "world")
                .map(s -> s + " name");
        Assertions.assertAll(
                () -> assertThat(pair, left(is("hello name"))),
                () -> assertThat(pair, right(is("world")))
        );
    }

    @Test
    void mapLeft() {
        Pair<String, String> pair = Pair.of("hello", "world")
                .mapLeft(s -> s + " name");
        Assertions.assertAll(
                () -> assertThat(pair, left(is("hello name"))),
                () -> assertThat(pair, right(is("world")))
        );
    }

    @Test
    void mapRight() {
        Pair<String, String> pair = Pair.of("hello", "world")
                .mapRight(s -> "all the " + s);
        Assertions.assertAll(
                () -> assertThat(pair, left(is("hello"))),
                () -> assertThat(pair, right(is("all the world")))
        );
    }

    @Test
    void fold() {
        Pair<String, String> pair = Pair.of("left", "right");
        assertThat(pair.fold((l, r) -> l + " " +r), is("left right"));
    }

    @Test
    void swap() {
        Pair<String, String> pair = Pair.of("world", "hello").swap();
        Assertions.assertAll(
                () -> assertThat(pair, left(is("hello"))),
                () -> assertThat(pair, right(is("world")))
        );
    }

    @Test
    void flatMap() {
        Pair<String, String> pair = Pair.of("left", "right").flatMap((l, r) -> Pair.of(l+r, r+l));
        Assertions.assertAll(
                () -> assertThat(pair, left(is("leftright"))),
                () -> assertThat(pair, right(is("rightleft")))
        );
    }

    private <Right> Matcher<? super Pair<?, Right>> right(Matcher<Right> matchRight) {
        return new FeatureMatcher<Pair<?, Right>, Right>(matchRight, "right", "right") {
            protected Right featureValueOf(Pair<?, Right> actual) {
                return actual.right();
            }
        };
    }

    private <Left> Matcher<? super Pair<Left, ?>> left(Matcher<Left> matchLeft) {
        return new FeatureMatcher<Pair<Left, ?>, Left>(matchLeft, "left", "left") {

            protected Left featureValueOf(Pair<Left, ?> actual) {
                return actual.left();
            }
        };
    }
}