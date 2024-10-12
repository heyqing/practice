package com.heyqing.spark.model.vo;

import com.heyqing.spark.model.dto.Header;
import com.heyqing.spark.model.dto.Payload;
import lombok.Data;

/**
 * ClassName:JsonParse
 * Package:com.heyqing.spark.model.vo
 * Description:
 *
 * @Date:2024/9/27
 * @Author:Heyqing
 */
@Data
public class JsonParse {
    Header header;
    Payload payload;
}
