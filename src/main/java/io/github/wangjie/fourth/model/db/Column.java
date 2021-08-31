package io.github.wangjie.fourth.model.db;

import lombok.*;

/**
 * @ClassName Column
 * @Description
 * @Author wangjie
 * @Date 2020/5/21 1:41 下午
 * @Email wangjie_fourth@163.com
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Column {

    private String columnName;

    private String columnComment;

    private String columnKey;

    private String dataType;

    private String columnType;

    private String columnDefault;

    private String extra;

    private String nullAble;

    private String columnSize;

}
