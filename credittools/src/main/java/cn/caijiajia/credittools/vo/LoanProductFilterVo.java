/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.vo;

import cn.caijiajia.credittools.bo.LoanProductFilterBo;
import cn.caijiajia.credittools.bo.LoanProductSortBo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by liujianyang on 2018/5/4.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanProductFilterVo {
    private String filterName;
    private List<LoanProductFilterBo> filters;
    private LoanProductSortBo sort;
}