package tdd.groomingzone.global.utils;

import lombok.Getter;

@Getter
public enum CommonEnums {

    NEW_INSTANCE(0),
    MINIMUM_PAGE_NUMBER_VALUE(1),
    PAGE_SIZE(20);

    private final int value;

    CommonEnums(int value) {
        this.value = value;
    }
}
