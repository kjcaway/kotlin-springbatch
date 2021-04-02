package me.examplebatch.batch.domain

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_book")
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var bookId: Long? = null,
    var name: String? = null,
    var author: String? = null,
    var bookstoreId: Long? = null
): Base(), Serializable {
}