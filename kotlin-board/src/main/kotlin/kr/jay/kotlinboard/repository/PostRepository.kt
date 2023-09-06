package kr.jay.kotlinboard.repository

import kr.jay.kotlinboard.domain.Post
import kr.jay.kotlinboard.domain.QPost.post
import kr.jay.kotlinboard.service.dto.PostSearchRequestDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

/**
 * PostRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/09/05
 */
interface PostRepository : JpaRepository<Post, Long>, CustomPostRepository

interface CustomPostRepository {
    fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post>
}

class CustomPostRepositoryImpl : CustomPostRepository, QuerydslRepositorySupport(Post::class.java) {
    override fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post> {
        val result = from(post)
            .where(
                postSearchRequestDto.title?.let { post.title.contains(it) },
                postSearchRequestDto.createdBy?.let { post.createdBy.eq(it) }
            )
            .orderBy(post.createdAt.desc())
            .limit(pageRequest.pageSize.toLong())
            .offset(pageRequest.offset)
            .fetchResults()

        return PageImpl(result.results, pageRequest, result.total)
    }
}
