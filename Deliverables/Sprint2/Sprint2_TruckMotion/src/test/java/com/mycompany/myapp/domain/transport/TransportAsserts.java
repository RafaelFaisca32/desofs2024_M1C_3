package com.mycompany.myapp.domain.transport;

import static com.mycompany.myapp.domain.AssertUtils.zonedDataTimeSameInstant;
import static org.assertj.core.api.Assertions.assertThat;

public class TransportAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTransportAllPropertiesEquals(Transport expected, Transport actual) {
        assertTransportAutoGeneratedPropertiesEquals(expected, actual);
        assertTransportAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTransportAllUpdatablePropertiesEquals(Transport expected, Transport actual) {
        assertTransportUpdatableFieldsEquals(expected, actual);
        assertTransportUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTransportAutoGeneratedPropertiesEquals(Transport expected, Transport actual) {
        assertThat(expected)
            .as("Verify Transport auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTransportUpdatableFieldsEquals(Transport expected, Transport actual) {
        assertThat(expected)
            .as("Verify Transport relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getStartTime())
                        .as("check startTime")
                        //.usingComparator(zonedDataTimeSameInstant)
                        .isEqualTo(actual.getStartTime())
            )
            .satisfies(
                e ->
                    assertThat(e.getEndTime()).as("check endTime").
                        //usingComparator(zonedDataTimeSameInstant).
                        isEqualTo(actual.getEndTime())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTransportUpdatableRelationshipsEquals(Transport expected, Transport actual) {
        assertThat(expected)
            .as("Verify Transport relationships")
            .satisfies(e -> assertThat(e.getLocation()).as("check location").isEqualTo(actual.getLocation()))
            .satisfies(e -> assertThat(e.getDriver()).as("check driver").isEqualTo(actual.getDriver()))
            .satisfies(e -> assertThat(e.getServiceRequest()).as("check serviceRequest").isEqualTo(actual.getServiceRequest()));
    }
}
