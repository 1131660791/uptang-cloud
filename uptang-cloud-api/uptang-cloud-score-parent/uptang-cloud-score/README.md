## backup
```java
@GetMapping("/{type}/{schoolId}/{gradeId}/{semesterId}")
@ApiImplicitParams({
        @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
        @ApiImplicitParam(name = "schoolId", value = "学校ID", paramType = "path", required = true),
        @ApiImplicitParam(name = "gradeId", value = "年级ID", paramType = "path", required = true),
        @ApiImplicitParam(name = "semesterId", value = "学期ID", paramType = "path", required = true),
})
@ApiOperation(value = "是否归档", response = Boolean.class)
public ApiOut<Boolean> isArchive(@PathVariable("schoolId") @NotNull Long schoolId,
                                 @PathVariable("gradeId") @NotNull Long gradeId,
                                 @PathVariable("semesterId") @NotNull Long semesterId,
                                 @PathVariable("type") @NotNull Integer type) {
    boolean archive = scoreStatusService.isArchive(schoolId, gradeId, semesterId, ScoreTypeEnum.code(type));
    return ApiOut.newSuccessResponse(archive);
}
```