package com.uptang.cloud.behavior.pojo.vo;

import com.uptang.cloud.starter.common.util.Validator;
import com.uptang.cloud.starter.common.validation.GroupCreate;
import com.uptang.cloud.starter.common.validation.GroupUpdate;
import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserInfoVO extends BaseVO<UserInfoVO> implements Serializable, Cloneable {
    private static final long serialVersionUID = -747494520404788237L;

    @ApiModelProperty(notes = "用户ID")
    private String id;

    @NotBlank(groups = {GroupCreate.class, GroupUpdate.class}, message = "用户名称不能为空")
    @Length(groups = {GroupCreate.class, GroupUpdate.class}, max = 20, message = "用户名称不能超过10个字符")
    @Pattern(groups = {GroupCreate.class, GroupUpdate.class}, regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9*]*$", message = "用户名称：最多10字符，包含文字、字母和数字")
    @ApiModelProperty(notes = "用户名称")
    private String userName;

    @ApiModelProperty(notes = "用户编号")
    private String userCode;

    @NotBlank(groups = {GroupCreate.class, GroupUpdate.class}, message = "手机号不能为空")
    @Pattern(groups = {GroupCreate.class, GroupUpdate.class}, regexp = Validator.REGEX_MOBILE, message = "手机号格式不正确")
    @ApiModelProperty(notes = "手机号")
    private String mobile;

    @NotBlank(groups = GroupCreate.class, message = "电子邮箱不能为空")
    @Pattern(groups = GroupCreate.class, regexp = Validator.REGEX_EMAIL, message = "电子邮箱格式不正确")
    @ApiModelProperty(notes = "电子邮箱")
    private String email;

    @ApiModelProperty(notes = "单位名称")
    private String schoolName;

    @ApiModelProperty(notes = "创建时间")
    private Date createdTime;

    @ApiModelProperty(notes = "修改时间")
    private Date updatedTime;
}