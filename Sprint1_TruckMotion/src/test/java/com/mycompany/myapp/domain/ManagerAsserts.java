package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ManagerAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertManagerAllPropertiesEquals(Manager expected, Manager actual) {
        assertManagerAutoGeneratedPropertiesEquals(expected, actual);
        assertManagerAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertManagerAllUpdatablePropertiesEquals(Manager expected, Manager actual) {
        assertManagerUpdatableFieldsEquals(expected, actual);
        assertManagerUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertManagerAutoGeneratedPropertiesEquals(Manager expected, Manager actual) {
        assertThat(expected)
            .as("Verify Manager auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertManagerUpdatableFieldsEquals(Manager expected, Manager actual) {}

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertManagerUpdatableRelationshipsEquals(Manager expected, Manager actual) {
        assertThat(expected)
            .as("Verify Manager relationships")
            .satisfies(e -> assertThat(e.getApplicationUser()).as("check applicationUser").isEqualTo(actual.getApplicationUser()));
    }
}