package kr.jay.couponcore.repository.mysql

import kr.jay.couponcore.model.CouponIssue
import org.springframework.data.jpa.repository.JpaRepository

/**
 * CouponIssueJpaRepository
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2/1/24
 */
interface CouponIssueJpaRepository : JpaRepository<CouponIssue, Long>