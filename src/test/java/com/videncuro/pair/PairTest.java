package com.videncuro.pair;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PairTest {

    @Test
    void createPairLeftAndRight() {
        Pair<String, String> pair = Pair.of("hello", "world");
        Assertions.assertAll("match pair",
                () -> assertThat(pair, left(is("hello"))),
                () -> assertThat(pair, right(is("world")))
        );
    }

    @Test
    void leftNonNull() {
        Assertions.assertThrows(NullPointerException.class, () -> Pair.of(null, "right"));
    }

    @Test
    void rightNonNull() {
        Assertions.assertThrows(NullPointerException.class, () -> Pair.of("left", null));
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