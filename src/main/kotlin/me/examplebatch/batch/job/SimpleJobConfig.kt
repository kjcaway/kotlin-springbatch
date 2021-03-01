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

    @Bean
    fun simpleJob(): Job {
        return jobBuilderFactory.get("simpleJob5")
            .start(simpleStep1())
            .next(simpleStep2())
            .build()
    }

    @Bean
    fun simpleStep1(): Step{
        return stepBuilderFactory.get("simpleStep1")
            .tasklet { contribution, chunkContenxt ->
                logger.info("SimpleStep1 >>>>>")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun simpleStep2(): Step{
        return stepBuilderFactory.get("simpleStep2")
            .tasklet(simpleTasklet)
            .build()
    }
}