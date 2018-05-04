/**
 * Caijiajia confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.caijiajia.credittools.form;

import cn.caijiajia.credittools.constant.ProductFilterTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liujianyang on 2018/5/3.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListClientForm {
    private ProductFilterTypeEnum filterType;
    private String filterValue;
    private String sortValue;
}