package com.uptang.cloud.base.controller;

import com.uptang.cloud.base.common.converter.AttachmentConverter;
import com.uptang.cloud.base.common.domain.PaperImageSource;
import com.uptang.cloud.base.common.enums.AttachmentEnum;
import com.uptang.cloud.base.common.model.Attachment;
import com.uptang.cloud.base.common.support.PaperImageProcessor;
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
import java.net.URL;
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
    private final PaperImageProcessor processor;

    @Autowired
    public AttachmentController(AttachmentService attachmentService, PaperImageProcessor processor) {
        this.attachmentService = attachmentService;
        this.processor = processor;
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


    @ApiOperation(value = "计算图片路径", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examCode", value = "考试项目代码", paramType = "path", example = "xty_20191011112438446"),
            @ApiImplicitParam(name = "subjectCode", value = "科目代码", paramType = "path", example = "4"),
            @ApiImplicitParam(name = "itemNum", value = "题目号", paramType = "path", example = "90"),
            @ApiImplicitParam(name = "studentId", value = "学生ID或准考证号", paramType = "path", example = "001131701"),
            @ApiImplicitParam(name = "mode", value = "拼接模式（horizontally:横拼, vertically:竖拼）", paramType = "query", defaultValue = "vertically")
    })
    @GetMapping(path = "/{examCode}/{subjectCode}/{itemNum}/{studentId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiOut<String[]> getImageUrl(@NotBlank @PathVariable String examCode, @NotBlank @PathVariable String subjectCode,
                                        @NotBlank @PathVariable String itemNum, @NotBlank @PathVariable String studentId,
                                        @RequestParam(name = "mode", defaultValue = "vertically", required = false) String mode) {
        String relativePath = processor.generateUrlPath(examCode, subjectCode, itemNum, studentId, "vertically".equalsIgnoreCase(mode));
        return ApiOut.newSuccessResponse(new String[]{attachmentService.generateFullUrl(relativePath), relativePath});
    }


    @ApiOperation(value = "显示裁切后的图片", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examCode", value = "考试项目代码", paramType = "path", example = "xty_20191011112438446"),
            @ApiImplicitParam(name = "subjectCode", value = "科目代码", paramType = "path", example = "4"),
            @ApiImplicitParam(name = "itemNum", value = "题目号", paramType = "path", example = "90"),
            @ApiImplicitParam(name = "studentId", value = "学生ID或准考证号", paramType = "path", example = "001131701"),
            @ApiImplicitParam(name = "mode", value = "拼接模式（horizontally:横拼, vertically:竖拼）", paramType = "query", defaultValue = "vertically")
    })
    @GetMapping(path = "/{examCode}/{subjectCode}/{itemNum}/{studentId}.png", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void getImage(HttpServletResponse response,
                         @PathVariable String examCode, @PathVariable String subjectCode,
                         @PathVariable String itemNum, @PathVariable String studentId,
                         @RequestParam(name = "mode", defaultValue = "vertically", required = false) String mode) {
        String imageUrl = processor.generateUrlPath(examCode, subjectCode, itemNum, studentId, "vertically".equalsIgnoreCase(mode));
        try {
            BufferedImage bufferedImage = ImageIO.read(new URL(attachmentService.generateFullUrl(imageUrl)));
            response.setHeader("x-img-url", imageUrl);
            outputImage(response, bufferedImage);
        } catch (Exception ex) {
            log.error("获取物理裁切图片({})失败！", imageUrl);
        }
    }


    /**
     * http://localhost:8103/v1/attachments/xty_20191011112438446/4/90/001131701?debug=false&mode=vertically
     *
     * @param response     HttpServletResponse
     * @param examCode     考试项目代码
     * @param subjectCode  科目代码
     * @param itemNum      题目号
     * @param studentId    学生ID或准考证号
     * @param mode         拼接模式（horizontally:横拼, vertically:竖拼）
     * @param imageSources <pre>
     *                                                                                                       [{
     *                                                                                                         "height": 476,
     *                                                                                                         "path": "/21/20191010/7/7_18120190918114540270_a.jpg",
     *                                                                                                         "width": 1427,
     *                                                                                                         "x": 110,
     *                                                                                                         "y": 1297
     *                                                                                                       }, {
     *                                                                                                         "height": 458,
     *                                                                                                         "path": "/21/20191010/7/7_18120190918114540270_b.jpg",
     *                                                                                                         "width": 1409,
     *                                                                                                         "x": 116,
     *                                                                                                         "y": 161
     *                                                                                                       }, {
     *                                                                                                         "height": 1101,
     *                                                                                                         "path": "/21/20191010/7/7_18120190918114540270_b.jpg",
     *                                                                                                         "width": 1398,
     *                                                                                                         "x": 131,
     *                                                                                                         "y": 645
     *                                                                                                       }, {
     *                                                                                                         "height": 1132,
     *                                                                                                         "path": "/21/20191010/7/7_18120190918114540270_b.jpg",
     *                                                                                                         "width": 1430,
     *                                                                                                         "x": 111,
     *                                                                                                         "y": 641
     *                                                                                                       }]
     *                                                                                                     </pre>
     * @throws Exception 生成图片的异常
     */
    @ApiOperation(value = "处理图片 裁切/接接", response = Void.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examCode", value = "考试项目代码", paramType = "path", example = "xty_20191011112438446"),
            @ApiImplicitParam(name = "subjectCode", value = "科目代码", paramType = "path", example = "4"),
            @ApiImplicitParam(name = "itemNum", value = "题目号", paramType = "path", example = "90"),
            @ApiImplicitParam(name = "studentId", value = "学生ID或准考证号", paramType = "path", example = "001131701"),
            @ApiImplicitParam(name = "mode", value = "拼接模式（horizontally:横拼, vertically:竖拼）", paramType = "query", defaultValue = "vertically"),
            @ApiImplicitParam(name = "imageSources", value = "拼接参数")
    })
    @PostMapping(path = "/{examCode}/{subjectCode}/{itemNum}/{studentId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void getImage(HttpServletResponse response, @NotBlank @PathVariable String examCode, @NotBlank @PathVariable String subjectCode,
                         @NotBlank @PathVariable String itemNum, @NotBlank @PathVariable String studentId,
                         @RequestParam(name = "mode", defaultValue = "vertically", required = false) String mode,
                         @RequestBody @Validated PaperImageSource[] imageSources) throws Exception {

        boolean vertically = "vertically".equalsIgnoreCase(mode);
        String imageUrl = processor.generateUrlPath(examCode, subjectCode, itemNum, studentId, vertically);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new URL(attachmentService.generateFullUrl(imageUrl)));
            response.addHeader("x-img-src", "offline");
        } catch (Exception ex) {
            log.error("获取物理裁切图片({})失败！", imageUrl);
        }

        // 如果没有获取到物理裁切图片
        if (Objects.isNull(bufferedImage)) {
            // 校验参数
            verifyParameters(imageSources);

            // 生成图片
            bufferedImage = attachmentService.processImage(vertically, imageSources);
            response.addHeader("x-img-src", "realtime");
        }

        // 输出图片
        outputImage(response, bufferedImage);
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
        // 校验参数
        verifyParameters(imageSources);

        // 输出图片
        outputImage(response, attachmentService.processImage("vertically".equalsIgnoreCase(mode), imageSources));
    }

    /**
     * 上传附件
     *
     * @param type             附件类别
     * @param multipartRequest 附件
     * @return 上传成功的附件
     */
    @ApiOperation(value = "上传附件", produces = MediaType.MULTIPART_FORM_DATA_VALUE, response = AttachmentVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "附件类型, 支持[EXAM_PAPER, EXAM_PAPER_FORMAT, OTHER]"),
            @ApiImplicitParam(name = "keepOriginalFilename", value = "是否保持原来的文件名", example = "false", dataTypeClass = Boolean.class),
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
     * 对参数进行校验
     *
     * @param imageSources 实时裁剪拼接参数
     */
    private void verifyParameters(PaperImageSource[] imageSources) {
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
    }

    /**
     * 将图片以流的方式输出到前端
     *
     * @param response      HttpServletResponse
     * @param bufferedImage 生成的图片
     * @throws Exception 产生图片异常
     */
    private void outputImage(HttpServletResponse response, BufferedImage bufferedImage) throws Exception {
        if (Objects.isNull(response) || Objects.isNull(bufferedImage)) {
            return;
        }

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

        // write the data out
        ImageIO.write(bufferedImage, "png", outStream);
        outStream.flush();
        outStream.close();
        response.setStatus(HttpServletResponse.SC_OK);
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
