
package com.example.project1.dao;

import java.util.List;

import com.example.project1.vo.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {

	@Select("SELECT * FROM member WHERE BINARY id=#{id} AND password=#{password}")
	Member selectAll(Member member);

	@Select("SELECT * FROM member WHERE BINARY id=#{id} AND deleted = 0")
	Member selectById(String id);

	@Update("UPDATE member SET failed_attempts=#{failedAttempts}, locked=#{locked} WHERE BINARY id=#{id}")
	void updateFailedAttempts(Member member);

	@Insert("INSERT INTO member (id, password, name, tel, postcode, address, detail_address, authority, profile) VALUES (#{id}, #{password}, #{name}, #{tel}, #{postcode}, #{address}, #{detailAddress}, #{authority}, #{profile})")
	void insert(Member member);

	@Select({
	    "<script>",
	    "SELECT * FROM member",
	    "<where>",
	    	"deleted = 0",
	        "<if test='searchType != null and searchType == \"id\"'> AND BINARY id LIKE CONCAT('%', #{searchValue}, '%') </if>" ,
	        "<if test='searchType != null and searchType == \"name\"'> AND name LIKE CONCAT('%', #{searchValue}, '%') </if>",
	        "<if test='searchType != null and searchType == \"tel\"'> AND tel LIKE CONCAT('%', #{searchValue}, '%') </if>",
	        "<if test='searchType != null and searchType == \"address\"'> AND address LIKE CONCAT('%', #{searchValue}, '%') </if>",
	        "<if test='searchType != null and searchType == \"createdat\"'> AND created_at BETWEEN #{startDate} AND DATE_ADD(#{endDate}, INTERVAL 1 DAY) - INTERVAL 1 SECOND </if>",
	        "<if test='searchType != null and searchType == \"authority\"'> AND authority = #{searchValue} </if>",
	    "</where>",
	    "ORDER BY created_at DESC",
	    "LIMIT #{pageSize}",
	    "OFFSET #{offset}",
	    "</script>"})
	List<Member> selectMemberByCondition(@Param("searchType") String searchType,
			@Param("searchValue") String searchValue, @Param("startDate") String startDate,
			@Param("endDate") String endDate, @Param("pageSize") int pageSize, @Param("offset") int offset);

	@Select({ 
	    "<script>", 
	    "SELECT COUNT(*) FROM member",
	    "<where>",
	        "deleted = 0",
	        "<if test='searchType != null and searchType == \"id\"'> AND BINARY id LIKE CONCAT('%', #{searchValue}, '%') </if>",
	        "<if test='searchType != null and searchType == \"name\"'> AND name LIKE CONCAT('%', #{searchValue}, '%') </if>",
	        "<if test='searchType != null and searchType == \"tel\"'> AND tel LIKE CONCAT('%', #{searchValue}, '%') </if>",
	        "<if test='searchType != null and searchType == \"address\"'> AND address LIKE CONCAT('%', #{searchValue}, '%') </if>",
	        "<if test='searchType != null and searchType == \"createdat\"'> AND created_at BETWEEN #{startDate} AND DATE_ADD(#{endDate}, INTERVAL 1 DAY) - INTERVAL 1 SECOND </if>",
	        "<if test='searchType != null and searchType == \"authority\"'> AND authority = #{searchValue} </if>",
	    "</where>",
	    "</script>" 
	})
	int countMember(@Param("searchType") String searchType, @Param("searchValue") String searchValue,
			@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Update("UPDATE member SET profile=#{profile}, name=#{name}, tel=#{tel}, postcode=#{postcode}, address=#{address}, detail_address=#{detailAddress}, authority=#{authority}, updated_at=#{updatedAt}, updated_by=#{updatedBy} WHERE BINARY id=#{id}")
	int update(Member member);

	@Delete("DELETE FROM member WHERE BINARY id=#{id}")
	int deleteById(String id);

	@Update("UPDATE member SET locked=#{locked} WHERE BINARY id=#{id}")
	int updateLocked(Member member);

	@Update("UPDATE member SET deleted = #{deleted}, updated_by = #{updatedBy}, updated_at=#{updatedAt} WHERE BINARY id=#{id}")
	int updateMember(Member member);

	@Update("UPDATE member SET profile=#{profile} WHERE BINARY id=#{id}")
	int updateProfile(Member member);

}
