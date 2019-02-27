/**
 * Shuhe confidential
 * <p>
 * Copyright (C) 2017 Shanghai Shuhe Co., Ltd. All rights reserved.
 * <p>
 * No parts of this file may be reproduced or transmitted in any form or by any means,
 * electronic, mechanical, photocopying, recording, or otherwise, without prior written
 * permission of Shanghai Shuhe Co., Ltd.
 */
package cn.openapi;

/**
 * Created by gogogo on 17/11/3.
 */
public class ShuheResponse {

    private int statusCode;

    private String entityString;

    public ShuheResponse(int statusCode, String entityString) {
        this.statusCode = statusCode;
        this.entityString = entityString;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getEntityString() {
        return entityString;
    }

    public void setEntityString(String entityString) {
        this.entityString = entityString;
    }
}
