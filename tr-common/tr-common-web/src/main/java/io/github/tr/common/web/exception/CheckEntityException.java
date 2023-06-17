package io.github.tr.common.web.exception;

import io.github.tr.common.web.utils.CheckEntityResult;
import io.github.tr.common.web.utils.SingleCheckEntityResult;
import lombok.Getter;

import java.util.List;

public class CheckEntityException extends RuntimeException{
    @Getter
    private final List<SingleCheckEntityResult> result;
    public CheckEntityException(CheckEntityResult e) {
        this.result = e.getResult();
    }
}
