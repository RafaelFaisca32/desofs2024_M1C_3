package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationAllPropertiesEquals(Location expected, Location actual) {
        assertLocationAutoGeneratedPropertiesEquals(expected, actual);
        assertLocationAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationAllUpdatablePropertiesEquals(Location expected, Location actual) {
        assertLocationUpdatableFieldsEquals(expected, actual);
        assertLocationUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationAutoGeneratedPropertiesEquals(Location expected, Location actual) {
        assertThat(expected)
            .as("Verify Location auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationUpdatableFieldsEquals(Location expected, Location actual) {
        assertThat(expected)
            .as("Verify Location relevant properties")
            .satisfies(e -> assertThat(e.getCoordX()).as("check coordX").isEqualTo(actual.getCoordX()))
            .satisfies(e -> assertThat(e.getCoordY()).as("check coordY").isEqualTo(actual.getCoordY()))
            .satisfies(e -> assertThat(e.getCoordZ()).as("check coordZ").isEqualTo(actual.getCoordZ()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLocationUpdatableRelationshipsEquals(Location expected, Location actual) {
        assertThat(expected)
            .as("Verify Location relationships")
            .satisfies(e -> assertThat(e.getCustomer()).as("check customer").isEqualTo(actual.getCustomer()));
    }
}
