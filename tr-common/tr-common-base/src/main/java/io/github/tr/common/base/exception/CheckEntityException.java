package io.github.tr.common.base.exception;

import lombok.Getter;

import java.util.List;

public class CheckEntityException extends RuntimeException{
    @Getter
    private final List<SingleCheckEntityResult> result;
    public CheckEntityException(CheckEntityResult e) {
        this.result = e.getResult();
    }
}
