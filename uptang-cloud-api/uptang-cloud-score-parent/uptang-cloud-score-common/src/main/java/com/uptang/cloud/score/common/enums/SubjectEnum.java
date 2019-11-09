package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.pojo.enums.IEnumType;
import com.uptang.cloud.score.common.dto.AcademicScoreDTO;
import com.uptang.cloud.score.common.dto.HealthScoreDTO;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.common.util.Calculator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 11:03
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Getter
@AllArgsConstructor
public enum SubjectEnum implements IEnumType {

    /**
     * 语文
     */
    CHINESE(1, "语文", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = numberScoreRule(academic.getChinese());
            score.setSubject(this);
            return score;
        }
    },

    /**
     * 语文
     */
    MATH(2, "语文", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = numberScoreRule(academic.getMath());
            score.setSubject(this);
            return score;
        }
    },
    /**
     * 英文
     */
    ENGLISH(3, "英文", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = numberScoreRule(academic.getEnglish());
            score.setSubject(this);
            return score;
        }
    },

    /**
     * 物理
     */
    PHYSICS(4, "物理", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = numberScoreRule(academic.getPhysical());
            score.setSubject(this);
            return score;
        }
    },

    /**
     * 化学
     */
    CHEMISTRY(5, "化学", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = numberScoreRule(academic.getChemistry());
            score.setSubject(this);
            return score;
        }
    },

    /**
     * 历史
     */
    HISTORY(5, "历史", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = numberScoreRule(academic.getHistory());
            score.setSubject(this);
            return score;
        }
    },

    /**
     * 地理
     */
    GEOGRAPHY(7, "地理", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = numberScoreRule(academic.getGeography());
            score.setSubject(this);
            return score;
        }
    },
    /**
     * 生物
     */
    BIOLOGICAL(8, "生物", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = numberScoreRule(academic.getBiological());
            score.setSubject(this);
            return score;
        }
    },

    /**
     * 体育
     */
    PHYSICAL_CULTURE(9, "体育", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = numberScoreRule(academic.getPhysicalEducation());
            score.setSubject(this);
            return score;
        }
    },

    /**
     * 信息技术
     */
    INFORMATION_TECHNOLOGY(10, "信息技术", "成绩为等级：只有四个等级: 0 A ,1 B ,2 C ,3 D'") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = new Score();
            score.setSubject(this);
            score.setType(ScoreTypeEnum.ACADEMIC);
            score.setScoreText(academic.getTechnology());
            score.setScoreNumber(Data.INT_NONE);
            return score;
        }
    },

    /**
     * 音乐
     */
    MUSIC(11, "音乐", "0 合格 1 不合格") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = new Score();
            score.setSubject(this);
            score.setType(ScoreTypeEnum.ACADEMIC);
            score.setScoreText(academic.getMusic());
            score.setScoreNumber(Data.INT_NONE);
            return score;
        }
    },
    /**
     * 美术
     */
    ART(12, "美术", "0 合格 1 不合格") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = new Score();
            score.setSubject(this);
            score.setType(ScoreTypeEnum.ACADEMIC);
            score.setScoreText(academic.getArt());
            score.setScoreNumber(Data.INT_NONE);
            return score;
        }
    },

    /**
     * 物理实验
     */
    PHYSICAL_EXPERIMENT(13, "物理实验", "0 合格 1 不合格") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = new Score();
            score.setSubject(this);
            score.setType(ScoreTypeEnum.ACADEMIC);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(academic.getPhysicalExperiment());
            return score;
        }
    },
    /**
     * 化学实验
     */
    CHEMISTRY_EXPERIMENT(14, "化学实验", "0 合格 1 不合格") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = new Score();
            score.setSubject(this);
            score.setType(ScoreTypeEnum.ACADEMIC);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(academic.getChemistryExperiment());
            return score;
        }
    },
    /**
     * 生物实验
     */
    BIOLOGICAL_EXPERIMENT(15, "生物实验", "0 合格 1 不合格") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = new Score();
            score.setSubject(this);
            score.setType(ScoreTypeEnum.ACADEMIC);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(academic.getBiologicalExperiments());
            return score;
        }
    },

    /**
     * 劳动与技术教育
     */
    LABOR_TECHNICAL_EDUCATION(16, "劳动与技术教育", "0 合格 1 不合格") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = new Score();
            score.setSubject(this);
            score.setType(ScoreTypeEnum.ACADEMIC);
            score.setScoreText(academic.getLabor());
            score.setScoreNumber(Data.INT_NONE);
            return score;
        }
    },

    /**
     * 地方及校本课程
     */
    LOCAL_CURRICULUM(17, "地方及校本课程", "0 合格 1 不合格") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = new Score();
            score.setSubject(this);
            score.setType(ScoreTypeEnum.ACADEMIC);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(academic.getLocalCourse());
            return score;
        }
    },

    HEIGHT(18, "身高", "身高") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getHeight()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    BODY_WEIGHT(19, "体重", "体重") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getBodyWeight()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    BODY_WEIGHT_SCORE(20, "体重评分", "体重评分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getBodyWeightScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    BODY_WEIGHT_LEVEL(21, "体重等级", "体重等级") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(health.getBodyWeightLevel());
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    VITAL_CAPACITY(22, "肺活量", "肺活量") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getVitalCapacity()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    VITAL_CAPACITY_SCORE(23, "肺活量评分", "肺活量评分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getVitalCapacityScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    VITAL_CAPACITY_LEVEL(24, "肺活量等级", "肺活量等级") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(health.getVitalCapacityLevel());
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    FIFTY_METERS_RUN(25, "50米跑", "50米跑") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getFiftyMetersRun()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    FIFTY_METERS_LEVEL(26, "50米跑等级", "50米跑等级") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(health.getFiftyMetersLevel());
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    FIFTY_METERS_SCORE(27, "50米跑评分", "50米跑评分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getFiftyMetersScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    STANDING_LONG_JUMP(28, "立定跳远", "立定跳远") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getStandardScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    STANDING_LONG_JUMPS_CORE(29, "立定跳远评分", "立定跳远评分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getStandingLongJumpScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    STANDING_LONG_JUMP_LEVEL(30, "立定跳远等级", "立定跳远等级") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(health.getStandingLongJumpLevel());
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    FLEXION(31, "坐位体前屈", "坐位体前屈") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getFlexion()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    FLEXION_SCORE(32, "坐位体前屈评分", "坐位体前屈评分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getFlexionScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    FLEXION_LEVEL(33, "坐位体前屈等级", "坐位体前屈等级") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(health.getFlexionLevel());
            score.setScoreNumber(Data.INT_NONE);
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    EIGHT_HUNDRED_METERS(34, "800米跑", "800米跑") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(health.getEightHundredMeters());
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    EIGHT_HUNDRED_METERS_SCORE(35, "800米跑评分", "800米跑评分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getEightHundredMetersScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    EIGHT_HUNDRED_METERS_LEVEL(36, "800米跑等级", "800米跑等级") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(health.getEightHundredMetersLevel());
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    ADDITIONAL_POINTS_800(37, "800米跑附加分", "800米跑附加分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getAdditionalPoints800()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },


    ONE_KILO_METERS(38, "1000米跑", "1000米跑") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(health.getOneKilometerMeters());
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },


    ONE_KILOMETER_METERS_SCORE(39, "1000米跑评分", "1000米跑评分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getOneKilometerMetersScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    ONE_KILOMETER_METERS_LEVEL(40, "1000米跑等级", "1000米跑等级") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(health.getOneKilometerMetersLevel());
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    ADDITIONAL_POINTS_1000(41, "1000米跑附加分", "1000米跑附加分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getAdditionalPoints1000()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    SIT_UP(42, "一分钟仰卧起坐", "一分钟仰卧起坐") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(health.getSitUp());
            score.setScoreNumber(Data.INT_NONE);
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    SIT_UP_SCORE(43, "一分钟仰卧起坐评分", "一分钟仰卧起坐评分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getSitUpScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    SIT_UP_LEVEL(44, "一分钟仰卧起坐等级", "一分钟仰卧起坐等级") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(health.getSitUpLevel());
            score.setScoreNumber(Data.INT_NONE);
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    SIT_UP_ADDITIONAL_POINTS(46, "一分钟仰卧起坐附加分", "一分钟仰卧起坐附加分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getSitUpAdditionalPoints()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },


    PULL_UP(47, "引体向上", "引体向上") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Data.INT_NONE);
            score.setScoreText(health.getPullUp());
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    PULL_UP_SCORE(48, "引体向上评分", "引体向上评分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setScoreNumber(Calculator.x10(health.getPullUpScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    PULL_UP_LEVEL(49, "引体向上等级", "引体向上等级") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setScoreNumber(Data.INT_NONE);
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(health.getPullUpLevel());
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    PULL_UP_ADDITIONAL_POINTS(50, "引体向上附加分", "引体向上附加分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setScoreText(Strings.EMPTY);
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Calculator.x10(health.getPullUpAdditionalPoints()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    STANDARD_SCORE(51, "标准分", "标准分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setScoreText(Strings.EMPTY);
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Calculator.x10(health.getStandardScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    ADDITIONAL_POINTS(52, "附加分", "附加分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setScoreText(Strings.EMPTY);
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Calculator.x10(health.getAdditionalPoints()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    TOTAL_SCORE(53, "总分", "总分") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setScoreText(Strings.EMPTY);
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreNumber(Calculator.x10(health.getTotalScore()));
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    TOTAL_SCORE_LEVEL(54, "总分等级", "总分等级") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            Score score = new Score();
            score.setType(ScoreTypeEnum.HEALTH);
            score.setSubject(this);
            score.setScoreText(health.getTotalScoreLevel());
            score.setScoreNumber(Data.INT_NONE);
            return score;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            return null;
        }
    },

    /**
     * 道德与法治
     */
    MORALITY_LAW(55, "道德与法治", "道德与法治") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = numberScoreRule(academic.getMoralityLaw());
            score.setSubject(this);
            return score;
        }
    },

    /**
     * 艺术成绩
     */
    ART_SUBJECT(60, "艺术成绩", "艺术成绩") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            return null;
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            Score score = new Score();
            score.setSubject(this);
            score.setScoreText(Strings.EMPTY);
            score.setType(ScoreTypeEnum.ACADEMIC);
            score.setScoreNumber(Calculator.x10(academic.getChinese()));
            return score;
        }
    },

    /**
     * 未识别
     */
    UNKNOWN(98, "未识别", "未识别") {
        @Override
        public Score toScore(HealthScoreDTO health) {
            throw new BusinessException("无法识别该科目");
        }

        @Override
        public Score toScore(AcademicScoreDTO academic) {
            throw new BusinessException("无法识别该科目");
        }
    };


    @EnumValue
    private final int code;

    private final String desc;

    private final String ruleText;

    @JsonCreator
    public static SubjectEnum code(int code) {
        for (SubjectEnum member : SubjectEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return SubjectEnum.UNKNOWN;
    }

    @JsonValue
    public String toValue() {
        for (SubjectEnum member : SubjectEnum.values()) {
            if (member.getDesc().equals(this.desc)) {
                return member.desc;
            }
        }
        return UNKNOWN.desc;
    }

    private static Score numberScoreRule(Double scoreNumber) {
        Score score = new Score();
        score.setScoreText(Strings.EMPTY);
        score.setType(ScoreTypeEnum.ACADEMIC);
        if (scoreNumber != null && scoreNumber != 0.0D && scoreNumber <= 100) {
            score.setScoreNumber(Calculator.x10(scoreNumber));
        } else if (scoreNumber > 100 || scoreNumber < 0.0D) {
            throw new BusinessException(Message.SCORE);
        }
        return score;
    }

    public abstract Score toScore(HealthScoreDTO health);

    public abstract Score toScore(AcademicScoreDTO academic);

    /**
     * 分值 不符合要求
     */
    interface Message {
        String SCORE = "分数为百分制[0~100]";
    }

    /**
     * 分数值
     */
    public interface Data {
        /**
         * 表示该分数值未录入，如果scoreText有值，则说明scoreNumber是没有值得
         * 数据库类型为 SMALLINT(4)
         */
        Integer INT_NONE = 9999;
    }
}
