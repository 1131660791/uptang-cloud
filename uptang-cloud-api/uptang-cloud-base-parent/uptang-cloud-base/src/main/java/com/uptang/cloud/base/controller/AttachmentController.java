package com.uptang.cloud.base.controller;

import com.uptang.cloud.base.common.converter.AttachmentConverter;
import com.uptang.cloud.base.common.domain.PaperImageSource;
import com.uptang.cloud.base.common.enums.AttachmentEnum;
import com.uptang.cloud.base.common.model.Attachment;
import com.uptang.cloud.base.common.vo.AttachmentVO;
import com.uptang.cloud.base.feign.AttachmentProvider;
import com.uptang.cloud.base.service.AttachmentService;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import com.uptang.cloud.starter.web.annotation.JsonResult;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@Slf4j
@RestController
@RequestMapping("/v1/attachments")
@Api(value = "AttachmentController", tags = {"附件管理"})
public class AttachmentController extends BaseController implements AttachmentProvider {
    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    /**
     * 附件详情
     *
     * @param id 附件id
     * @return 查询到的附件详情
     */
    @ApiOperation(value = "附件详情", response = AttachmentVO.class)
    @ApiImplicitParam(name = "id", value = "附件ID", paramType = "path", required = true)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiOut<AttachmentVO> getDetail(@PathVariable("id") @NotNull Long id) {
        if (NumberUtils.isNotPositive(id)) {
            return ApiOut.newParameterRequiredResponse("附件ID");
        }

        Attachment attachment = attachmentService.load(id);
        return ApiOut.newSuccessResponse(AttachmentConverter.INSTANCE.toVo(attachment));
    }


    /**
     * 批量获得附件详情
     *
     * @param ids 批量ids
     * @return 查询到的附件详情
     */
    @ApiOperation(value = "批量获得附件详情", response = AttachmentVO.class)
    @ApiImplicitParam(name = "ids", value = "附件IDs, 以半角逗号分隔", paramType = "query", required = true, example = "1,2,3")
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiOut<List<AttachmentVO>> getDetails(@RequestParam("ids") Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return ApiOut.newParameterRequiredResponse("附件ID");
        }

        Map<Long, Attachment> attachmentMap = attachmentService.findByIds(ids);
        if (MapUtils.isEmpty(attachmentMap)) {
            return ApiOut.newResponse(ResponseCodeEnum.DATA_NOT_FOUND, "附件");
        }

        return ApiOut.newSuccessResponse(toVos(attachmentMap.values()));
    }


    /**
     * 处理图片，剪裁/接接
     * <pre>
     * [{
     *   "height": 476,
     *   "path": "/21/20191010/7/7_18120190918114540270_a.jpg",
     *   "width": 1427,
     *   "x": 110,
     *   "y": 1297
     * }, {
     *   "height": 458,
     *   "path": "/21/20191010/7/7_18120190918114540270_b.jpg",
     *   "width": 1409,
     *   "x": 116,
     *   "y": 161
     * }, {
     *   "height": 1101,
     *   "path": "/21/20191010/7/7_18120190918114540270_b.jpg",
     *   "width": 1398,
     *   "x": 131,
     *   "y": 645
     * }, {
     *   "height": 1132,
     *   "path": "/21/20191010/7/7_18120190918114540270_b.jpg",
     *   "width": 1430,
     *   "x": 111,
     *   "y": 641
     * }]
     * </pre>
     *
     * @param response     HttpServletResponse
     * @param mode         拼接模式（horizontally:横拼, vertically:竖拼）
     * @param imageSources 处理参数
     * @throws Exception 处理图片失败
     */
    @ApiOperation(value = "处理（剪切/拼接）图片", response = Void.class)
    @ApiImplicitParam(name = "mode", value = "拼接模式（horizontally:横拼, vertically:竖拼）", paramType = "query", defaultValue = "vertically")
    @PostMapping(path = "/image.png", produces = MediaType.IMAGE_PNG_VALUE)
    public void getImage(HttpServletResponse response,
                         @RequestParam(name = "mode", defaultValue = "vertically", required = false) String mode,
                         @RequestBody @Validated PaperImageSource[] imageSources) throws Exception {

        if (ArrayUtils.isEmpty(imageSources)) {
            throw new BusinessException(ResponseCodeEnum.PARAMETER_REQUIRED.getCode(), "图片ID或图片路径");
        }

        // 检查图片参数
        Arrays.stream(imageSources).forEach(source -> {
            if (NumberUtils.isNotPositive(source.getId()) && StringUtils.isBlank(source.getPath())) {
                throw new BusinessException(ResponseCodeEnum.PARAMETER_REQUIRED.getCode(), "图片ID或图片路径");
            }

            if (NumberUtils.isNotPositive(source.getWidth()) || NumberUtils.isNotPositive(source.getHeight())) {
                throw new BusinessException(ResponseCodeEnum.PARAMETER_REQUIRED.getCode(), "图片裁切范围");
            }
        });

        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a png
        response.setContentType("image/png");

        ServletOutputStream outStream = response.getOutputStream();
        BufferedImage image = attachmentService.processImage("vertically".equalsIgnoreCase(mode), imageSources);

        // write the data out
        ImageIO.write(image, "png", outStream);
        outStream.flush();
        outStream.close();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * 上传附件
     *
     * @param type             附件类别
     * @param multipartRequest 附件
     * @return 上传成功的附件
     */
    @ApiOperation(value = "上传附件", response = AttachmentVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "附件类型, 支持[EXAM_PAPER, OTHER]"),
            @ApiImplicitParam(name = "keepOriginalFilename", value = "是否保持原来的文件名", example = "false"),
            @ApiImplicitParam(name = "multipartRequest", value = "附件")
    })
    @JsonResult(type = AttachmentVO.class, include = {"id", "srcName", "relativePath", "fullPath"})
    @PostMapping(path = "/{type}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiOut<List<AttachmentVO>> upload(@PathVariable("type") @NotBlank String type,
                                             @RequestParam(required = false, defaultValue = "false") boolean keepOriginalFilename,
                                             @NotNull MultipartHttpServletRequest multipartRequest) {
        if (null == multipartRequest || MapUtils.isEmpty(multipartRequest.getFileMap())) {
            return ApiOut.newParameterRequiredResponse("上传的文件不正确");
        }

        AttachmentEnum attachmentType = AttachmentEnum.parse(type);
        if (Objects.isNull(attachmentType)) {
            return ApiOut.newParameterRequiredResponse("上传附件类别不正确");
        }

        List<Attachment> attachments = attachmentService.upload(attachmentType, keepOriginalFilename, buildMultipartFiles(multipartRequest));
        return ApiOut.newSuccessResponse(toVos(attachments));
    }

    /**
     * 转换成文件列表
     */
    private MultipartFile[] buildMultipartFiles(MultipartHttpServletRequest multipartRequest) {
        if (Objects.isNull(multipartRequest) || MapUtils.isEmpty(multipartRequest.getFileMap())) {
            return null;
        }

        return multipartRequest.getMultiFileMap().values()
                .stream().flatMap(Collection::stream)
                .filter(file -> Objects.nonNull(file) && !file.isEmpty())
                .toArray(MultipartFile[]::new);
    }

    /**
     * 转换成VO
     *
     * @param attachments 附件列表
     * @return VOs
     */
    private List<AttachmentVO> toVos(Collection<Attachment> attachments) {
        if (CollectionUtils.isEmpty(attachments)) {
            return new ArrayList<>(0);
        }

        return attachments.stream().map(AttachmentConverter.INSTANCE::toVo)
                .sorted(Comparator.comparing(AttachmentVO::getId))
                .collect(Collectors.toList());
    }
}
