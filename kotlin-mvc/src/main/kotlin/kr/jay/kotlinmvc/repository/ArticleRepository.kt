package kr.jay.kotlinmvc.repository

import kr.jay.kotlinmvc.model.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository: JpaRepository<Article, Long> {

    fun findAllByTitleContains(title: String): List<Article>

}