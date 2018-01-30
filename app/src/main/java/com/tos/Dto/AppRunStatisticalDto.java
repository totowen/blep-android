package com.tos.Dto;

import java.io.Serializable;

/**
 * app实例运行统计Dto
 * @author weishengbin
 *
 */
public class AppRunStatisticalDto implements Serializable {
	private static final long serialVersionUID = 8757020148297343181L;
	
	/**
	 * 7天运行次数
	 */
	private Integer sevenDaysRunNumber;
	
	/**
	 * 7天运行时长
	 */
	private long sevenDaysRunDuration;
	
	/**
	 * 1月运行次数
	 */
	private Integer oneMonthRunNumber;
	
	/**
	 * 1月运行时长
	 */
	private long oneMonthRunDuration;
	
	/**
	 * 半年运行次数
	 */
	private Integer halfYearRunNumber;
	
	/**
	 * 半年运行时长
	 */
	private long halfYearRunDuration;
	
	/**
	 * 总运行次数
	 */
	private Integer allRunNumber;
	
	/**
	 * 总运行时长
	 */
	private long allRunDuration;

	public Integer getSevenDaysRunNumber() {
		return sevenDaysRunNumber;
	}

	public void setSevenDaysRunNumber(Integer sevenDaysRunNumber) {
		this.sevenDaysRunNumber = sevenDaysRunNumber;
	}

	public long getSevenDaysRunDuration() {
		return sevenDaysRunDuration;
	}

	public void setSevenDaysRunDuration(long sevenDaysRunDuration) {
		this.sevenDaysRunDuration = sevenDaysRunDuration;
	}

	public Integer getOneMonthRunNumber() {
		return oneMonthRunNumber;
	}

	public void setOneMonthRunNumber(Integer oneMonthRunNumber) {
		this.oneMonthRunNumber = oneMonthRunNumber;
	}

	public long getOneMonthRunDuration() {
		return oneMonthRunDuration;
	}

	public void setOneMonthRunDuration(long oneMonthRunDuration) {
		this.oneMonthRunDuration = oneMonthRunDuration;
	}

	public Integer getHalfYearRunNumber() {
		return halfYearRunNumber;
	}

	public void setHalfYearRunNumber(Integer halfYearRunNumber) {
		this.halfYearRunNumber = halfYearRunNumber;
	}

	public long getHalfYearRunDuration() {
		return halfYearRunDuration;
	}

	public void setHalfYearRunDuration(long halfYearRunDuration) {
		this.halfYearRunDuration = halfYearRunDuration;
	}

	public Integer getAllRunNumber() {
		return allRunNumber;
	}

	public void setAllRunNumber(Integer allRunNumber) {
		this.allRunNumber = allRunNumber;
	}

	public long getAllRunDuration() {
		return allRunDuration;
	}

	public void setAllRunDuration(long allRunDuration) {
		this.allRunDuration = allRunDuration;
	}


}
