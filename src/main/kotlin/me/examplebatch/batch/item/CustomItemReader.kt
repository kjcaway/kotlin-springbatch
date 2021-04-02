package me.examplebatch.batch.item

import me.examplebatch.batch.domain.Book
import me.examplebatch.batch.domain.BookRepository
import org.springframework.batch.item.ItemReader
import org.springframework.beans.factory.annotation.Autowired
import javax.annotation.PostConstruct

/**
 * Custom ItemReader
 *
 * open class 가 아니라면 StepScope로 프록시 객체 생성시 에러
 */
open class CustomItemReader : ItemReader<Book> {
    @Autowired
    private lateinit var bookRepository: BookRepository
    private lateinit var list: MutableList<Book>
    private var nextIndex: Int = 0

    @PostConstruct
    fun postConstruct(){
        list = bookRepository.findAll()
    }

    override fun read(): Book? {
        if(nextIndex < list.size){
            return list[nextIndex++]
        }
        return null
    }
}