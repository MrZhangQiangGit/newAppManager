package com.bets.entity;

import java.util.List;

/** 
 * 说明：树形结构测试 实体类
 * 创建人：bets
 * 创建时间：2016-09-09
 */
public class TreeTest{ 
	
	private String TREETEST_ID;	//主键
	private String NAME;					//名称
	private String PARENT_ID;				//父类ID
	private String target;
	private TreeTest treetest;
	private List<TreeTest> subTreeTest;
	private boolean hasTreeTest = false;
	private String treeurl;
	
	private String CODE;			//编码
	public String getFCODE() {
		return CODE;
	}
	public void setFCODE(String CODE) {
		this.CODE = CODE;
	}
	private String REMARK;			//描述
	public String getFREMARK() {
		return REMARK;
	}
	public void setFREMARK(String REMARK) {
		this.REMARK = REMARK;
	}

	public String getTREETEST_ID() {
		return TREETEST_ID;
	}
	public void setTREETEST_ID(String TREETEST_ID) {
		this.TREETEST_ID = TREETEST_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String PARENT_ID) {
		this.PARENT_ID = PARENT_ID;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public TreeTest getTreeTest() {
		return treetest;
	}
	public void setTreeTest(TreeTest treetest) {
		this.treetest = treetest;
	}
	public List<TreeTest> getSubTreeTest() {
		return subTreeTest;
	}
	public void setSubTreeTest(List<TreeTest> subTreeTest) {
		this.subTreeTest = subTreeTest;
	}
	public boolean isHasTreeTest() {
		return hasTreeTest;
	}
	public void setHasTreeTest(boolean hasTreeTest) {
		this.hasTreeTest = hasTreeTest;
	}
	public String getTreeurl() {
		return treeurl;
	}
	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}
	
}
