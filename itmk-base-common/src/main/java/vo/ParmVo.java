package vo;

import lombok.Data;

@Data
public class ParmVo {
    /**
     * 页容量
     */
    private int pageSize;
    /**
     * 当前页
     */
    private int currentPage;
}
