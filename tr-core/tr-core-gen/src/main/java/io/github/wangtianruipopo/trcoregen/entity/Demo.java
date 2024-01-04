package io.github.wangtianruipopo.trcoregen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("")
@EqualsAndHashCode(callSuper = true)
@Data
public class Demo extends Model<Demo> {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField()
    private LocalDate date;
    private BigDecimal b;

    private boolean bool;

}
