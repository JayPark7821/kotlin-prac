package kr.jay.couponcore.repository.mysql

import com.querydsl.core.QueryFactory
import com.querydsl.jpa.JPQLQueryFactory
import kr.jay.couponcore.model.CouponIssue
import kr.jay.couponcore.model.QCouponIssue
import kr.jay.couponcore.model.QCouponIssue.*
import org.springframework.stereotype.Repository

/**
 * CouponIssueRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/1/24
 */

@Repository
class CouponIssueRepository(
    private val queryFactory: JPQLQueryFactory
) {

    fun findFirstCouponIssued(couponId: Long, userId: Long): CouponIssue? =
        queryFactory.selectFrom(couponIssue)
            .where(couponIssue.couponId.eq(couponId))
            .where(couponIssue.userId.eq(userId))
            .fetchFirst()

}