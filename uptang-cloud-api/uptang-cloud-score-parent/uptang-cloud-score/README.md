## backup
```java
@GetMapping("/{type}/{school-id}/{grade-id}/{semester-id}")
@ApiImplicitParams({
        @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
        @ApiImplicitParam(name = "school-id", value = "学校ID", paramType = "path", required = true),
        @ApiImplicitParam(name = "grade-id", value = "年级ID", paramType = "path", required = true),
        @ApiImplicitParam(name = "semester-id", value = "学期ID", paramType = "path", required = true),
})
@ApiOperation(value = "是否归档", response = Boolean.class)
public ApiOut<Boolean> isArchive(@PathVariable("school-id") @NotNull Long schoolId,
                                 @PathVariable("grade-id") @NotNull Long gradeId,
                                 @PathVariable("semester-id") @NotNull Long semesterId,
                                 @PathVariable("type") @NotNull Integer type) {
    boolean archive = scoreStatusService.isArchive(schoolId, gradeId, semesterId, ScoreTypeEnum.code(type));
    return ApiOut.newSuccessResponse(archive);
}
```