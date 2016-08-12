package cc.doublez.platform.job;

import cc.doublez.domain.pojo.Row;

/**
 * Created by yz on 2016/7/22
 */
public class JobParam {
    private int id;

    private Row row;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }
}
