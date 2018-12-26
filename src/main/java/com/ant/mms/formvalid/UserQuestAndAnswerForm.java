package com.ant.mms.formvalid;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by wolf
 */
@Data
public class UserQuestAndAnswerForm {

    @NotEmpty(message = "问题不能为空")
    private String question;
    /** 答案. */
    @NotEmpty(message = "答案不能为空")
    private String answer;

}
