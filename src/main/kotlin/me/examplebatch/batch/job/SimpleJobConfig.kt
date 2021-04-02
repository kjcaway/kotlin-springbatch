package me.examplebatch.batch.job

import me.examplebatch.batch.tasklet.SimpleTasklet
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SimpleJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val simpleTasklet: SimpleTasklet
) {
    val logger: Log = LogFactory.getLog(SimpleJobConfig::class.java)

    private final val SIMPLE_JOB = "SIMPLE_JOB"
    private final val SIMPLE_JOB_STEP_1 = "SIMPLE_JOB_STEP_1"
    private final val SIMPLE_JOB_STEP_2 = "SIMPLE_JOB_STEP_2"

    @Bean
    fun simpleJob(): Job {
        return jobBuilderFactory.get(SIMPLE_JOB)
            .start(simpleStep1())
            .next(simpleStep2())
            .build()
    }

    @Bean
    fun simpleStep1(): Step{
        return stepBuilderFactory.get(SIMPLE_JOB_STEP_1)
            .tasklet { contribution, chunkContenxt ->
                logger.info("SimpleStep1 >>>>>")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun simpleStep2(): Step{
        return stepBuilderFactory.get(SIMPLE_JOB_STEP_2)
            .tasklet(simpleTasklet)
            .build()
    }
}