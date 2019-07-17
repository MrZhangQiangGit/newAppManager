package com.bets.service.game.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bets.dao.DaoSupport;
import com.bets.service.game.RoomNumberManager;
import com.bets.util.PageData;

@Service("roomNumberService")
public class RoomNumberService implements RoomNumberManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public int judge(String room_id) throws Exception {
		return (int)dao.findForObject("RoomNumberMapper.judge", room_id);	
	}
	
	@Override
	public void reset(String room_id) throws Exception {
		dao.update("RoomNumberMapper.reset", room_id);	
	}

	

}
