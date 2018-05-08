package cn.caijiajia.credittools.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author:chendongdong
 * @Date:2018/4/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusForm {
    @NotBlank
    private String productId;

    @NotBlank
    private String status;
}
