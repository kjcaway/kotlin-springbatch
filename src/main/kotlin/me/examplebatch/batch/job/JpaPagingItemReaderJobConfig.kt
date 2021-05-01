package me.examplebatch.batch.job

import me.examplebatch.batch.domain.Book
import me.examplebatch.batch.utils.DateUtil
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import javax.persistence.EntityManagerFactory

@Configuration
class JpaPagingItemReaderJobConfig(
        val jobBuilderFactory: JobBuilderFactory,
        val stepBuilderFactory: StepBuilderFactory,
        val entityManagerFactory: EntityManagerFactory
) {
    private final val JPA_PAGING_IR_JOB = "JPA_PAGING_IR_JOB"
    private final val JPA_PAGING_IR_JOB_STEP = JPA_PAGING_IR_JOB +"_STEP"
    private final val CHUNK_SIZE = 10

    @Bean
    fun jpaPagingItemReaderJob(): Job {
        return jobBuilderFactory.get(JPA_PAGING_IR_JOB)
                .start(jpaPagingItemReaderStep())
                .build()
    }

    @Bean
    fun jpaPagingItemReaderStep(): Step {
        return stepBuilderFactory.get(JPA_PAGING_IR_JOB_STEP)
                .chunk<Book, Book>(CHUNK_SIZE)
                .reader(jpaPagingItemReader(null))
                .processor(jpaPagingProcessor())
                .writer(jpaPagingWriter())
                .build()
    }

    @Bean
    @StepScope
    fun jpaPagingItemReader(@Value("#{jobParameters[createDate]}") createDate: String?): JpaPagingItemReader<Book> {
        val params = hashMapOf<String, Any>()
        params["now"] = Date(DateUtil.getTimestamp(createDate!!, "yyyy-MM-dd").time)

        return JpaPagingItemReaderBuilder<Book>()
                .queryString("SELECT b FROM Book b WHERE b.createdTime < :now")
                .pageSize(CHUNK_SIZE)
                .entityManagerFactory(entityManagerFactory)
                .parameterValues(params)
                .name("bookJpaPagingItemReader")
                .build()
    }

    @Bean
    fun jpaPagingProcessor(): ItemProcessor<Book, Book> {
        return ItemProcessor {
            // TODO: do something...
            it
        }
    }

    @Bean
    fun jpaPagingWriter(): ItemWriter<Book> {
        return ItemWriter {
            it.forEach { book ->
                println(book)
            }
        }
    }
}