package cn.caijiajia.credittools.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @Author:chendongdong
 * @Date:2018/4/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankForm {
    @NotEmpty
    private String productId;
    @NotNull
    private Integer currentRank;
    @NotNull
    private Integer changedRank;
}
