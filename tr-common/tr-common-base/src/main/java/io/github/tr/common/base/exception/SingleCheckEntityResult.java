package io.github.tr.common.base.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Schema(description = "保存前的校验方法校验失败时返回的校验信息")
public class SingleCheckEntityResult {
//    @Schema(description = "校验失败的字段列名")
    private String column;
//    @Schema(description = "失败原因")
    private String message;
}
