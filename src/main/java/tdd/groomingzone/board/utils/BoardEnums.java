package tdd.groomingzone.board.utils;

import lombok.Getter;

@Getter
public enum BoardEnums {

    NEW_INSTANCE(0),
    MINIMUM_PAGE_NUMBER_VALUE(1),
    PAGE_SIZE(20);

    private final int value;

    BoardEnums(int value) {
        this.value = value;
    }
}
