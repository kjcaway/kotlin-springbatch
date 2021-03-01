package me.examplebatch.batch.tasklet

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@StepScope // jobParameters 사용을 위해서
class SimpleTasklet : Tasklet {
    val logger: Log = LogFactory.getLog(SimpleTasklet::class.java)

    @Value("#{jobParameters[createDate]}")
    private val createDate: String? = null   // ex) In program arguments, set like 'createDate=2020-10-10'

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        logger.info("SimpleTasklet >>>>")
        logger.info("createDate : $createDate")

        return RepeatStatus.FINISHED
    }
}