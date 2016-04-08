package org.alljet.dal.impl;

import org.alljet.dal.dialect.AbstractDialect;

public class SqlServerDialect extends AbstractDialect {

    public String getLimitStringForRandom(String sql) {
        // return new StringBuffer(sql.length() + 100).append("SELECT TOP :_limit * FROM ").append(sql).toString();
        // StringBuilder pagingSelect = new StringBuilder(sql.length() + 250)
        // .append("select * from ( select inner2_.*, rownumber() over(order by order of inner2_) as rownumber_ from ( ")
        // .append(sql).append(" order by rand() ").append(" fetch first :_limit")
        // .append(" rows only ) as inner2_ ) as inner1_").append(" order by rownumber_");
        StringBuilder pagingSelect = new StringBuilder(sql.length() + 250)
                .append("SELECT * FROM (SELECT inner2_.*, ROW_NUMBER() OVER (ORDER BY RAND()) AS RowNumber FROM  (")
                .append(sql)
                .append(") as inner2_ ) as inner1_ WHERE RowNumber > :_offset AND RowNumber <= (:_offset + :_limit)")
                .append(" order by RowNumber");
        return pagingSelect.toString();
    }

    public String getLimitString(String sql) {
        // return new StringBuffer(sql.length() + 100)
        // .append("SELECT * FROM (SELECT TOP 1 * FROM ( SELECT TOP 2   * FROM ").append(sql)
        // .append("ORDER BY id ASC ) as aSysTable   ORDER BY id DESC ) as bSysTable   ORDER BY id ASC")
        // .toString();
        // StringBuilder pagingSelect = new StringBuilder(sql.length() + 200)
        // .append("select * from ( select inner2_.*, rownumber() over(order by order of inner2_) as rownumber_ from ( ")
        // .append(sql).append(" fetch first :_limit")
        // .append(" rows only ) as inner2_ ) as inner1_ where rownumber_ > :_offset")
        // .append(" order by rownumber_");
        // return pagingSelect.toString();
        StringBuilder pagingSelect = new StringBuilder(sql.length() + 200)
                .append("SELECT * FROM (SELECT inner2_.*, ROW_NUMBER() OVER (ORDER BY inner2_.id DESC) AS RowNumber FROM  (")
                .append(sql)
                .append(") as inner2_ ) as inner1_ WHERE RowNumber > :_offset AND RowNumber <= (:_offset + :_limit)")
                .append(" order by RowNumber");
        return pagingSelect.toString();
    }

}
