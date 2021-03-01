package me.examplebatch.batch.job

import me.examplebatch.batch.type.JsonItem
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.json.JacksonJsonObjectReader
import org.springframework.batch.item.json.JsonItemReader
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource


@Configuration
class JsonFileJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory
) {
    private val logger: Log = LogFactory.getLog(JsonFileJobConfig::class.java)
    private final val chunkSize = 2;

    @Bean
    fun jsonFileJob(): Job{
        return jobBuilderFactory.get("jsonFileJob4")
            .start(jsonFileStep1())
            .build()
    }

    @Bean
    fun jsonFileStep1(): Step{
        return stepBuilderFactory.get("jsonFileStep4")
            .chunk<JsonItem,JsonItem>(chunkSize)
            .reader(jsonItemReader())
            .writer{ jsonItem ->
                jsonItem.stream()
                    .forEach{ println(it.toString())}
            }.build()
    }

    @Bean
    fun jsonItemReader(): JsonItemReader<JsonItem> {
        return JsonItemReaderBuilder<JsonItem>()
            .jsonObjectReader(JacksonJsonObjectReader(JsonItem::class.java))
            .resource(ClassPathResource("/sample/json/test.json"))
            .name("jsonItemReader")
            .build()
    }
}