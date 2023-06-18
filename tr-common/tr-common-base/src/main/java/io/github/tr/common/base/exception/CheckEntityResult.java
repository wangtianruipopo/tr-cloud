package io.github.tr.common.base.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

//@Schema(description = "保存前的校验方法返回的校验信息")
public class CheckEntityResult {
    @Getter
//    @Schema(description = "校验未通过时的返回结果")
    private final List<SingleCheckEntityResult> result = new ArrayList<>();;

    public void add(String column, String message) {
        SingleCheckEntityResult singleCheckEntityResult = SingleCheckEntityResult.builder()
                .column(column)
                .message(message)
                .build();
        this.result.add(singleCheckEntityResult);
    }

    public boolean isPassed() {
        return this.result.isEmpty();
    }
}
