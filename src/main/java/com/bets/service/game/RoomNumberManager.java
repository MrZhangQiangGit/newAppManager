package com.bets.service.game;

import com.bets.util.PageData;

public interface RoomNumberManager {
	
	/**
	 * 重置房间状态
	 */
	public int judge(String room_id)throws Exception;
	
	/**
	 * 重置房间状态
	 */
	public void reset(String room_id)throws Exception;
}
