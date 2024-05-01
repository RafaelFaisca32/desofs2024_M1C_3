package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDriverAllPropertiesEquals(Driver expected, Driver actual) {
        assertDriverAutoGeneratedPropertiesEquals(expected, actual);
        assertDriverAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDriverAllUpdatablePropertiesEquals(Driver expected, Driver actual) {
        assertDriverUpdatableFieldsEquals(expected, actual);
        assertDriverUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDriverAutoGeneratedPropertiesEquals(Driver expected, Driver actual) {
        assertThat(expected)
            .as("Verify Driver auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDriverUpdatableFieldsEquals(Driver expected, Driver actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDriverUpdatableRelationshipsEquals(Driver expected, Driver actual) {
        assertThat(expected)
            .as("Verify Driver relationships")
            .satisfies(e -> assertThat(e.getTruck()).as("check truck").isEqualTo(actual.getTruck()));
    }
}
