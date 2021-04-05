package me.examplebatch.batch.job

import me.examplebatch.batch.domain.Book
import me.examplebatch.batch.domain.BookRepository
import me.examplebatch.batch.item.CustomItemReader
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomReaderJobConfig(
        val jobBuilderFactory: JobBuilderFactory,
        val stepBuilderFactory: StepBuilderFactory,
        val bookRepository: BookRepository
) {
    private final val CUSTOM_READER_JOB = "CUSTOM_READER_JOB"
    private final val CUSTOM_READER_JOB_STEP = CUSTOM_READER_JOB +"_STEP"
    private final val CHUNK_SIZE = 10

    @Bean
    fun customReaderJob(): Job{
        return jobBuilderFactory.get(CUSTOM_READER_JOB)
                .start(customReaderStep())
                .build()
    }

    @Bean
    fun customReaderStep(): Step {
        return stepBuilderFactory.get(CUSTOM_READER_JOB_STEP)
                .chunk<Book, Book>(CHUNK_SIZE)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build()
    }

    @Bean
    @StepScope
    fun reader(): CustomItemReader{
        return CustomItemReader()
    }

    @Bean
    fun processor(): ItemProcessor<Book, Book>{
        return ItemProcessor {
            it.author = "Author. " + it.author
            it
        }
    }

    @Bean
    fun writer(): ItemWriter<Book> {
        return ItemWriter {
            bookRepository.saveAll(it)
        }
    }
}