package tdd.groomingzone.domain.board.utils;

import lombok.Getter;

@Getter
public enum BoardEnums {

    MINIMUM_PAGE_NUMBER_VALUE(1);

    private final int value;

    BoardEnums(int value) {
        this.value = value;
    }
}
